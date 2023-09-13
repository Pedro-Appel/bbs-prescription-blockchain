package br.com.bbs.blockchain.model;

import br.com.bbs.blockchain.model.dto.CreatePrescription;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZoneId;

public class Prescription {

    @Getter
    private final String doctorKey;
    @Getter
    private final String patientKey;
    @Getter
    private final String medicine;
    @Getter
    private final String signature;
    @Getter
    private final LocalDate creationDate;
    private final LocalDate expirationDate;

    public Prescription() {
        this.doctorKey = "";
        this.patientKey = "";
        this.medicine = "";
        this.signature = "";
        this.expirationDate = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        this.creationDate = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
    }

    public Prescription(CreatePrescription createPrescription) {
        this.doctorKey = createPrescription.doctorKey();
        this.patientKey = createPrescription.patientKey();
        this.medicine = createPrescription.medicine();
        this.signature = createPrescription.signature();
        this.expirationDate = createPrescription.expirationDate();
        this.creationDate = createPrescription.creationDate();

    }

    public boolean verifyPrescriptionExpiration() {

        return LocalDate.now(ZoneId.of("America/Sao_Paulo")).isBefore(this.expirationDate);

    }
}
