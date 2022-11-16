package hospital.web.parser;

import hospital.web.domain.Hospital;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadData {
    public List<Hospital> ReadLine(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
        List<Hospital> hospitals = new ArrayList<>();
        String column = br.readLine();

        HospitalParser hospitalParser = new HospitalParser();

        String str = null;
        Hospital hospital = null;
        while ((str = br.readLine()) != null) {
            try {
                hospital = hospitalParser.parse(str);
                hospitals.add(hospital);
            } catch (Exception e) {
                System.out.println(hospital.getHospitalName() +"는(/은) 데이터 오류가 있습니다.");
            }
        }
        return hospitals;
    }
}
