package com.airline.repository.impl;

import com.airline.repository.NewsRepository;
import com.airline.repository.entity.NewsEntity;
import com.airline.utils.ConnectionUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class NewsRepositoryImpl implements NewsRepository {
    @Override
    public List<NewsEntity> findAll() {
        List<NewsEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM news ORDER BY created_at DESC";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public NewsEntity findById(Long id) {
        String sql = "SELECT * FROM news WHERE news_id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(NewsEntity news) {
        String sql = "INSERT INTO news (title, content, created_at, author) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(news.getCreatedAt()));
            ps.setString(4, news.getAuthor());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) news.setNewsId(keys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(NewsEntity news) {
        String sql = "UPDATE news SET title=?, content=?, author=? WHERE news_id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getContent());
            ps.setString(3, news.getAuthor());
            ps.setLong(4, news.getNewsId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM news WHERE news_id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private NewsEntity mapRow(ResultSet rs) throws SQLException {
        NewsEntity n = new NewsEntity();
        n.setNewsId(rs.getLong("news_id"));
        n.setTitle(rs.getString("title"));
        n.setContent(rs.getString("content"));
        n.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        n.setAuthor(rs.getString("author"));
        return n;
    }
}
