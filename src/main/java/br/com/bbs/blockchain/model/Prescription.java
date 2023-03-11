package br.com.bbs.blockchain.model;

import br.com.bbs.blockchain.model.dto.CreatePrescriptionDTO;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Prescription {
    private String doctorPublicKey;
    @Getter
    private String patientPublicKey;
    private String prescriptionData;
    private String prescriptionSignature;
    private String creationDate;
    private String expirationDate;

    public Prescription() {
    }

    public Prescription(String doctorPublicKey, String patientPublicKey, String prescriptionData, String expirationDate) {
        this.doctorPublicKey = doctorPublicKey;
        this.patientPublicKey = patientPublicKey;
        this.prescriptionData = prescriptionData;
        this.prescriptionSignature = "";
        this.creationDate = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        this.expirationDate = LocalDateTime.parse(expirationDate).toString();
    }

    public Prescription(CreatePrescriptionDTO createPrescriptionDTO) {
        this.doctorPublicKey = createPrescriptionDTO.getDoctorPublicKey();
        this.patientPublicKey = createPrescriptionDTO.getPatientPublicKey();
        this.prescriptionData = createPrescriptionDTO.getPrescriptionData();
        this.prescriptionSignature = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        this.expirationDate = LocalDateTime.parse(createPrescriptionDTO.getExpirationDate(), formatter).toString();
        this.creationDate = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));

    }

    public boolean verifyPatientKey (String patientPublicKey){
        return patientPublicKey.equals(this.patientPublicKey);
    }

    public boolean verifyPrescriptionExpiration() {
        return LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))
                .isBefore(LocalDateTime.parse(this.expirationDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
    }

    public String getMedicine() {
        return prescriptionData;
    }
}
