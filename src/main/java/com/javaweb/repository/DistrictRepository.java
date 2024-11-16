package com.javaweb.repository;


import com.javaweb.repository.entity.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {
	DistrictEntity findNameByID(Long id);
}
