package com.airline.repository;

import java.util.List;

import com.airline.repository.entity.BookingDetailEntity;

public interface BookingDetailRepository {
    List<BookingDetailEntity> findAll();
    BookingDetailEntity findById(Long id);
    void save(BookingDetailEntity bookingDetail);
    void update(BookingDetailEntity bookingDetail);
    void delete(Long id);
}
