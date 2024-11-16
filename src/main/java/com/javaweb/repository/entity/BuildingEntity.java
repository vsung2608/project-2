package com.javaweb.repository.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="building")
public class BuildingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="street")
	private String street;
	
	@Column(name="ward")
	private String ward;

	@Column(name="managername")
	private String managername;
	
	@Column(name="managerphonenumber")
	private Long managerphonenumber;

	@Column(name = "rentprice")
	private int rentPrice;

	public int getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(int rentPrice) {
		this.rentPrice = rentPrice;
	}

	@ManyToOne
	@JoinColumn(name="districtid")
	private DistrictEntity district;
	
	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
	private List<RentAreaEntity> items = new ArrayList<>();
	
	@ManyToMany(mappedBy="buildings",fetch=FetchType.LAZY)
	private List<RentTypeEntity> renttypes= new ArrayList<>();

	public BuildingEntity() {}

	public BuildingEntity(Long id, String name, String street, String ward, String managername, Long managerphonenumber) {
		this.id = id;
		this.name = name;
		this.street = street;
		this.ward = ward;
		this.managername = managername;
		this.managerphonenumber = managerphonenumber;
	}

	public List<RentTypeEntity> getRenttypes() {
		return renttypes;
	}
	public void setRenttypes(List<RentTypeEntity> renttypes) {
		this.renttypes = renttypes;
	}
	public List<RentAreaEntity> getItems() {
		return items;
	}
	public void setItems(List<RentAreaEntity> items) {
		this.items = items;
	}
	public Long getManagerphonenumber() {
		return managerphonenumber;
	}
	public void setManagerphonenumber(Long managerphonenumber) {
		this.managerphonenumber = managerphonenumber;
	}
	public DistrictEntity getDistrict() {
		return district;
	}
	public void setDistrict(DistrictEntity district) {
		this.district = district;
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

	public String getManagername() {
		return managername;
	}
	public void setManagername(String managername) {
		this.managername = managername;
	}
	public Long getManagerPhoneNumber() {
		return managerphonenumber;
	}
	public void setManagerPhoneNumber(Long managerphonenumber) {
		this.managerphonenumber = managerphonenumber;
	}

	
	
	
}
