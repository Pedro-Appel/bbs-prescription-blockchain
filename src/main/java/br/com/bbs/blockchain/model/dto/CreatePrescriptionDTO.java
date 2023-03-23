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
}
