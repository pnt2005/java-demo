package com.example.demo.converter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.builder.BuildingSearchBuilder;
import com.example.demo.utils.MapUtil;

@Component
public class BuildingSearchBuilderConverter {
	public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> typeCode) {
		BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
				.setName(MapUtil.getObject(params, "name", String.class))
				.setStreet(MapUtil.getObject(params, "street", String.class))
				.setWard(MapUtil.getObject(params, "ward", String.class))
				.setDistrictId(MapUtil.getObject(params, "districtId", Long.class))
				.setNumberOfBasement(MapUtil.getObject(params, "numberOfBasement", Integer.class))
				.setFloorArea(MapUtil.getObject(params, "floorArea", Long.class))
				.setRentPriceFrom(MapUtil.getObject(params, "rentPriceFrom", Long.class))
				.setRentPriceTo(MapUtil.getObject(params, "rentPriceTo", Long.class))
				.setAreaFrom(MapUtil.getObject(params, "areaFrom", Long.class))
				.setAreaTo(MapUtil.getObject(params, "areaTo", Long.class))
				.setManagerName(MapUtil.getObject(params, "managerName", String.class))
				.setManagerPhonenumber(MapUtil.getObject(params, "managerPhoneNumber", String.class))
				.setTypeCode(typeCode).setStaffId(MapUtil.getObject(params, "staffId", Long.class)).build();
		return buildingSearchBuilder;
	}
}
