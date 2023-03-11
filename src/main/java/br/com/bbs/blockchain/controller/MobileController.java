package br.com.bbs.blockchain.controller;

import br.com.bbs.blockchain.model.Prescription;
import br.com.bbs.blockchain.model.dto.CreatePrescriptionDTO;
import br.com.bbs.blockchain.model.dto.VerifyDataDTO;
import br.com.bbs.blockchain.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/patient")
public class MobileController {

    @Autowired
    BlockchainService blockchain;

    @RequestMapping(method = RequestMethod.POST)
    public Boolean addBlockToValidation(@RequestBody() CreatePrescriptionDTO createPrescriptionDTO){

        Prescription prescription = new Prescription(createPrescriptionDTO);

        return blockchain.includePrescription(prescription);
    }
    @RequestMapping(method = RequestMethod.GET, path = "/heathCheck")
    public String heathCheck(){
        return "STATUS UP";
    }
    @RequestMapping(method = RequestMethod.GET, path = "/keyExchange")
    public String keyExchange(){
        return "Success";
    }
    @RequestMapping(method = RequestMethod.GET, path = "/prescription")
    public String verifyDataEncryption(@RequestBody() VerifyDataDTO verifyData){
        return "Success";
    }
}
