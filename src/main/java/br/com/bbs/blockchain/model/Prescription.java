package br.com.bbs.blockchain.model;

import br.com.bbs.blockchain.model.dto.CreatePrescriptionDTO;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Prescription {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    public static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");
    private String doctorKey;
    @Getter
    private String patientKey;
    private String medicine;
    private String signature;
    private String creationDate;
    private String expirationDate;
    public Prescription() {
    }


    public Prescription(String doctorKey, String patientKey, String medicine, String signature, String creationDate, String expirationDate) {
        this.doctorKey = doctorKey;
        this.patientKey = patientKey;
        this.medicine = medicine;
        this.signature = signature;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public Prescription(CreatePrescriptionDTO createPrescriptionDTO) {
        this.doctorKey = createPrescriptionDTO.getDoctorKey();
        this.patientKey = createPrescriptionDTO.getPatientKey();
        this.medicine = createPrescriptionDTO.getMedicine();
        this.signature = createPrescriptionDTO.getSignature();
        this.expirationDate = createPrescriptionDTO.getExpirationDate();
        this.creationDate = createPrescriptionDTO.getCreationDate();

    }

    public boolean verifyPatientKey (String patientPublicKey){

        return patientPublicKey.equals(this.patientKey);

    }

    public boolean verifyPrescriptionExpiration() {

        return LocalDateTime.now(ZONE_ID)
                .isBefore(LocalDateTime.parse(this.expirationDate, FORMATTER));

    }

    public boolean isValid(String patientKey) {
        return  this.verifyPatientKey(patientKey) && this.verifyPrescriptionExpiration();
    }

    public String getMedicine() {

        return this.medicine;

    }

    public String getSignature() {
        return this.signature;
    }
}
