package com.hcg.interview.service;

import com.hcg.interview.entity.RoomAvailability;
import com.hcg.interview.repository.RoomAvailabilityRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AvailabilityService {
    @Autowired
    private RoomAvailabilityRepository roomAvailabilityRepository;

    public List<RoomAvailability> getAvailabilityForPeriod(LocalDate startDate, LocalDate endDate) {
        return roomAvailabilityRepository.findByDateBetween(startDate, endDate);
    }
}



