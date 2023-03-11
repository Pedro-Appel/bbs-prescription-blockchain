package br.com.bbs.blockchain.model.dto;

import lombok.Data;

@Data
public class VerifyDataDTO {
    private String privateKey;
    private String prescriptionHex;

}
