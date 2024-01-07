package com.hcg.interview.repository;

import com.hcg.interview.entity.RoomAvailability;
import com.hcg.interview.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {
    List<RoomAvailability> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
