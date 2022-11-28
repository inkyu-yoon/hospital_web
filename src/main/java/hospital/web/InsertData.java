package hospital.web;

import hospital.web.repository.HospitalJpaRepository;
import hospital.web.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;


public class InsertData {


    public static void main(String[] args) throws IOException {
        HospitalJpaRepository hospitalJpaRepository = new HospitalJpaRepository();
        hospitalJpaRepository.saveAllByPureJpa("hospitalData.txt");
    }
}
