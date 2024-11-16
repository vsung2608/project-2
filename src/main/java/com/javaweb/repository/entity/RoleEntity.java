package com.javaweb.repository.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="role")
public class RoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType. IDENTITY)
	private Long id;

	@Column(name="name", nullable = false)
	private String name;

	@Column(name="code",unique = true, nullable = false)
	private String code;
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public List<UserRoleEntity> getUserRoleEntities() {
//		return userRoleEntities;
//	}
//
//	public void setUserRoleEntities(List<UserRoleEntity> userRoleEntities) {
//		this.userRoleEntities = userRoleEntities;
//	}

	
	// dùng 1 entity phụ để kết nối 2 bảng khác khi nó có quan hệ many to many
//	@OneToMany(mappedBy = "role",fetch = FetchType.LAZY)
//	private List<UserRoleEntity> userRoleEntities = new ArrayList<>();
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY) // mappedBy phải đúng như khi bên kia trả ra
	private List<UserEntity> users = new ArrayList<>();

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}
	
}
