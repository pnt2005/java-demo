package com.example.demo.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.BuildingDTO;
import com.example.demo.service.BuildingService;

//presentation layer

@RestController
public class BuildingAPI {
	@Autowired // tìm Buildingservice vì nó là interface, tức không có constructor
	private BuildingService buildingService;

	@GetMapping("/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> params,
			@RequestParam(name = "typeCode", required = false) List<String> typeCode) { // typeCode là 1 key nhưng nhiều
																						// value -> dùng list
		List<BuildingDTO> result = buildingService.findAll(params, typeCode);
		return result;
	}

//	public void valiDate(BuildingDTO buidBuildingDTO) throws FieldRequiredException {
//		if (buidBuildingDTO.getName() == null) {
//			throw new FieldRequiredException("name null");
//		}
//	}

//	@GetMapping("/api/building/")
//	public BuildingDTO postBuilding(@RequestBody BuildingDTO building) {
//		return building;
//	}

	@DeleteMapping("/api/building/{id}")
	public void deleteBuilding(@PathVariable Integer id) {
		System.out.println("da xoa building id " + id);
	}
}
