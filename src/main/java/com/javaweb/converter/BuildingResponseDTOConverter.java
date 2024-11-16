package com.javaweb.converter;

import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingResponseDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentAreaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuildingResponseDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public BuildingResponseDTO toBuildingDTO(BuildingEntity item) {
        BuildingResponseDTO building = modelMapper.map(item, BuildingResponseDTO.class);

        List<RentAreaEntity> rentAreas = item.getItems();
        String areaResult = rentAreas.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(","));
        building.setRentArea(areaResult);
        return building;
    }
}
