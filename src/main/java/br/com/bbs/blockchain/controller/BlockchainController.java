package br.com.bbs.blockchain.controller;

import br.com.bbs.blockchain.model.Block;
import br.com.bbs.blockchain.model.Prescription;
import br.com.bbs.blockchain.model.dto.CreatePrescription;
import br.com.bbs.blockchain.model.dto.UserPrescriptionsDTO;
import br.com.bbs.blockchain.service.impl.BlockchainService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InvalidApplicationException;
import java.util.List;

@Log4j2
@RestController()
@RequestMapping("/chain")
public class BlockchainController {
    @Autowired
    BlockchainService blockchain;

    @GetMapping()
    public ResponseEntity<List<Block>> getChain(){
        return ResponseEntity.ok(blockchain.getFullChain());
    }

    @GetMapping("/prescription")
    public ResponseEntity<List<UserPrescriptionsDTO>> getUserPrescriptions(@RequestParam String patientKey) {

        try {
            List<UserPrescriptionsDTO> userPrescriptions = blockchain.getUserPrescriptions(patientKey);
            if(userPrescriptions.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(userPrescriptions);
        } catch (InvalidApplicationException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping()
    public ResponseEntity<String> addBlockToValidation(@RequestBody() CreatePrescription createPrescription){

        log.info("Adding block to validation ... {}", createPrescription.toString());
        Prescription prescription = new Prescription(createPrescription);
        boolean blockIncludedSuccessfully = blockchain.includePrescription(prescription);
        if(blockIncludedSuccessfully) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
