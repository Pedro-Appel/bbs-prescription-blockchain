package br.com.bbs.blockchain.model.dto;

import lombok.Data;

@Data
public class CreatePrescriptionDTO {

    private String doctorPublicKey;
    private String patientPublicKey;
    private String prescriptionData;
    private String expirationDate;
}
