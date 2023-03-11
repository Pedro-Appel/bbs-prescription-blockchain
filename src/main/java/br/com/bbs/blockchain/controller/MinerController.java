package br.com.bbs.blockchain.controller;

import br.com.bbs.blockchain.model.dto.VerifyDataDTO;
import br.com.bbs.blockchain.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/miner")
public class MinerController {
    @Autowired
    BlockchainService blockchain;
    @RequestMapping(method = RequestMethod.GET, path = "/heathCheck")
    public String heathCheck(){
        return "STATUS UP";
    }
    @RequestMapping(method = RequestMethod.GET)
    public String mineAllBlocks(){
        return "Success";
    }
    @RequestMapping(method = RequestMethod.GET, path = "/blocks")
    public Integer getBlocksPending(){

        return blockchain.getPendingPrescriptions().size();
    }
}
