package com.pl.vpp.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BatteryResponse {
    private List<String> names;
    private int totalCapacity;
    private double avgCapacity;
}