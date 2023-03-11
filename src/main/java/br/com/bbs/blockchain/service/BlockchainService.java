package br.com.bbs.blockchain.service;

import br.com.bbs.blockchain.model.Block;
import br.com.bbs.blockchain.model.Prescription;
import br.com.bbs.blockchain.model.dto.CreatePrescriptionDTO;
import br.com.bbs.blockchain.model.dto.UserPrescriptionsDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InvalidApplicationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class BlockchainService {

    @Autowired
    private SecurityService securityService;
    private ArrayList<Block> chain;
    @Getter
    private ArrayList<Prescription> pendingPrescriptions;

    private Integer difficulty;

    public BlockchainService() {
        generateGenesisBlock();
        this.pendingPrescriptions = new ArrayList<>();
        this.difficulty = 1;
    }

    public ArrayList<Block> getFullChain(){
        return this.chain;
    }

    private void generateGenesisBlock() {
        this.chain = new ArrayList<>();
        chain.add(new Block(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")), new Prescription(), "0"));
    }

    private Block getChainLastBlock(){
        return this.chain.get(this.chain.size() - 1);
    }

    public boolean includePrescription(Prescription prescription){
        return this.pendingPrescriptions.add(prescription);
    }

    public String[] mineAllPendingBlocks(){

        String[] hashArray = new String[0];

        for(int i = 0; i<this.pendingPrescriptions.size(); i++){
            Block newBlock = new Block(
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")),
                    this.pendingPrescriptions.remove(this.pendingPrescriptions.size()-1),
                    getChainLastBlock().getHash()
            );
            newBlock.mineBlock(this.difficulty);
            addBlockToChain(newBlock);
            auditMinedBlock(newBlock); //TODO

            hashArray[i] = newBlock.getHash();
            this.difficulty += 1;
        }

        return hashArray;
    }

    public ArrayList<UserPrescriptionsDTO> getUserPrescriptions(String patientKey) throws InvalidApplicationException {
        isChainValid();

        int index = 1;
        ArrayList<UserPrescriptionsDTO> userPrescriptions = new ArrayList<>();

        for(Block block : this.chain){
            if(block.isValid(patientKey)){

                Prescription prescription = block.getPrescriptions();
                String medicine = prescription.getMedicine();
                String signature = securityService.encryptPrescription(block);

                userPrescriptions.add(
                        new UserPrescriptionsDTO(
                                index,
                                medicine,
                                signature
                        )
                );
            }
        }
        return userPrescriptions;

    }

    public boolean isChainValid() throws InvalidApplicationException {
        for(int i = 1; i<this.chain.size(); i++){
            Block currentBlock = this.chain.get(i);
            Block previousBlock = this.chain.get(i - 1);

            if(!currentBlock.getHash().equals(previousBlock.getHash())){
                throw new InvalidApplicationException("Blockchain is corrupted, broken sequence");
            }

            if(!currentBlock.validateHash(currentBlock.getHash())){
                throw new InvalidApplicationException("Blockchain is corrupted, invalid hash");
            }
        }
        return true;
    }

    //TODO
    private void auditMinedBlock(Block newBlock) {
        System.out.println("-----------Block audition--------------");
    }

    private boolean addBlockToChain(Block newBlock) {
        return this.chain.add(newBlock);
    }
}
