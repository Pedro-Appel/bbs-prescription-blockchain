package br.com.bbs.blockchain.service;

import br.com.bbs.blockchain.model.Block;
import br.com.bbs.blockchain.model.Prescription;
import br.com.bbs.blockchain.model.dto.UserPrescriptionsDTO;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

import javax.management.InvalidApplicationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class BlockchainService {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private ArrayList<Block> chain;
    @Getter
    private ArrayList<Prescription> pendingPrescriptions;

    private Integer difficulty;

    public BlockchainService() throws InvalidApplicationException {
        generateGenesisBlock();
        this.pendingPrescriptions = new ArrayList<>();
        this.difficulty = 1;
    }

    public List<Block> getFullChain(){
        return this.chain;
    }

    private void generateGenesisBlock() throws InvalidApplicationException {
        this.chain = new ArrayList<>();
        chain.add(new Block(LocalDateTime.now().format(FORMATTER), new Prescription(), "0"));
    }

    private Block getChainLastBlock(){
        return this.chain.get(this.chain.size() - 1);
    }

    public boolean includePrescription(Prescription prescription){
        return this.pendingPrescriptions.add(prescription);
    }

    public List<String> mineAllPendingBlocks() throws InvalidApplicationException {

        List<String> hashArray = new ArrayList<>();

        if(this.pendingPrescriptions.isEmpty()) return hashArray;

        for(int i = this.pendingPrescriptions.size(); i > 0; i--){
            Block newBlock = new Block(
                    LocalDateTime.now().format(FORMATTER),
                    this.pendingPrescriptions.remove(this.pendingPrescriptions.size()-1),
                    getChainLastBlock().getHash()
            );
            newBlock.mineBlock(this.difficulty);
            addBlockToChain(newBlock);
            auditMinedBlock(newBlock); //TODO

            hashArray.add(newBlock.getHash());
            this.difficulty += 1;
        }

        return hashArray;
    }

    public List<UserPrescriptionsDTO> getUserPrescriptions(String patientKey) throws InvalidApplicationException {

        isChainValid();

        ArrayList<UserPrescriptionsDTO> userPrescriptions = new ArrayList<>();

        for(Block block : this.chain){
            if(block.getPrescriptions().isValid(patientKey)){

                Prescription prescription = block.getPrescriptions();
                String medicine = prescription.getMedicine();

                userPrescriptions.add(new UserPrescriptionsDTO(medicine, block.getPrescriptions().getSignature()));
            }
        }
        if(userPrescriptions.isEmpty()){
            return Collections.emptyList();
        }
        return userPrescriptions;

    }

    public boolean isChainValid() throws InvalidApplicationException {
        for(int i = 1; i<this.chain.size(); i++){
            Block currentBlock = this.chain.get(i);
            Block previousBlock = this.chain.get(i - 1);

            if(!currentBlock.getPreviousHash().equals(previousBlock.getHash())){
                log.log(Level.DEBUG, "Blockchain is corrupted, broken sequence");
                throw new InvalidApplicationException("Blockchain is corrupted, broken sequence");
            }

            if(!currentBlock.validateHash(currentBlock.getHash())){
                log.log(Level.DEBUG, "Blockchain is corrupted, invalid hash");
                throw new InvalidApplicationException("Blockchain is corrupted, invalid hash");
            }
        }
        return true;
    }

    //TODO
    private void auditMinedBlock(Block newBlock) {
        log.log(Level.DEBUG, "-----------Block audition--------------");
        log.log(Level.DEBUG, newBlock);
    }

    private boolean addBlockToChain(Block newBlock) {
        return this.chain.add(newBlock);
    }
}
