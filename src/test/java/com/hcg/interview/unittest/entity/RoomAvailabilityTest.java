package com.hcg.interview.unittest.entity;

import com.hcg.interview.entity.RatePlan;
import com.hcg.interview.entity.RoomAvailability;
import com.hcg.interview.entity.RoomType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RoomAvailabilityTest {
    @Test
    public void testRoomAvailability() {
        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(1L);

        RatePlan ratePlan = new RatePlan();
        ratePlan.setRatePlanId("RP1");

        LocalDate date = LocalDate.of(2024, 1, 1);

        RoomAvailability roomAvailability = new RoomAvailability();
        roomAvailability.setRoomType(roomType);
        roomAvailability.setRatePlan(ratePlan);
        roomAvailability.setDate(date);
        roomAvailability.setAvailableRooms(10L);

        assertEquals(roomType, roomAvailability.getRoomType());
        assertEquals(ratePlan, roomAvailability.getRatePlan());
        assertEquals(date, roomAvailability.getDate());
        assertEquals(Long.valueOf(10), roomAvailability.getAvailableRooms());
    }
}
