package me.alekseinovikov.qts.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alekseinovikov.qts.persistence.model.Timestamp;
import me.alekseinovikov.qts.persistence.repository.TimestampJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor

@Service
public class TimestampServiceImpl implements TimestampService {

    private final TimestampJpaRepository timestampJpaRepository;


    @Transactional(readOnly = true)
    @Override
    public List<Timestamp> findAll() {
        return timestampJpaRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Timestamp timestamp) {
        timestampJpaRepository.saveAndFlush(timestamp);
    }
}
