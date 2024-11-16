package com.javaweb.model;

public class BuildingResponseDTO {
    private long id;
    private String name;
    private String street;
    private String ward;
    private String managerName;
    private Long managerPhone;
    private String rentArea;

    public BuildingResponseDTO() {}

    public BuildingResponseDTO(long id, String name, String street, String ward, String managerName, Long managerPhone) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.ward = ward;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
    }

    public BuildingResponseDTO(String name, String street, String ward, String managerName, Long managerPhone) {
        this.name = name;
        this.street = street;
        this.ward = ward;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Long getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(Long managerPhone) {
        this.managerPhone = managerPhone;
    }

    public void setRentArea(String areaResult) {
        this.rentArea = areaResult;
    }
}
