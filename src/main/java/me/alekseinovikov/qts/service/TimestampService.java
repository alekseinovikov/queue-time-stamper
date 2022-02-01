package me.alekseinovikov.qts.service;

import me.alekseinovikov.qts.persistence.model.Timestamp;

import java.util.List;

public interface TimestampService {
    List<Timestamp> findAll();
    void save(final Timestamp timestamp);
}
