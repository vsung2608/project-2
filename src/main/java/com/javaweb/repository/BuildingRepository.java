package com.javaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaweb.repository.custom.BuildingRepositoryCustom;
import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long>,BuildingRepositoryCustom {
	
	// hàm này để xóa 1 list Id
	void deleteByIdIn(Long[] ids); // thêm in vào giúp xóa xả 1 mảng hoặc list

	// hàm này tìm kiếm 1 list theo name
	List<BuildingEntity> findByNameContaining(String s);
}
