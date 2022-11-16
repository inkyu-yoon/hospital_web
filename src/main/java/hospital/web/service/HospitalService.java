package hospital.web.service;

import hospital.web.domain.Hospital;
import hospital.web.parser.ReadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

@Service
public class HospitalService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insertData() throws IOException {
        ReadData readData = new ReadData();
        List<Hospital> hospitals = readData.ReadLine("hospitalData.txt");

        for (Hospital hospital : hospitals) {
            em.persist(hospital);
            String hospitalName = hospital.getHospitalName();
            System.out.println(hospitalName);
        }

    }
}
