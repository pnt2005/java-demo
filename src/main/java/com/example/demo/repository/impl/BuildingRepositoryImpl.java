package com.example.demo.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.entity.BuildingEntity;
import com.example.demo.utils.ConnectionJDBCUtil;
import com.example.demo.utils.NumberUtil;
import com.example.demo.utils.StringUtil;

//data access layer

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	public static void joinTable(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
		String staffId = (String) params.get("staffId");
		if (StringUtil.checkString(staffId)) {
			sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}
		if (typeCode != null && typeCode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}
	}

	// normal là không cần join
	public static void queryNormal(Map<String, Object> params, StringBuilder where) {
		for (Map.Entry<String, Object> it : params.entrySet()) {
			if (!it.getKey().equals("staffId") && !it.getKey().equals("typeCode") && !it.getKey().startsWith("area")
					&& !it.getKey().startsWith("rentPrice")) {
				String value = it.getValue().toString();
				if (StringUtil.checkString(value)) {
					if (NumberUtil.isNumber(value)) {
						where.append(" AND b. " + it.getKey() + " = " + value);
					} else {
						where.append(" AND b. " + it.getKey() + " LIKE '%" + value + "%' ");
					}
				}
			}
		}
	}

	// special là cần join và có <= >=
	public static void querySpecial(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
		String staffId = (String) params.get("staffId");
		if (StringUtil.checkString(staffId)) {
			where.append(" AND assignmentbuilding.staffid = " + staffId);
		}
		String rentAreaTo = (String) params.get("areaTo");
		String rentAreaFrom = (String) params.get("areaFrom");
		if (StringUtil.checkString(rentAreaTo) || StringUtil.checkString(rentAreaFrom)) {
			where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid ");
			if (StringUtil.checkString(rentAreaFrom)) {
				where.append(" AND r.value >= " + rentAreaFrom);
			}
			if (StringUtil.checkString(rentAreaTo)) {
				where.append(" AND r.value <= " + rentAreaTo);
			}
			where.append(") ");
		}
		String rentPriceTo = (String) params.get("rentPriceTo");
		String rentPriceFrom = (String) params.get("rentPriceFrom");
		if (StringUtil.checkString(rentPriceTo) || StringUtil.checkString(rentPriceFrom)) {
			if (StringUtil.checkString(rentPriceFrom)) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if (StringUtil.checkString(rentPriceTo)) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
		if (typeCode != null && typeCode.size() != 0) {
			where.append(" AND(");
			String sql = typeCode.stream().map(it -> "renttype.code LIKE" + "'%" + it + "%' ")
					.collect(Collectors.joining(" OR "));
			where.append(sql + ") ");
		}
	}

	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		// StringBuilder là string có thể thay đổi
		StringBuilder sql = new StringBuilder(
				"SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.floorarea, b.rentprice, b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ");
		joinTable(params, typeCode, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNormal(params, where);
		querySpecial(params, typeCode, where);
		where.append(" GROUP BY b.id ");
		sql.append(where);

		System.out.println(sql);
		List<BuildingEntity> result = new ArrayList<BuildingEntity>();

		try (Connection conn = ConnectionJDBCUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			while (rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setId(rs.getLong("b.id"));
				buildingEntity.setName(rs.getString("b.name"));
				buildingEntity.setStreet(rs.getString("b.street"));
				buildingEntity.setWard(rs.getString("b.ward"));
				buildingEntity.setDistrictid(rs.getLong("b.districtid"));
				buildingEntity.setFloorArea(rs.getLong("b.floorarea"));
				buildingEntity.setRentPrice(rs.getLong("b.rentprice"));
				buildingEntity.setServiceFee(rs.getString("b.servicefee"));
				buildingEntity.setBrokerageFee(rs.getLong("b.brokeragefee"));
				buildingEntity.setManagerName(rs.getString("b.managername"));
				buildingEntity.setManagerPhonenumber(rs.getString("b.managerphonenumber"));
				result.add(buildingEntity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

}
