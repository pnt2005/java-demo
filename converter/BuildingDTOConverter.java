package com.example.demo.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.BuildingDTO;
import com.example.demo.repository.DistrictRepository;
import com.example.demo.repository.RentAreaRepository;
import com.example.demo.repository.entity.BuildingEntity;
import com.example.demo.repository.entity.DistrictEntity;
import com.example.demo.repository.entity.RentAreaEntity;

@Component // đánh dấu đây là 1 bean
public class BuildingDTOConverter {
	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private RentAreaRepository rentAreaRepository;

	@Autowired
	private ModelMapper modelMapper;

	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		BuildingDTO building = modelMapper.map(item, BuildingDTO.class);
		DistrictEntity districtEntity = districtRepository.findNameById(item.getDistrictid());
		building.setAddress(item.getStreet() + ", " + item.getWard() + ", " + districtEntity.getName());
		List<RentAreaEntity> rentAreas = rentAreaRepository.getValueByBuildingId(item.getId());
		String areaResult = rentAreas.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(","));
		building.setRentArea(areaResult);
		return building;
	}
}
