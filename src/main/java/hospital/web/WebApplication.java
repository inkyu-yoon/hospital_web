package hospital.web;

import hospital.web.domain.entity.Hospital;
import hospital.web.parser.ReadData;
import hospital.web.repository.HospitalJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(WebApplication.class, args);

	}
}
