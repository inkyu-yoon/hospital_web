package hospital.web.parser;


import hospital.web.domain.entity.Hospital;

public class HospitalParser {

    public Hospital parse(String str) {
        //탭으로 구분된 txt파일을 split 한다.
        String[] row = str.split("\t");

        //개방 서비스명 (의원 혹은 병원)
        String openServiceName = row[1];

        //영업상태 구분 (1: 영업/정상  || 2: 휴업 || 3: 폐업 || 4: 취소/말소/만료/정지/중지)
        Integer businessStatus = Integer.parseInt(row[7]);

        //소재지 전화
        String phone = row[15];
        //소재지 우편번호
        String zipCode = null;
        //전체 주소
        String fullAddress = row[18].replaceAll("\"", "");
        //도로명 주소
        String roadNameAddress = row[19].replaceAll("\"", "");
        //사업장명
        String hospitalName = row[21];
        //업태구분명
        String businessTypeName = row[25];
        //의료인 수
        Integer healthcareProviderCount = 0;
        //입원실 수
        Integer patientRoomCount = 0;
        //병상 수
        Integer totalNumberOfBeds = 0;
        //총 면적
        //진료과목
        String medicalDepartment = null;

        if (!row[17].equals("")) {
            zipCode = row[17];
        }

        if (row.length >= 29) {
            if (!row[29].equals(""))
                healthcareProviderCount = Integer.parseInt(row[29]);
        }
        if (row.length >= 30) {
            if (!row[30].equals(""))
                patientRoomCount = Integer.parseInt(row[30]);
        }
        if (row.length >= 31) {
            if (!row[31].equals(""))
                totalNumberOfBeds = Integer.parseInt(row[31]);
        }

        if (row.length >= 34) {
            if (!row[34].equals("")) {
                medicalDepartment = row[34].replaceAll("\"", "");
            }
        }

        //위 로직에 따라, 정해진 값들을 Hospital 객체에 넣는다.
        Hospital hospital = new Hospital(openServiceName,  businessStatus,  phone,  zipCode,  fullAddress,  roadNameAddress,  hospitalName,
                businessTypeName,  healthcareProviderCount,  patientRoomCount,  totalNumberOfBeds,  medicalDepartment);



        return hospital;
    }
}
