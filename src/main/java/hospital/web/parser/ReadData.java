package hospital.web.parser;

import hospital.web.domain.entity.Hospital;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadData {
    public List<Hospital> readLine(String filename) throws IOException {
        //전국 병·의원 데이터가 들어있는 txt 파일을 읽어온다.
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));

        //hospital 객체를 입력해 둘 List 초기화
        List<Hospital> hospitals = new ArrayList<>();

        //데이터 파일의 첫행은 스키마이므로, 한번 입력받는다.
        String column = br.readLine();

        //parser를 불러온다.
        HospitalParser hospitalParser = new HospitalParser();

        String str;

        //입력 값이 있는 한 진행한다.
        while ((str = br.readLine()) != null) {
            try {
                Hospital hospital = hospitalParser.parse(str);
                hospitals.add(hospital);
            } catch (Exception e) {
                System.out.println( str.substring(0,10) +" 는(/은) 데이터 오류가 있습니다.");
            }
        }
        return hospitals;
    }
}
