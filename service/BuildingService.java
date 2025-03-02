package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.model.BuildingDTO;

public interface BuildingService {
	List<BuildingDTO> findAll(Map<String, Object> params, List<String> typeCode);
}
