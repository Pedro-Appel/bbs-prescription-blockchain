package br.com.bbs.blockchain.controller;

import br.com.bbs.blockchain.model.Block;
import br.com.bbs.blockchain.service.AuthenticatorService;
import br.com.bbs.blockchain.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController()
@RequestMapping("/chain")
public class BlockchainController {

    @Autowired
    AuthenticatorService authenticatorService;
    @Autowired
    BlockchainService blockchain;

    @RequestMapping(method = RequestMethod.GET, path = "/heathCheck")
    public String heathCheck(@RequestParam() String appKey){
        if(!authenticatorService.checkAppkey(appKey)) return null;

        return "STATUS UP";
    }
    @RequestMapping(method = RequestMethod.GET)
    public ArrayList<Block> getChain(@RequestParam() String appKey){
        if(!authenticatorService.checkAppkey(appKey)) return null;

        return blockchain.getFullChain();
    }
    @RequestMapping(method = RequestMethod.GET, path = "/blocks")
    public String listPendingBlocks(@RequestParam() String appKey){
        if(!authenticatorService.checkAppkey(appKey)) return null;
        return "Success";
    }
    @RequestMapping(method = RequestMethod.GET, path = "/audition")
    public String getAudition(@RequestParam() String appKey){
        if(!authenticatorService.checkAppkey(appKey)) return null;
        return "Success";
    }
    @RequestMapping(method = RequestMethod.GET, path = "/fullAudition")
    public String getAuditionChain(@RequestParam() String appKey){
        if(!authenticatorService.checkAppkey(appKey)) return null;
        return "Success";
    }

    @GetMapping("/{key}/prescription")
    public String getUserPrescriptions(@PathVariable String key){
        return "Success";
    }

}
