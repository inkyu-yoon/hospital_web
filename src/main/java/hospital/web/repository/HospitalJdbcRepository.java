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
                ps.setString(1, hospital.getOpenServiceName());
                ps.setInt(2, hospital.getBusinessStatus());
                ps.setString(3, hospital.getPhone());
                ps.setString(4, hospital.getZipCode());
                ps.setString(5, hospital.getFullAddress());
                ps.setString(6, hospital.getRoadNameAddress());
                ps.setString(7, hospital.getHospitalName());
                ps.setString(8, hospital.getBusinessTypeName());
                ps.setInt(9, hospital.getHealthcareProviderCount());
                ps.setInt(10, hospital.getPatientRoomCount());
                ps.setInt(11, hospital.getTotalNumberOfBeds());
                ps.setString(12, hospital.getMedicalDepartment());}
        });
    }
}
