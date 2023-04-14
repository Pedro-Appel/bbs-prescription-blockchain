package br.com.bbs.blockchain.controller;

import br.com.bbs.blockchain.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InvalidApplicationException;
import java.util.List;

@RestController()
@RequestMapping("/miner")
public class MinerController {
    @Autowired
    BlockchainService blockchain;

    @GetMapping()
    public ResponseEntity<List<String>> mineAllBlocks() throws InvalidApplicationException {
        try {
            List<String> listOfHash = blockchain.mineAllPendingBlocks();
            if(listOfHash.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(listOfHash);
        } catch (InvalidApplicationException e) {
            throw new InvalidApplicationException(e);
        }
    }

    @GetMapping(path = "/blocks")
    public ResponseEntity<Integer> getBlocksPending(){

        return ResponseEntity.ok(blockchain.getPendingPrescriptions().size());

    }
}
