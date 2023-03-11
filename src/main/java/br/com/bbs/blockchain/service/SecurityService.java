package br.com.bbs.blockchain.service;

import br.com.bbs.blockchain.model.Block;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class SecurityService {
    public SecurityService() {

    }
    public String encryptPrescription(Block block) {
        System.out.println("-----------Block data encryption--------------");
        System.out.println(block.getPrescriptions().getPatientPublicKey());
        System.out.println(block.getPrescriptions().getMedicine());
        //TODO
        return "Encrypted";
    }
}
