package br.com.bbs.blockchain.model;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.management.InvalidApplicationException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
@Log4j2
public class Block {

    private String hash;
    private final String timeStamp;
    private final String previousHash;
    private final Prescription prescriptions;
    private BigInteger nonce = BigInteger.valueOf(0);

    public Block(String timeStamp, Prescription prescriptions, String previousHash) throws InvalidApplicationException {
        this.timeStamp = timeStamp;
        this.prescriptions = prescriptions;
        this.previousHash = previousHash;
        this.hash = this.calculateHash();
    }

    private String calculateHash() throws InvalidApplicationException {
        String originalString = this.timeStamp + this.prescriptions.toString() + this.previousHash + this.nonce;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] byteHash = messageDigest.digest(originalString.getBytes());
            String hexHash = new BigInteger(1, byteHash).toString(16);
            log.info("HexHash {} size, {} nonce, {}", hexHash.length(), this.nonce,hexHash);

            if (hexHash.length() < 128) hexHash = StringUtils.leftPad(hexHash, 128, "0");

            log.info("HexHash {} size, {} nonce, {}", hexHash.length(), this.nonce,hexHash);
            return hexHash;
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            throw new InvalidApplicationException("No Such Algorithm");
        }
    }

    public void mineBlock(Integer difficulty) throws InvalidApplicationException {

        String zeroSequence = "0".repeat(Math.max(0, difficulty));

        while (!this.hash.substring(0, difficulty).equals(zeroSequence)) {
            this.nonce = this.nonce.add(BigInteger.ONE);
            this.hash = this.calculateHash();
        }
        log.info("Difficulty = {}", difficulty);
    }

    public boolean validateHash(String hash) throws InvalidApplicationException {
        return hash.equals(this.calculateHash());
    }
}
