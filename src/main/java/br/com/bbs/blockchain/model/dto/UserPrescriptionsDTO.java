package br.com.bbs.blockchain.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonSerialize
public class UserPrescriptionsDTO {
    private String medicine;
    private String signature;

    public UserPrescriptionsDTO(String medicine, String signature) {
        this.medicine = medicine;
        this.signature = signature;
    }
}
