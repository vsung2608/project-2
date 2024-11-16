package com.javaweb.api;

import com.javaweb.model.ApiResponse;
import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.model.BuildingResponseDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@PropertySource("classpath:application.properties")
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;

	@GetMapping(value="/api/building/")
	public List<BuildingResponseDTO> getBuilding(@RequestParam Map<String,Object> params,
			@RequestParam(name="typeCode", required = false) List<String> typeCode){
		return buildingService.findAll(params, typeCode);
	}

	@Autowired
	BuildingRepository buildingRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	@PostMapping(value="/api/building/")
	@Transactional
	public ApiResponse<BuildingResponseDTO> createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingResponseDTO response = buildingService.save(buildingRequestDTO);
		return new ApiResponse<BuildingResponseDTO>(1000, "Add building successful", response);
	}

	@PutMapping(value="/api/building/")
	@Transactional
	public ApiResponse<BuildingResponseDTO> updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingResponseDTO response = buildingService.update(buildingRequestDTO);
		return new ApiResponse<BuildingResponseDTO>(1000, "Update building successful", response);
	}

	@DeleteMapping(value="/api/building/{ids}")
	@Transactional
	public void deleteByListId(@PathVariable Long[] ids) {
		buildingRepository.deleteByIdIn(ids);
	}

}



