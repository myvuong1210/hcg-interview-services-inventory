package com.hcg.interview.repository;

import com.hcg.interview.entity.RoomRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRateRepository extends JpaRepository<RoomRate, Long> {
    @Query("SELECT rr FROM RoomRate rr WHERE rr.ratePlan.ratePlanId = :ratePlanId AND rr.date = :date")
    List<RoomRate> findByRatePlanIdAndDate(@Param("ratePlanId") String ratePlanId, @Param("date") LocalDate date);
}

