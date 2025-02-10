package com.pl.vpp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.pl.vpp.entities.Battery;
import com.pl.vpp.responses.BatteryResponse;
import com.pl.vpp.services.BatteryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batteries")
@AllArgsConstructor
@Slf4j
@Tag(name = "Battery API", description = "API for managing battery data")
class BatteryController {
    private final BatteryService service;

    @PostMapping
    @Operation(summary = "Add batteries", description = "Adds a list of batteries to the database", responses = {
        @ApiResponse(responseCode = "200", description = "Batteries added successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Battery.class)))
    })
    public List<Battery> addBatteries(@RequestBody(description = "List of batteries to add") List<Battery> batteries) {
        log.info("Received request to add batteries");
        return service.addBatteries(batteries);
    }

    @GetMapping
    @Operation(summary = "Get batteries by postcode range", description = "Fetches batteries within the specified postcode range with optional filtering by watt capacity", responses = {
        @ApiResponse(responseCode = "200", description = "Battery data retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BatteryResponse.class)))
    })
    public BatteryResponse getBatteries(
        @RequestParam String startPostcode,
        @RequestParam String endPostcode,
        @RequestParam(required = false) Integer minCapacity,
        @RequestParam(required = false) Integer maxCapacity) {
        log.info("Received request to fetch batteries in range");
        return service.getBatteries(startPostcode, endPostcode, minCapacity, maxCapacity);
    }
}