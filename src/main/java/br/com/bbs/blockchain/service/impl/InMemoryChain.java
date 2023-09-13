package br.com.bbs.blockchain.service.impl;

import br.com.bbs.blockchain.model.Block;
import br.com.bbs.blockchain.service.ChainRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryChain implements ChainRepository {

    private final List<Block> chainList;

    public InMemoryChain() {
        this.chainList = new ArrayList<>();
    }

    public void save(Block entity) {
        chainList.add(entity);
    }

    public Block findLast() {
        return chainList.get(count() - 1);
    }

    public List<Block> findAll() {
        return chainList;
    }

    public List<Block> findAllById(String patientKey) {
        return chainList.stream().filter(block -> block.validate(patientKey)).collect(Collectors.toList());
    }

    @Override
    public Block findByIndex(int i) {
        return chainList.get(i);
    }

    public int count() {
        return chainList.size();
    }
}
