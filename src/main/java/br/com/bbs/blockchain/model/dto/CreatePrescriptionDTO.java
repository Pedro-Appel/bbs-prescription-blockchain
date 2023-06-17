package br.com.bbs.blockchain.model.dto;

import lombok.Data;

@Data
public class CreatePrescriptionDTO {

    private String medicine;
    private String patientKey;
    private String doctorKey;
    private String creationDate;
    private String expirationDate;
    private String signature;

    @Override
    public String toString() {
        return "CreatePrescriptionDTO{" +
                "medicine='" + medicine + '\'' +
                ", patientKey='" + patientKey + '\'' +
                ", doctorKey='" + doctorKey + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
