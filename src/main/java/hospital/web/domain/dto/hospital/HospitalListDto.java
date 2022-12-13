package hospital.web.domain.dto.hospital;


import hospital.web.domain.entity.Hospital;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HospitalListDto {
    private Long id;
    private String hospitalName;
    private String roadNameAddress;
    private String phone;
    private String status;

    public HospitalListDto(Hospital hospital) {
        this.id = hospital.getId();
        this.hospitalName = hospital.getHospitalName();
        this.roadNameAddress = hospital.getRoadNameAddress();
        this.phone = hospital.getPhone();

        //영업상태 구분 (1: 영업/정상  || 2: 휴업 || 3: 폐업 || 4: 취소/말소/만료/정지/중지)
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
