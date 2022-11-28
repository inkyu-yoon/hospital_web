package hospital.web.repository;

import hospital.web.domain.entity.Hospital;
import hospital.web.parser.ReadData;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;



public class HospitalJpaRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("hospital");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction ts = em.getTransaction();

    @Transactional
    public int saveAllByPureJpa(String filename) throws IOException {

        ts.begin();

        ReadData readData = new ReadData();
        List<Hospital> hospitals = readData.readLine("hospitalData.txt");

        for (Hospital hospital : hospitals) {
            em.persist(hospital);
            System.out.println("hospital.getHospitalName() = " + hospital.getHospitalName());
        }

        ts.commit();
        return hospitals.size();
    }

}
