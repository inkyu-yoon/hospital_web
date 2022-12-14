package hospital.web.repository;

import hospital.web.domain.entity.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HospitalJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void insertHospitalList(List<Hospital> hospitalList) {
        jdbcTemplate.batchUpdate("insert into hospital (open_service_name, business_status,  phone,  zip_code,  full_address,  road_name_address,  hospital_name,\n" +
                "                     business_type_name,  healthcare_provider_count,  patient_room_count,  total_number_of_beds,  medical_department) values " +
                "(?,?,?,?,?,?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

            @Override
            public int getBatchSize() {
                return hospitalList.size();
            }

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Hospital hospital = hospitalList.get(i);
                int parameterIndex = 1;
                ps.setString(parameterIndex++, hospital.getOpenServiceName());
                ps.setInt(parameterIndex++, hospital.getBusinessStatus());
                ps.setString(parameterIndex++, hospital.getPhone());
                ps.setString(parameterIndex++, hospital.getZipCode());
                ps.setString(parameterIndex++, hospital.getFullAddress());
                ps.setString(parameterIndex++, hospital.getRoadNameAddress());
                ps.setString(parameterIndex++, hospital.getHospitalName());
                ps.setString(parameterIndex++, hospital.getBusinessTypeName());
                ps.setInt(parameterIndex++, hospital.getHealthcareProviderCount());
                ps.setInt(parameterIndex++, hospital.getPatientRoomCount());
                ps.setInt(parameterIndex++, hospital.getTotalNumberOfBeds());
                ps.setString(parameterIndex, hospital.getMedicalDepartment());}
        });
    }
}
