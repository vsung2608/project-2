package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.javaweb.converter.BuildingResponseDTOConverter;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.model.BuildingResponseDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.DistrictEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.converter.BuildingSearchBuilderConvert;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuildingServiceImpl implements BuildingService{
	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired 
	private BuildingResponseDTOConverter buildingDTOConverter;
	
	@Autowired
	private BuildingSearchBuilderConvert buildingSearchBuilderConvert;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<BuildingResponseDTO> findAll(Map <String,Object> params, List<String> typeCode) {
		// TODO Auto-generated method stub
		BuildingSearchBuilder buildingSearchBuilder =buildingSearchBuilderConvert.toBuildingSearchBuilder(params, typeCode);
		
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(buildingSearchBuilder); // khi custom thì nó ra
		List<BuildingResponseDTO> result= new ArrayList<BuildingResponseDTO>();
		for(BuildingEntity item : buildingEntities){
			BuildingResponseDTO building = buildingDTOConverter.toBuildingDTO(item);
			
			result.add(building);
		}
		
		return result;
	}

	@Override
	@Transactional
	public BuildingResponseDTO save(BuildingRequestDTO request) {
		if(buildingRepository.checkExist(request)){
			throw new RuntimeException("Building already exist");
		}
		BuildingEntity buildEntity = new BuildingEntity();
		buildEntity.setName(request.getName());
		buildEntity.setStreet(request.getStreet());
		buildEntity.setWard(request.getWard());
		buildEntity.setManagername(request.getManagerName());
		buildEntity.setManagerphonenumber(request.getManagerPhone());

		if(districtRepository.existsById(request.getDistrictId())){
			throw new RuntimeException("District invalid");
		}

		DistrictEntity districtEntity = new DistrictEntity();
		districtEntity.setId(request.getDistrictId());
		buildEntity.setDistrict(districtEntity);
		entityManager.persist(buildEntity);

		return buildingDTOConverter.toBuildingDTO(buildEntity);
	}

	@Override
	public BuildingResponseDTO update(BuildingRequestDTO request) {
			BuildingEntity buildEntity = buildingRepository.findById(request.getId())
					.orElseThrow(() -> new RuntimeException("Building does not exist"));
			buildEntity.setName(request.getName());
			buildEntity.setStreet(request.getStreet());
			buildEntity.setWard(request.getWard());
			buildEntity.setRentPrice(request.getRentPrice());
			return buildingDTOConverter.toBuildingDTO(buildEntity);
	}
}
