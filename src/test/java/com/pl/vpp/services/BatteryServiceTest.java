package com.pl.vpp.services;

import com.pl.vpp.entities.Battery;
import com.pl.vpp.repositories.BatteryRepository;
import com.pl.vpp.responses.BatteryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BatteryServiceTest {
    @Mock
    private BatteryRepository repository;

    @InjectMocks
    private BatteryService service;

    @Test
    void testAddBatteries() {
        List<Battery> batteries = List.of(new Battery(null, "Battery1", "6000", 5000));
        Mockito.when(repository.saveAll(batteries)).thenReturn(batteries);
        List<Battery> result = service.addBatteries(batteries);
        assert(result.size() == 1);
    }

    @Test
    void testGetBatteriesByPostcodeRange() {
        List<Battery> batteries = new ArrayList<>(List.of(
            new Battery(1L, "BatteryA", "6001", 4000),
            new Battery(2L, "BatteryB", "6005", 6000))
        );
        Mockito.when(repository.findAll(Mockito.any(Specification.class))).thenReturn(batteries);
        BatteryResponse response = service.getBatteries("6000", "6100", null, null);
        assert(response.getNames().size() == 2);
        assert(response.getTotalCapacity() == 10000);
    }

    @Test
    void testGetBatteriesByCapacityAndSortingBatteriesName() {
        List<Battery> batteries = new ArrayList<>(List.of(
            new Battery(1L, "BatteryB", "6001", 4000),
            new Battery(2L, "BatteryA", "6005", 6000))
        );
        Mockito.when(repository.findAll(Mockito.any(Specification.class))).thenReturn(batteries);
        BatteryResponse response = service.getBatteries("6000", "6100", 3000, 7000);
        assert(response.getNames().size() == 2);
        assert(response.getTotalCapacity() == 10000);
        assert(response.getNames().get(0).equals("BatteryA")); // Ensure sorting by name
    }
}
