package com.example.demo.repository.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.builder.BuildingSearchBuilder;
import com.example.demo.repository.entity.BuildingEntity;

@Repository
public interface BuildingRepositoryCustom {
	List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder);
}
