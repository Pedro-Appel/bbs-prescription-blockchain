package br.com.bbs.blockchain.model.dto;

import br.com.bbs.blockchain.model.Block;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

@Getter
@JsonSerialize
public class UserPrescriptionsDTO{

    private final String medicine;
    private final String signature;

    public UserPrescriptionsDTO(Block block) {
        this.medicine = block.getPrescription().getMedicine();
        this.signature = block.getPrescription().getSignature();
    }
}
