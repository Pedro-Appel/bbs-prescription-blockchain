package br.com.bbs.blockchain.service;

import br.com.bbs.blockchain.model.Prescription;

public interface PendingPrescriptionsRepository {

    boolean save(Prescription prescription);
    boolean isEmpty();
    int size();
    Prescription removeLast();
}
