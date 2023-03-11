package br.com.bbs.blockchain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatorService {

    @Value("${server.appKey}")
    private String serverAppKey;

    public boolean checkAppkey(String appKey) {
        System.out.println("Request appKey: "+ appKey + " server app Key: "+ serverAppKey);
        return appKey.equals(serverAppKey);
    }
}
