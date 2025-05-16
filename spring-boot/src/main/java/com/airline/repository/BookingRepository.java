package com.airline.repository;


import java.util.List;

import com.airline.repository.entity.BookingEntity;

public interface BookingRepository {
    List<BookingEntity> findAll();
    BookingEntity findById(Long id);
    void save(BookingEntity booking);
    void update(BookingEntity booking);
    void delete(Long id);
}
