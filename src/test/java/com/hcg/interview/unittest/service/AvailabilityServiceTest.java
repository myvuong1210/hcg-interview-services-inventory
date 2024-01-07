package com.hcg.interview.unittest.service;

import com.hcg.interview.entity.RoomAvailability;
import com.hcg.interview.repository.RoomAvailabilityRepository;
import com.hcg.interview.service.AvailabilityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AvailabilityServiceTest {

    @InjectMocks
    private AvailabilityService availabilityService;

    @Mock
    private RoomAvailabilityRepository roomAvailabilityRepository;

    @Test
    public void testGetAvailabilityForPeriod() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-01-10");

        RoomAvailability roomAvailability1 = new RoomAvailability();
        roomAvailability1.setDate(LocalDate.parse("2024-01-01"));
        roomAvailability1.setAvailableRooms(10L);

        RoomAvailability roomAvailability2 = new RoomAvailability();
        roomAvailability2.setDate(LocalDate.parse("2024-01-02"));
        roomAvailability2.setAvailableRooms(5L);

        List<RoomAvailability> mockRoomAvailabilities = Arrays.asList(roomAvailability1, roomAvailability2);
        when(roomAvailabilityRepository.findByDateBetween(startDate, endDate)).thenReturn(mockRoomAvailabilities);
        List<RoomAvailability> result = availabilityService.getAvailabilityForPeriod(startDate, endDate);

        assertNotNull(result);
        assertEquals(2, result.size());

        RoomAvailability resultRoomAvailability1 = result.get(0);
        assertEquals(roomAvailability1.getDate(), resultRoomAvailability1.getDate());
        assertEquals(roomAvailability1.getAvailableRooms(), resultRoomAvailability1.getAvailableRooms());

        RoomAvailability resultRoomAvailability2 = result.get(1);
        assertEquals(roomAvailability2.getDate(), resultRoomAvailability2.getDate());
        assertEquals(roomAvailability2.getAvailableRooms(), resultRoomAvailability2.getAvailableRooms());
    }
}

