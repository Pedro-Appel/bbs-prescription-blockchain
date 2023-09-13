package br.com.bbs.blockchain.model.dto;

public record VerifyData(
        String privateKey,
        String prescriptionHex
) {}
