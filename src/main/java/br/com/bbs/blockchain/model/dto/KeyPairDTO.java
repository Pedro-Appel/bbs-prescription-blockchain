package br.com.bbs.blockchain.model.dto;

import lombok.Data;

import java.util.Base64;

@Data
public class KeyPairDTO {

    private String t1;
    private String t2;

    public KeyPairDTO(String t1, String t2) {
        this.t1 = Base64.getUrlEncoder().encodeToString(t1.getBytes());
        this.t2 = Base64.getUrlEncoder().encodeToString(t1.getBytes());
    }

}
