package com.hcg.interview.unittest.controller;

import com.hcg.interview.controller.AvailabilityController;
import com.hcg.interview.dto.RoomAvailabilityDTO;
import com.hcg.interview.dto.RoomRateDTO;
import com.hcg.interview.dto.RoomTypeAvailabilityDTO;
import com.hcg.interview.entity.RatePlan;
import com.hcg.interview.entity.RoomAvailability;
import com.hcg.interview.entity.RoomRate;
import com.hcg.interview.entity.RoomType;
import com.hcg.interview.repository.RoomRateRepository;
import com.hcg.interview.service.AvailabilityService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AvailabilityControllerTest {

    @InjectMocks
    private AvailabilityController availabilityController;

    @Mock
    private AvailabilityService availabilityService;

    @Mock
    private RoomRateRepository roomRateRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(availabilityService);
    }

    @Test
    public void testGetAvailabilityForPeriod() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-01-10");

        RoomAvailability roomAvailability = new RoomAvailability();
        roomAvailability.setDate(LocalDate.parse("2024-01-01"));
        roomAvailability.setAvailableRooms(10L);
        roomAvailability.setAvailabilityId(10L);

        RoomRate roomRate = new RoomRate();
        RatePlan ratePlan = new RatePlan();
        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(10L);
        roomType.setRoomTypeName("TEST");
        ratePlan.setRatePlanId("RP1");
        roomRate.setRatePlan(ratePlan);
        roomRate.setRoomRateID(10L);
        roomRate.setDate(LocalDate.parse("2024-01-01"));
        roomRate.setPrice(BigDecimal.valueOf(150.00));
        roomRate.setRoomType(roomType);
        roomAvailability.setRatePlan(ratePlan);
        roomAvailability.setRoomType(roomType);

        List<RoomRate> roomRates = Collections.singletonList(roomRate);
        when(availabilityService.getAvailabilityForPeriod(startDate, endDate)).thenReturn(Collections.singletonList(roomAvailability));
        when(roomRateRepository.findByRatePlanIdAndDate("RP1", LocalDate.parse("2024-01-01"))).thenReturn(roomRates);

        ResponseEntity<List<RoomAvailabilityDTO>> responseEntity = availabilityController.getAvailabilityForPeriod(startDate, endDate);
        List<RoomAvailabilityDTO> response = responseEntity.getBody();

        assertNotNull(response);
        assertEquals(1, response.size());

        RoomAvailabilityDTO roomAvailabilityDTO = response.get(0);
        assertEquals(roomAvailability.getDate(), roomAvailabilityDTO.getDate());

        RoomTypeAvailabilityDTO roomTypeAvailabilityDTO = roomAvailabilityDTO.getRoomAvailability().get(0);
        assertEquals(roomAvailability.getRoomType().getRoomTypeId(), roomTypeAvailabilityDTO.getRoomTypeId());
        assertEquals(roomAvailability.getAvailableRooms(), roomTypeAvailabilityDTO.getAvailableToSell());

        RoomRateDTO roomRateDTO = roomAvailabilityDTO.getRoomRate().get(0);
        assertEquals(roomRate.getRatePlan().getRatePlanId(), roomRateDTO.getRatePlanId());
        assertEquals(roomRate.getRoomType().getRoomTypeId(), roomRateDTO.getRoomTypeId());
        assertEquals(roomRate.getPrice(), roomRateDTO.getPrice());
        assertEquals("USD", roomRateDTO.getCurrency());
    }
}
