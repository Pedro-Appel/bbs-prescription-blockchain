package br.com.bbs.blockchain.service.impl;

import br.com.bbs.blockchain.model.Prescription;
import br.com.bbs.blockchain.service.PendingPrescriptionsRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryPendingPrescription implements PendingPrescriptionsRepository {

    private final List<Prescription> pendingPrescriptions;

    public InMemoryPendingPrescription() {
        this.pendingPrescriptions = new ArrayList<>();
    }

    @Override
    public boolean save(Prescription prescription) {
        return pendingPrescriptions.add(prescription);
    }

    @Override
    public boolean isEmpty() {
        return pendingPrescriptions.isEmpty();
    }

    @Override
    public int size() {
        return pendingPrescriptions.size();
    }

    @Override
    public Prescription removeLast() {
        return pendingPrescriptions.remove(size() - 1);
    }
}
