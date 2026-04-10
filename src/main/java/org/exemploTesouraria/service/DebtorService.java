package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.DebtorDTO;
import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.exemploTesouraria.financial.FinancialEventPublisher;
import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.model.Debtors;
import org.exemploTesouraria.repository.CanteenRepository;
import org.exemploTesouraria.repository.DebtorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DebtorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebtorService.class);

    private final DebtorRepository debtorRepository;
    private final CanteenRepository canteenRepository;
    private final FinancialEventPublisher financialEventPublisher;

    public DebtorService(DebtorRepository debtorRepository,
                         CanteenRepository canteenRepository,
                         FinancialEventPublisher financialEventPublisher) {
        this.debtorRepository = debtorRepository;
        this.canteenRepository = canteenRepository;
        this.financialEventPublisher = financialEventPublisher;
    }

    @Transactional
    public DebtorDTO createDebtors(Integer idCanteen, String nameDebtor, Double amounth) {
        Canteen canteen = canteenRepository.findById(idCanteen)
                .orElseThrow(() -> new ResourceNotFoundException("Cantina não encontrada"));

        Debtors debtors = new Debtors();
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

    @Transactional
    public void payDebtor(Integer idCanteen, String nameDebtor, Double value) {
        Canteen canteen = canteenRepository.findById(idCanteen)
                .orElseThrow(() -> new ResourceNotFoundException("Cantina não encontrada"));

        Debtors debtors = debtorRepository.findByNameDebtorsAndCanteen(nameDebtor, canteen)
                .orElseThrow(() -> new ResourceNotFoundException("Devedor não encontrado nesta cantina"));

        double amountBeforePayment = debtors.getAmount();
        debtors.setAmount(debtors.getAmount() - value);

        if (debtors.getAmount() <= 0) {
            debtorRepository.delete(debtors);
            publishSafely(() -> financialEventPublisher.onDebtorPaid(canteen.getId(), nameDebtor, value, amountBeforePayment, 0.0, true), "onDebtorPaid", canteen.getId(), nameDebtor);
            return;
        }

        Debtors updatedDebtor = debtorRepository.save(debtors);
        publishSafely(() -> financialEventPublisher.onDebtorPaid(canteen.getId(), nameDebtor, value, amountBeforePayment, updatedDebtor.getAmount(), false), "onDebtorPaid", canteen.getId(), nameDebtor);
    }

    private void publishSafely(Runnable publisherCall, String action, Integer canteenId, String debtorName) {
        try {
            publisherCall.run();
        } catch (Exception ex) {
            LOGGER.error("Failed to publish financial event '{}' for canteenId={} debtor={}", action, canteenId, debtorName, ex);
            // TODO: persist failed financial event for retry (outbox pattern)
        }
    }
}
