package com.javaweb.service;

import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.model.BuildingResponseDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class BuildingServiceTest {

    @Autowired
    private BuildingService buildingService;

    @MockBean
    private BuildingRepository buildingRepository;

    @MockBean
    private DistrictRepository districtRepository;

    private BuildingRequestDTO request;
    private BuildingResponseDTO response;
    private BuildingEntity building;

    @BeforeEach
    void setupData(){
        Long phone = Long.parseLong("02437723801");
        request = new BuildingRequestDTO(
                1L,
                "LandMark72Tower",
                "Phạm Hùng - Nam Từ Liêm - Hà Nội",
                "Lô E6",
                "Keangnam Enterprises",
                phone,
                1L
        );

        response = new BuildingResponseDTO(
                1L,
                "Haui University",
                "Phạm Hùng - Nam Từ Liêm - Hà Nội",
                "Lô E6",
                "Keangnam Enterprises",
                phone
        );

        building = new BuildingEntity(
                1L,
                "LandMark72Tower",
                "Phạm Hùng - Nam Từ Liêm - Hà Nội",
                "Lô E6",
                "Keangnam Enterprises",
                phone
        );
    }

    @Test
    void add_validRequest_success(){
        // GIVEN
        Mockito.when(buildingRepository.checkExist(ArgumentMatchers.any())).thenReturn(false);
        Mockito.when(buildingRepository.save(ArgumentMatchers.any(BuildingEntity.class))).thenReturn(building);

        // WHEN
        var res = buildingService.save(request);

        // THEN
        Assertions.assertThat(res.getName()).isEqualTo("LandMark72Tower");
        Assertions.assertThat(res.getManagerName()).isEqualTo("Keangnam Enterprises");
        Assertions.assertThat(res.getStreet()).isEqualTo("Phạm Hùng - Nam Từ Liêm - Hà Nội");
    }

    @Test
    void add_existedBuilding_fail(){
        // GIVEN
        Mockito.when(buildingRepository.checkExist(ArgumentMatchers.any())).thenReturn(true);

        // WHEN
        var exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class, () -> buildingService.save(request));

        // THEN
        Assertions.assertThat(exception.getMessage()).isEqualTo("Building already exist");
    }

    @Test
    void add_invalidDistrict_success(){
        // GIVEN
        Mockito.when(buildingRepository.checkExist(ArgumentMatchers.any())).thenReturn(false);
        Mockito.when(districtRepository.existsById(request.getDistrictId())).thenReturn(true);

        // WHEN
        var exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class, () -> buildingService.save(request));

        // THEN
        Assertions.assertThat(exception.getMessage()).isEqualTo("District invalid");
    }

    @Test
    void update_validRequest_success(){
        request.setId(1L);
        request.setName("Haui University");

        Mockito.when(buildingRepository.findById(request.getId())).thenReturn(Optional.of(building));

        var result = buildingService.update(request);

        Assertions.assertThat(result.getName()).isEqualTo("Haui University");
    }
}
