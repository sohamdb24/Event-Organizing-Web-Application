package com.app.eventorganizer.repository;

import com.app.eventorganizer.entity.VendorEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorEventRepository extends JpaRepository<VendorEvent, Long> {
}
