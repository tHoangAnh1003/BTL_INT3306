package com.airline.repository.impl;

import com.airline.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import static org.junit.jupiter.api.Assertions.*;

class AirportRepositoryImplTest {
    @Test
    void testFindNameByIdAndFindIdByName() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        conn.createStatement().execute("CREATE TABLE airports (airport_id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255))");
        conn.createStatement().execute("INSERT INTO airports (name) VALUES ('Noi Bai')");
        AirportRepository repo = new AirportRepositoryImpl(conn);
        assertEquals("Noi Bai", repo.findNameById(1L));
        assertEquals(1L, repo.findIdByName("Noi Bai"));
    }
} 