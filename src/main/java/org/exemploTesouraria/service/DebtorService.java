package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.DebtorDTO;
import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.model.Debtors;
import org.exemploTesouraria.repository.CanteenRepository;
import org.exemploTesouraria.repository.DebtorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DebtorService {

    private final DebtorRepository debtorRepository;
    private final CanteenRepository canteenRepository;

    public DebtorService(DebtorRepository debtorRepository, CanteenRepository canteenRepository) {
        this.debtorRepository = debtorRepository;
        this.canteenRepository = canteenRepository;
    }

    public DebtorDTO registerDebtors(Integer idCanteen, String nameDebtor, Double amounth) {
        Canteen canteen = canteenRepository.findById(idCanteen)
                .orElseThrow(() -> new ResourceNotFoundException("Cantina não encontrada"));

        Debtors  debtors = new Debtors();
        debtors.setNameDebtors(nameDebtor);
        debtors.setAmount(amounth);
        debtors.setCanteen(canteen);

        Debtors savedDebtors = debtorRepository.save(debtors);
        return new DebtorDTO(savedDebtors.getNameDebtors(), savedDebtors.getAmount());
    }
    public List<DebtorDTO> getDebtorsByCanteen(Canteen canteen) {
        return debtorRepository.findByCanteen(canteen)
                .stream()
                .map(debtors -> new DebtorDTO(debtors.getNameDebtors(), debtors.getAmount()))
                .collect(Collectors.toList());
    }
    public void payDebtor(Integer idCanteen, String nameDebtor, Double value) {
        Canteen canteen = canteenRepository.findById(idCanteen)
                .orElseThrow(() -> new ResourceNotFoundException("Cantina não encontrada"));

        Debtors debtors = debtorRepository.findByNameDebtorsAndCanteen(nameDebtor, canteen)
                .orElseThrow(() -> new ResourceNotFoundException("Devedor não encontrado nesta cantina"));

        debtors.setAmount(debtors.getAmount() - value);

        if (debtors.getAmount() <= 0) {
            debtorRepository.delete(debtors);
            return; //aqui eu já fecho o metodo caso venha a deletar.
        }
        debtorRepository.save(debtors);

    }

}
