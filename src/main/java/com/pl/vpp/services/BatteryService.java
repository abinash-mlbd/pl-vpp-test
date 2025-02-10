package com.pl.vpp.services;

import com.pl.vpp.entities.Battery;
import com.pl.vpp.repositories.BatteryRepository;
import com.pl.vpp.responses.BatteryResponse;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BatteryService {
    private final BatteryRepository repository;

    public List<Battery> addBatteries(List<Battery> batteries) {
        log.info("Adding {} batteries", batteries.size());
        return repository.saveAll(batteries);
    }

    public BatteryResponse getBatteries(String startPostcode, String endPostcode, Integer minCapacity, Integer maxCapacity) {
        log.info("Fetching batteries for postcode range {} - {} with min capacity {} and max capacity {}", startPostcode, endPostcode, minCapacity, maxCapacity);

        Specification<Battery> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.between(root.get("postcode"), startPostcode, endPostcode));
            if (minCapacity != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), minCapacity));
            }
            if (maxCapacity != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("capacity"), maxCapacity));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        List<Battery> batteries = repository.findAll(spec);
        batteries.sort((b1, b2) -> b1.getName().compareToIgnoreCase(b2.getName()));

        int totalCapacity = batteries.stream().mapToInt(Battery::getCapacity).sum();
        double avgCapacity = batteries.isEmpty() ? 0 : (double) totalCapacity / batteries.size();

        return new BatteryResponse(batteries.stream().map(Battery::getName).collect(Collectors.toList()), totalCapacity, avgCapacity);
    }
}
