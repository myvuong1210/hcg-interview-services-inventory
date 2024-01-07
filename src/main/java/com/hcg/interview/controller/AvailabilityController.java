package com.hcg.interview.controller;

import com.hcg.interview.dto.RoomAvailabilityDTO;
import com.hcg.interview.dto.RoomRateDTO;
import com.hcg.interview.dto.RoomTypeAvailabilityDTO;
import com.hcg.interview.entity.RoomAvailability;
import com.hcg.interview.entity.RoomRate;
import com.hcg.interview.repository.RoomRateRepository;
import com.hcg.interview.service.AvailabilityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private RoomRateRepository roomRateRepository;

    @GetMapping("availability")
    public ResponseEntity<List<RoomAvailabilityDTO>> getAvailabilityForPeriod(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<RoomAvailability> availabilities = availabilityService.getAvailabilityForPeriod(startDate, endDate);
        List<RoomAvailabilityDTO> response = convertToResponseDTO(availabilities);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<RoomAvailabilityDTO> convertToResponseDTO(List<RoomAvailability> availabilities) {
        List<RoomAvailabilityDTO> response = new ArrayList<>();
        for (RoomAvailability availability : availabilities) {
            RoomAvailabilityDTO availabilityDTO = new RoomAvailabilityDTO();
            availabilityDTO.setDate(availability.getDate());

            RoomTypeAvailabilityDTO roomTypeAvailabilityDTO = new RoomTypeAvailabilityDTO();
            roomTypeAvailabilityDTO.setRoomTypeId(availability.getRoomType().getRoomTypeId());
            roomTypeAvailabilityDTO.setAvailableToSell(availability.getAvailableRooms());
            availabilityDTO.getRoomAvailability().add(roomTypeAvailabilityDTO);

            List<RoomRate> roomRates = roomRateRepository.findByRatePlanIdAndDate(availability.getRatePlan().getRatePlanId(), availability.getDate());
            for (RoomRate roomRate : roomRates) {
                RoomRateDTO roomRateDTO = new RoomRateDTO();
                roomRateDTO.setRatePlanId(availability.getRatePlan().getRatePlanId());
                roomRateDTO.setRoomTypeId(availability.getRoomType().getRoomTypeId());
                roomRateDTO.setPrice(roomRate.getPrice());
                roomRateDTO.setCurrency("USD");
                availabilityDTO.getRoomRate().add(roomRateDTO);
            }
            response.add(availabilityDTO);
        }

        return response;
    }
}
