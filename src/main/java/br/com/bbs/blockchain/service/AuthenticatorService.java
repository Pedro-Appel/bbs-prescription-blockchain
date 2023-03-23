package br.com.bbs.blockchain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatorService {

    @Value("${server.appKey}")
    private String serverAppKey;

    public boolean checkAppKey(String appKey) {

        return appKey.equals(serverAppKey);

    }
}
