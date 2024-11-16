package com.javaweb.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.model.BuildingResponseDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class BuildingApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingService buildingService;

    @MockBean
    private BuildingRepository buildingRepository;

    private BuildingRequestDTO request;
    private BuildingResponseDTO response;
    private BuildingRequestDTO invalidRequest;

    @BeforeEach
    public void setupData(){
        Long phone = Long.parseLong("02437723801");

        request = new BuildingRequestDTO(
                "LandMark72Tower",
                "Phạm Hùng - Nam Từ Liêm - Hà Nội",
                "Lô E6",
                "Keangnam Enterprises",
                phone,
                1L
        );

        response = new BuildingResponseDTO(
                "LandMark72Tower",
                "Phạm Hùng - Nam Từ Liêm - Hà Nội",
                "Lô E6",
                "Keangnam Enterprises",
                phone
        );

        invalidRequest = new BuildingRequestDTO(
                "Nam Giao",
                "74 Nam Từ Liêm",
                "Phường 11",
                "Anh Sung-Chị Nam",
                915354727L,
                1L
        );
    }

    @Test
    void createBuilding_validRequest_success() throws Exception {
        //GIVEN
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        Mockito.when(buildingService.save(ArgumentMatchers.any(BuildingRequestDTO.class))).thenReturn(response);

        //WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/building/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Add building successful"));
    }

    @Test
    void createBuilding_existedBuilding_fail() throws Exception {
        //GIVEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String content = mapper.writeValueAsString(invalidRequest);

        Mockito.when(buildingRepository.checkExist(ArgumentMatchers.any(BuildingRequestDTO.class))).thenReturn(true);

        Mockito.when(buildingService.save(ArgumentMatchers.any(BuildingRequestDTO.class)))
                .thenThrow(new RuntimeException("Building already exists"));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/building/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(999))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Building already exists"));
        //THEN
    }

}
