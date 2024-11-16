package com.javaweb.repository.custom.impl;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.custom.BuildingRepositoryCustom;
import com.javaweb.repository.entity.BuildingEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
		Long staffId = buildingSearchBuilder.getStaffId();
		if (staffId != null) {
			sql.append("INNER JOIN assignmentbuilding a on a.buildingid=b.id ");
		}
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if (typeCode != null && typeCode.size() != 0) {
			sql.append("INNER JOIN buildingrenttype on buildingrenttype.buildingid= b.id ");
			sql.append("INNER JOIN renttype on renttype.id= buildingrenttype.renttypeid ");
		}
		
	}

	// field của chính những cái bảng mà kh cần inner join
	public static void queryNomal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {

		try {
			// java refection
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for (Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if (!fieldName.equals("staffId") && !fieldName.equals("typeCode") && !fieldName.startsWith("area")
						&& !fieldName.startsWith(("rentPrice"))) {
					Object value = item.get(buildingSearchBuilder);
					if (value != null) {
						if (item.getType().getName().equals("java.lang.Long")|| item.getType().getName().equals("java.lang.Integer")) {
							where.append(" AND b." + fieldName + " = " + value);
						} else {
							where.append(" AND b." + fieldName + " LIKE '%" + value + "%' ");
						}

					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		Long staffId = buildingSearchBuilder.getStaffId();
		if (staffId != null) {
			where.append(" AND assignmentbuilding.staffid = " + staffId);
		}
		Long rentAreaTo = buildingSearchBuilder.getAreaTo();
		Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
		if (rentAreaTo != null || rentAreaFrom != null) {
			where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id=r.buildingid");
			if (rentAreaTo != null) {
				where.append(" AND r.value <=" + rentAreaTo);
			}
			if (rentAreaFrom != null) {
				where.append(" AND r.value >=" + rentAreaFrom);
			}
			where.append(") ");
		}
		Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
		Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		if (rentPriceTo!= null || rentPriceFrom!= null) {
			if (rentPriceTo!= null) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
			if (rentPriceFrom!= null) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
		}

		List<String> typeCode= buildingSearchBuilder.getTypeCode();
		if (typeCode != null && typeCode.size() != 0) {
			where.append(" AND (");
			String sql = typeCode.stream().map(it -> "renttype.code LIKE" + "'%" + it + "%' ")
					.collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(" ) ");
		}
	}

//	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		StringBuilder sql = new StringBuilder(
				"SELECT b.id, b.name, b.street, b.ward, b.districtid, b.numberofbasement, b.floorarea, b.rentprice, b.rentpricedescription, b.managername, b.managerphonenumber"
						+ " FROM building b ");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1");
		queryNomal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
		where.append(" GROUP BY b.id");
		sql.append(where);
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		return query.getResultList();
	}

	@Override
	public boolean checkExist(BuildingRequestDTO request) {
		String query = "SELECT b.id, b.name, b.managername, b.managerphonenumber, b.districtid, b.rentprice, b.street, b.ward " +
				"FROM building b INNER JOIN district d ON b.districtid = d.id " +
				"WHERE b.name = :name " +
				"AND b.managerphonenumber = :managerPhone " +
				"AND b.managername = :managerName";

		Query q = entityManager.createNativeQuery(query, BuildingEntity.class);
		q.setParameter("name", request.getName());
		q.setParameter("managerPhone", request.getManagerPhone());
		q.setParameter("managerName", request.getManagerName());
		return !q.getResultList().isEmpty();
	}

}
