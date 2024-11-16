package com.javaweb.model;


import jakarta.validation.constraints.Size;

public class BuildingRequestDTO {
    private Long id;
    private String name;
    private String street;
    private String ward;
    private String managerName;
    @Size()
    private Long managerPhone;
    private Long districtId;
    private int rentPrice;

    public BuildingRequestDTO() {}

    public BuildingRequestDTO(Long id, String name, String street, String ward, String managerName, Long managerPhone, Long districtId) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.ward = ward;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
        this.districtId = districtId;
    }

    public BuildingRequestDTO(String name, String street, String ward, String managerName, Long managerPhone, Long districtId) {
        this.name = name;
        this.street = street;
        this.ward = ward;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
        this.districtId = districtId;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(int rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
