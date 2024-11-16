package com.javaweb.service;

import java.util.List;
import java.util.Map;

import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.model.BuildingResponseDTO;

public interface BuildingService {
	List<BuildingResponseDTO> findAll(Map<String, Object> params, List<String> typeCode);

	BuildingResponseDTO save(BuildingRequestDTO request);

	BuildingResponseDTO update(BuildingRequestDTO request);
}
