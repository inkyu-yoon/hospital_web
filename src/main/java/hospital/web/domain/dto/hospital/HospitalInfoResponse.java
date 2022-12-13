package hospital.web.domain.dto.hospital;

import hospital.web.domain.entity.Hospital;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HospitalInfoResponse {
    private String openServiceName;
    private String status;
    private String phone;
    private String zipCode;
    private String fullAddress;
    private String roadNameAddress;
    private String hospitalName;
    private String businessTypeName;
    private Integer healthcareProviderCount;
    private Integer patientRoomCount;
    private Integer totalNumberOfBeds;
    private String medicalDepartment;

    public HospitalInfoResponse(Hospital hospital) {
        this.openServiceName = hospital.getOpenServiceName();
        this.phone = hospital.getPhone();
        this.zipCode = hospital.getZipCode();
        this.fullAddress = hospital.getFullAddress();
        this.roadNameAddress = hospital.getRoadNameAddress();
        this.hospitalName = hospital.getHospitalName();
        this.businessTypeName = hospital.getBusinessTypeName();
        this.healthcareProviderCount = hospital.getHealthcareProviderCount();
        this.patientRoomCount = hospital.getPatientRoomCount();
        this.totalNumberOfBeds = hospital.getTotalNumberOfBeds();
        this.medicalDepartment = hospital.getMedicalDepartment();

        if (hospital.getBusinessStatus() == 1) {
            this.status = "영업/정상";
        } else if (hospital.getBusinessStatus() == 2) {
            this.status = "휴업";
        } else if (hospital.getBusinessStatus() == 3) {
            this.status = "폐업";
        } else {
            this.status = "취소/말소/만료/정지/중지";
        }

    }
}
