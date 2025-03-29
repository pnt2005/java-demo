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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "building")
public class BuildingEntity {
	@Id // khóa chính
	@GeneratedValue(strategy = GenerationType.IDENTITY) // tự tăng dần
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "ward")
	private String ward;

	@Column(name = "street")
	private String street;

	@Column(name = "managername")
	private String managerName;

	@Column(name = "managerphonenumber")
	private String managerPhonenumber;

	@Column(name = "rentprice")
	private Long rentPrice;

	@Column(name = "floorarea")
	private Long floorarea;

	@Column(name = "numberofbasement")
	private Integer numberofbasement;

	@ManyToOne
	@JoinColumn(name = "districtid")
	private DistrictEntity district;

	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
	private List<RentAreaEntity> items = new ArrayList<>();

	@ManyToMany(mappedBy = "buildings", fetch = FetchType.LAZY)
	private List<RentTypeEntity> renttypes = new ArrayList<>();

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

	public DistrictEntity getDistrict() {
		return district;
	}

	public void setDistrict(DistrictEntity district) {
		this.district = district;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhonenumber() {
		return managerPhonenumber;
	}

	public void setManagerPhonenumber(String managerPhonenumber) {
		this.managerPhonenumber = managerPhonenumber;
	}

	public Long getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(Long rentPrice) {
		this.rentPrice = rentPrice;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumberofbasement() {
		return numberofbasement;
	}

	public void setNumberofbasement(Integer numberofbasement) {
		this.numberofbasement = numberofbasement;
	}

	public Long getFloorarea() {
		return floorarea;
	}

	public void setFloorarea(Long floorarea) {
		this.floorarea = floorarea;
	}
}
