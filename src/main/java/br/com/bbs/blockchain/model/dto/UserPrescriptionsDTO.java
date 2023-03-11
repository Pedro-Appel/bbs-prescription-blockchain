package br.com.bbs.blockchain.model.dto;

public class UserPrescriptionsDTO {
    private Integer index;
    private String medicine;
    private String signature;

    public UserPrescriptionsDTO(Integer index, String medicine, String signature) {
        this.index = index;
        this.medicine = medicine;
        this.signature = signature;
    }
}
