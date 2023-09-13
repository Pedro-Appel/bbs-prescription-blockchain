package br.com.bbs.blockchain.model;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.management.InvalidApplicationException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Log4j2
public class Block {

    private final LocalDateTime timeStamp;
    @Getter
    private final Prescription prescription;
    @Getter
    private final String previousHash;
    @Getter
    private String hash;
    private BigInteger nonce = BigInteger.valueOf(0);

    public Block(LocalDateTime timeStamp, Prescription prescriptions, String previousHash) throws InvalidApplicationException {
        this.timeStamp = timeStamp;
        this.prescription = prescriptions;
        this.previousHash = previousHash;
        this.hash = this.calculateHash();
    }

    private String calculateHash() throws InvalidApplicationException {
        String originalString = timeStamp + prescription.toString() + previousHash + nonce;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] byteHash = messageDigest.digest(originalString.getBytes());
            return StringUtils.leftPad(new BigInteger(1, byteHash).toString(16), 128, "0");
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            throw new InvalidApplicationException("No Such Algorithm");
        }
    }

    public void mineBlock(Integer difficulty) throws InvalidApplicationException {

        String zeroSequence = "0".repeat(Math.max(0, difficulty));

        while (!this.hash.substring(0, difficulty).equals(zeroSequence)){
            this.nonce = this.nonce.add(BigInteger.ONE);
            this.hash = this.calculateHash();
        }
    }

    public boolean validateHash(String hash) throws InvalidApplicationException {
        return hash.equals(this.calculateHash());
    }

    public boolean validate(String patientKey) {
        return this.getPrescription().getPatientKey().equals(patientKey) && prescription.verifyPrescriptionExpiration();
    }
}
