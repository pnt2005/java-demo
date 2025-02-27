package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converter.BuildingDTOConverter;
import com.example.demo.model.BuildingDTO;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.entity.BuildingEntity;
import com.example.demo.service.BuildingService;

//business logic layer
@Service
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private BuildingDTOConverter buildingDTOConverter;

	@Override
	public List<BuildingDTO> findAll(Map<String, Object> params, List<String> typeCode) {
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(params, typeCode);
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();

		for (BuildingEntity item : buildingEntities) {
			BuildingDTO building = buildingDTOConverter.toBuildingDTO(item);
			result.add(building);
		}
		return result; // filter
	}

}
