package hospital.web;

import hospital.web.domain.entity.Hospital;
import hospital.web.parser.ReadData;
import hospital.web.repository.HospitalJdbcRepository;
import hospital.web.repository.HospitalJpaRepository;
import hospital.web.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class InsertData {


    public static void main(String[] args) throws IOException {
        Map<String, String> env = System.getenv();
        SimpleDriverDataSource datasource = new SimpleDriverDataSource();
        datasource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        datasource.setUrl(env.get("SPRING_DATASOURCE_URL"));
        datasource.setUsername(env.get("SPRING_DATASOURCE_USERNAME"));
        datasource.setPassword(env.get("SPRING_DATASOURCE_PASSWORD"));

        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
        HospitalJdbcRepository hospitalJdbcRepository = new HospitalJdbcRepository(jdbcTemplate);

        ReadData readData = new ReadData();
        List<Hospital> hospitals = readData.readLine("hospitalData.txt");
        hospitalJdbcRepository.insertHospitalList(hospitals);
    }
}
