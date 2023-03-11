package br.com.bbs.blockchain.model;
import lombok.Getter;
import org.apache.tomcat.util.buf.HexUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Block {

    private String timeStamp;
    @Getter
    private Prescription prescriptions;
    private String previousHash;
    @Getter
    private String hash;
    private BigInteger nonce;

    public Block(String timeStamp, Prescription prescriptions, String previousHash) {
        this.timeStamp = timeStamp;
        this.prescriptions = prescriptions;
        this.previousHash = previousHash;
        this.hash = this.calculateHash();
        this.nonce = new BigInteger("0");
    }

    private String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            String originalString = this.timeStamp + this.prescriptions.toString() + this.previousHash + this.nonce;
            byte[] byteHash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
            return HexUtils.toHexString(byteHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean mineBlock(Integer difficulty){
        String difficultString = "";
        for(int i = 0; i< difficulty+1; i++) {
            difficultString+="0";
        }
        System.out.println(difficultString);
        while (this.hash.substring(0, difficulty) != difficultString){
            this.nonce.add(new BigInteger("1"));
            this.hash = this.calculateHash();
        }
        return false;
    }

    public boolean isValid(String patientKey) {
        return this.getPrescriptions().verifyPrescriptionExpiration() && this.getPrescriptions().verifyPatientKey(patientKey);
    }

    public boolean validateHash(String hash) {
        return hash.equals(this.calculateHash());
    }
}
