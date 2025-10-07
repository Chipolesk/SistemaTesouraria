package org.exemploTesouraria.service;

import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.model.Debtors;
import org.exemploTesouraria.repository.CanteenRepository;
import org.exemploTesouraria.repository.DebtorRepository;
import org.springframework.stereotype.Service;

@Service
public class DebtorService {

    private DebtorRepository debtorRepository;
    private CanteenRepository canteenRepository;

    public Debtors registerDebtors(Integer idCanteen, String nameDebtor, Double amounth) {
        Canteen canteen = canteenRepository.findById(idCanteen)
                .orElseThrow(() -> new ResourceNotFoundException("Cantina não encontrada"));

        Debtors  debtors = new Debtors();
        debtors.setNameDebtors(nameDebtor);
        debtors.setAmount(amounth);
        debtors.setCanteen(canteen);

        return debtorRepository.save(debtors);
    }
}
