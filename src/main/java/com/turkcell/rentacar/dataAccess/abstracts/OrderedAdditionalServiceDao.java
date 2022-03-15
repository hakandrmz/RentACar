package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;

public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService, Integer> {
    OrderedAdditionalService getByOrderedAdditionalServiceId(String orderedAdditionalServiceId);
}
