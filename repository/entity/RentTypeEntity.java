package com.example.demo.repository.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "renttype")
public class RentTypeEntity {
	@Id // khóa chính
	@GeneratedValue(strategy = GenerationType.IDENTITY) // tự tăng dần
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "buildingrenttype", joinColumns = @JoinColumn(name = "renttypeid", nullable = false), inverseJoinColumns = @JoinColumn(name = "buildingid", nullable = false))
	private List<BuildingEntity> buildings = new ArrayList<>();
}
