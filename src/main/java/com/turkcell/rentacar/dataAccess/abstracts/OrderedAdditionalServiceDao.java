package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;

import java.util.List;
import java.util.UUID;

public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService, Integer> {
    List<OrderedAdditionalService> getByOrderedAdditionalServiceId(UUID orderedAdditionalServiceId);
}
