package br.com.bbs.blockchain.model.dto;

import java.time.LocalDate;

public record CreatePrescription(
        String medicine,
        String patientKey,
        String doctorKey,
        String signature,
        LocalDate creationDate,
        LocalDate expirationDate
) {}