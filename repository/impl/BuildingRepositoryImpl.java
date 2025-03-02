package com.example.demo.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.builder.BuildingSearchBuilder;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.entity.BuildingEntity;
import com.example.demo.utils.ConnectionJDBCUtil;

//data access layer

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
		Long staffId = buildingSearchBuilder.getStaffId();
		if (staffId != null) {
			sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if (typeCode != null && typeCode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}
	}

	// normal là không cần join
	public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
//		for (Map.Entry<String, Object> it : params.entrySet()) {
//			if (!it.getKey().equals("staffId") && !it.getKey().equals("typeCode") && !it.getKey().startsWith("area")
//					&& !it.getKey().startsWith("rentPrice")) {
//				String value = it.getValue().toString();
//				if (StringUtil.checkString(value)) {
//					if (NumberUtil.isNumber(value)) {
//						where.append(" AND b. " + it.getKey() + " = " + value);
//					} else {
//						where.append(" AND b. " + it.getKey() + " LIKE '%" + value + "%' ");
//					}
//				}
//			}
//		}
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields(); // duyệt các thuộc tính của class
			for (Field item : fields) {
				item.setAccessible(true); // cho phép truy cập các field
				String fieldName = item.getName();
				if (!fieldName.equals("staffId") && !fieldName.equals("typeCode") && !fieldName.startsWith("area")
						&& !fieldName.startsWith("rentPrice")) {
					Object value = item.get(buildingSearchBuilder);
					if (value != null) {
						if (item.getType().getName().equals("java.lang.Long")
								|| item.getType().getName().equals("java.lang.Integer")) {
							where.append(" AND b. " + fieldName + " = " + value);
						} else if (item.getType().getName().equals("java.lang.String")) {
							where.append(" AND b. " + fieldName + " LIKE '%" + value + "%' ");
						}
					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// special là cần join và có <= >=
	public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		Long staffId = buildingSearchBuilder.getStaffId();
		if (staffId != null) {
			where.append(" AND assignmentbuilding.staffid = " + staffId);
		}
		Long rentAreaTo = buildingSearchBuilder.getAreaTo();
		Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
		if (rentAreaTo != null || rentAreaFrom != null) {
			where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid ");
			if (rentAreaFrom != null) {
				where.append(" AND r.value >= " + rentAreaFrom);
			}
			if (rentAreaTo != null) {
				where.append(" AND r.value <= " + rentAreaTo);
			}
			where.append(") ");
		}
		Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
		Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		if (rentPriceTo != null || rentPriceFrom != null) {
			if (rentPriceFrom != null) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if (rentPriceTo != null) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if (typeCode != null && typeCode.size() != 0) {
			where.append(" AND(");
			String sql = typeCode.stream().map(it -> "renttype.code LIKE" + "'%" + it + "%' ")
					.collect(Collectors.joining(" OR "));
			where.append(sql + ") ");
		}
	}

	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		// StringBuilder là string có thể thay đổi
		StringBuilder sql = new StringBuilder(
				"SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.floorarea, b.rentprice, b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNormal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
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
