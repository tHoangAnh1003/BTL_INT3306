package com.airline.repository;

import java.util.List;

import com.airline.repository.entity.TicketEntity;

public interface TicketRepository {
    List<TicketEntity> findAll();
    TicketEntity findById(Long id);
    void save(TicketEntity ticket);
    void update(TicketEntity ticket);
    void delete(Long id);
}

