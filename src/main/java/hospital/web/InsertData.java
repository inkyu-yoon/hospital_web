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


public class InsertData {


    public static void main(String[] args) throws IOException {
        SimpleDriverDataSource datasource = new SimpleDriverDataSource();
        datasource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        datasource.setUrl("jdbc:mysql://ec2-35-73-226-27.ap-northeast-1.compute.amazonaws.com/inkyu-db?&rewriteBatchedStatements=true&profileSQL=true&logger=Slf4JLogger&maxQuerySizeToLog=999999");
        //-  rewriteBatchedStatements
        //
        //batch 형태의 SQL로 재작성 해주는 옵셥이며, MySQL에서는 기본 값이 false 이기 때문에 true로 변경이 필요합니다.
        //
        //-  profileSQL
        //
        //Driver에서 전소하는 쿼리를 출력, 해당 값도 true로 변경을 해줍니다.
        //
        //-  logger
        //
        //MySQL 드라이버 같은 경우 기본 값은 System.err로 출력하도록 설정되어 있기 때문에 Slf4jLogger로 변경 해줍니다.
        //
        //-  maxQuerySizeToLog
        //
        //해당 옵션은 출력할 쿼리의 길이를 지정할 수 있습니다. MySQL 드라이버 같은 경우 기본 값은 0 입니다.
        datasource.setUsername("root");
        datasource.setPassword("password");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
        HospitalJdbcRepository hospitalJdbcRepository = new HospitalJdbcRepository(jdbcTemplate);
        ReadData readData = new ReadData();
        List<Hospital> hospitals = readData.readLine("hospitalData.txt");
        hospitalJdbcRepository.insertHospitalList(hospitals);
    }
}
