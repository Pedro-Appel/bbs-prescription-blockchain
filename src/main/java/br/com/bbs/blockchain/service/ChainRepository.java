package br.com.bbs.blockchain.service;

import br.com.bbs.blockchain.model.Block;

import java.util.List;
import java.util.Optional;

public interface ChainRepository {
    void save(Block entity);
    Block findLast();
    List<Block> findAll();
    List<Block> findAllById(String patientKey);
    int count();
    Block findByIndex(int i);
}
