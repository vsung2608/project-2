package com.javaweb.repository.entity;

import jakarta.persistence.*;

@Entity
@Table(name="rentarea")
public class RentAreaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="value")
	private String value;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BuildingEntity getBuilding() {
		return building;
	}
	public void setBuilding(BuildingEntity building) {
		this.building = building;
	}
	@ManyToOne
	@JoinColumn(name="buildingid")
	private BuildingEntity building; // bên kia mapped như nào thì bên này phải như thế
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
