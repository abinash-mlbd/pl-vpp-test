package com.pl.vpp.repositories;

import com.pl.vpp.entities.Battery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatteryRepository extends JpaRepository<Battery, Long> {
    List<Battery> findByPostcodeBetweenAndCapacityBetween(String start, String end, Integer minCapacity, Integer maxCapacity);
    List<Battery> findAll(Specification<Battery> spec);
}
