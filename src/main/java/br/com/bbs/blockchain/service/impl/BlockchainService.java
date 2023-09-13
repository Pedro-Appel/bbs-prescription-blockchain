package br.com.bbs.blockchain.service.impl;

import br.com.bbs.blockchain.model.Block;
import br.com.bbs.blockchain.model.Prescription;
import br.com.bbs.blockchain.model.dto.UserPrescriptionsDTO;
import br.com.bbs.blockchain.service.ChainRepository;
import br.com.bbs.blockchain.service.PendingPrescriptionsRepository;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

import javax.management.InvalidApplicationException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class BlockchainService {
    private final ChainRepository chain;

    @Getter
    private final PendingPrescriptionsRepository pendingPrescriptions;

    private Integer difficulty;

    public BlockchainService(PendingPrescriptionsRepository pendingPrescriptionsRepository, ChainRepository chain) throws InvalidApplicationException {
        this.pendingPrescriptions = pendingPrescriptionsRepository;
        this.chain = chain;
        this.difficulty = 1;
        generateGenesisBlock();
    }

    public List<Block> getFullChain(){
        return this.chain.findAll();
    }

    private void generateGenesisBlock() throws InvalidApplicationException {
        chain.save(new Block(
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
                new Prescription(),
                "0"));
    }

    public boolean includePrescription(Prescription prescription){
        return this.pendingPrescriptions.save(prescription);
    }

    public List<String> mineAllPendingBlocks() throws InvalidApplicationException {
        List<String> hashArray = new ArrayList<>();

        if(this.pendingPrescriptions.isEmpty()) return hashArray;

        for(int i = this.pendingPrescriptions.size(); i > 0; i--){
            Block newBlock = new Block(
                    LocalDateTime.now(),
                    this.pendingPrescriptions.removeLast(),
                    chain.findLast().getHash()
            );
            newBlock.mineBlock(difficulty);
            addBlockToChain(newBlock);
            auditMinedBlock(newBlock); //TODO

            hashArray.add(newBlock.getHash());
            difficulty += 1;
        }

        return hashArray;
    }

    public List<UserPrescriptionsDTO> getUserPrescriptions(String patientKey) throws InvalidApplicationException {

        isChainValid();

        ArrayList<UserPrescriptionsDTO> userPrescriptions = new ArrayList<>();
        chain.findAllById(patientKey).forEach(b -> userPrescriptions.add(new UserPrescriptionsDTO(b)));
        return userPrescriptions;

}

    public void isChainValid() throws InvalidApplicationException {
        for(int i = 1; i< chain.count(); i++){
            Block currentBlock = chain.findByIndex(i);
            Block previousBlock = chain.findByIndex(i - 1);

            if(!currentBlock.getPreviousHash().equals(previousBlock.getHash())){
                log.log(Level.DEBUG, "Blockchain is corrupted, broken sequence");
                throw new InvalidApplicationException("Blockchain is corrupted, broken sequence");
            }

            if(!currentBlock.validateHash(currentBlock.getHash())){
                log.log(Level.DEBUG, "Blockchain is corrupted, invalid hash");
                throw new InvalidApplicationException("Blockchain is corrupted, invalid hash");
            }
        }
    }

    //TODO
    private void auditMinedBlock(Block newBlock) {
        log.log(Level.DEBUG, "-----------Block audition--------------");
        log.log(Level.DEBUG, newBlock);
    }

    private void addBlockToChain(Block newBlock) {
        chain.save(newBlock);
    }
}
