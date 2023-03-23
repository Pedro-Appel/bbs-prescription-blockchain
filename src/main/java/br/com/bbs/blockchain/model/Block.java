package br.com.bbs.blockchain.model;
import lombok.Getter;

import javax.management.InvalidApplicationException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {

    private String timeStamp;
    @Getter
    private Prescription prescriptions;
    @Getter
    private String previousHash;
    @Getter
    private String hash;
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
            String hexaHash = new BigInteger(1, byteHash).toString(16);
            StringBuilder bld = new StringBuilder();
            while(hexaHash.length() < 128){
                bld.append("0");
                hexaHash = bld + hexaHash;
            }
            return hexaHash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new InvalidApplicationException("No Such Algorithm");
        }
    }

    public boolean mineBlock(Integer difficulty) throws InvalidApplicationException {

        String zeroSequence = "";
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i< difficulty; i++) {
            stringBuilder.append("0");
        }
        zeroSequence = stringBuilder.toString();

        while (!this.hash.substring(0, difficulty).equals(zeroSequence)){
            this.nonce = this.nonce.add(BigInteger.ONE);
            this.hash = this.calculateHash();
        }
        return true;
    }

    public boolean validateHash(String hash) throws InvalidApplicationException {
        return hash.equals(this.calculateHash());
    }
}
