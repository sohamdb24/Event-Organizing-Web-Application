package com.app.eventorganizer.repository;

import com.app.eventorganizer.entity.VendorEventService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorEventServiceRepository extends JpaRepository<VendorEventService, Long> {
}
