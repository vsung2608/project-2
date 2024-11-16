package com.javaweb.repository.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="renttype")
public class RentTypeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="code")
	private String code;
	
	@Column(name="name")
	private String name;
	

	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="buildingrenttype",
	joinColumns = @JoinColumn(name="renttypeid",nullable = false),
	inverseJoinColumns = @JoinColumn(name="buildingid", nullable = false))
	private List<BuildingEntity> buildings=new ArrayList<>();



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public List<BuildingEntity> getBuildings() {
		return buildings;
	}



	public void setBuildings(List<BuildingEntity> buildings) {
		this.buildings = buildings;
	}
	
}