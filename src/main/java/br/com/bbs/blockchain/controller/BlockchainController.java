package br.com.bbs.blockchain.controller;

import br.com.bbs.blockchain.model.Block;
import br.com.bbs.blockchain.model.Prescription;
import br.com.bbs.blockchain.model.dto.CreatePrescriptionDTO;
import br.com.bbs.blockchain.model.dto.UserPrescriptionsDTO;
import br.com.bbs.blockchain.service.AuthenticatorService;
import br.com.bbs.blockchain.service.BlockchainService;
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
    AuthenticatorService authenticatorService;
    @Autowired
    BlockchainService blockchain;

    @GetMapping(path = "/heathCheck")
    public ResponseEntity heathCheck(@RequestParam() String appKey){

        if(!authenticatorService.checkAppKey(appKey)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok("STATUS UP");

    }
    @GetMapping()
    public ResponseEntity<List<Block>> getChain(@RequestParam() String appKey){

        if(!authenticatorService.checkAppKey(appKey)) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(blockchain.getFullChain());
    }

    @GetMapping("/{key}/prescription")
    public ResponseEntity<List<UserPrescriptionsDTO>> getUserPrescriptions(@PathVariable String key) {

        try {
            List<UserPrescriptionsDTO> userPrescriptions = blockchain.getUserPrescriptions(key);
            if(userPrescriptions.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(userPrescriptions);
        } catch (InvalidApplicationException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping()
    public ResponseEntity<String> addBlockToValidation(@RequestBody() CreatePrescriptionDTO createPrescriptionDTO){

        log.info("Adding block to validation ... {}", createPrescriptionDTO.toString());
        Prescription prescription = new Prescription(createPrescriptionDTO);
        boolean blockIncludedSuccessfully = blockchain.includePrescription(prescription);
        if(blockIncludedSuccessfully) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
