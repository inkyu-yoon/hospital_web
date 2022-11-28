package hospital.web.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Long id;

    //개방 서비스명 (의원 혹은 병원)
    @Column(name = "open_service_name")
    private String openServiceName;

    //영업상태 구분 (1: 영업/정상  || 2: 휴업 || 3: 폐업 || 4: 취소/말소/만료/정지/중지)
    @Column(name = "business_status")
    private Integer businessStatus;

    //소재지 전화
    private String phone;

    //소재지 우편번호
    @Column(name = "zip_code")
    private String zipCode;

    //전체 주소
    @Column(name = "full_address")
    private String fullAddress;

    //도로명 주소
    @Column(name = "road_name_address")
    private String roadNameAddress;

    //사업장명
    @Column(name = "hospital_name")
    private String hospitalName;

    //업태구분명
    @Column(name = "business_type_name")
    private String businessTypeName;

    //의료인 수
    @Column(name = "healthcare_provider_count")
    private Integer healthcareProviderCount;

    //입원실 수
    @Column(name = "patient_room_count")
    private Integer patientRoomCount;

    //병상 수
    @Column(name = "total_number_of_beds")
    private Integer totalNumberOfBeds;

    //진료과목
    @Column(name = "medical_department",length = 350)
    private String medicalDepartment;


    public Hospital(String openServiceName, Integer businessStatus, String phone, String zipCode, String fullAddress, String roadNameAddress, String hospitalName,
                    String businessTypeName, Integer healthcareProviderCount, Integer patientRoomCount, Integer totalNumberOfBeds, String medicalDepartment) {
        this.openServiceName = openServiceName;
        this.businessStatus = businessStatus;
        this.phone = phone;
        this.zipCode = zipCode;
        this.fullAddress = fullAddress;
        this.roadNameAddress = roadNameAddress;
        this.hospitalName = hospitalName;
        this.businessTypeName = businessTypeName;
        this.healthcareProviderCount = healthcareProviderCount;
        this.patientRoomCount = patientRoomCount;
        this.totalNumberOfBeds = totalNumberOfBeds;
        this.medicalDepartment = medicalDepartment;
    }
}