package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.CanteenDTO;
import org.exemploTesouraria.DTO.DebtorDTO;
import org.exemploTesouraria.DTO.DebtorWithCanteenDTO;
import org.exemploTesouraria.exception.DataConflictException;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CanteenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CanteenService.class);

    private final CanteenRepository canteenRepository;
    private final DebtorRepository debtorRepository;
    private final FinancialEventPublisher financialEventPublisher;

    public CanteenService(CanteenRepository canteenRepository,
                          DebtorRepository debtorRepository,
                          FinancialEventPublisher financialEventPublisher) {
        this.canteenRepository = canteenRepository;
        this.debtorRepository = debtorRepository;
        this.financialEventPublisher = financialEventPublisher;
    }

    @Transactional
    public CanteenDTO createCanteen(String food, String description, double valueSold, LocalDate dateCant, double expenses, String annotations, List<DebtorDTO> debtorDTO) {
        Canteen insertCanteen = new Canteen();
        List<DebtorDTO> debtors = debtorDTO == null ? Collections.emptyList() : debtorDTO;

        if (canteenRepository.findByDateCant(dateCant).isPresent()) {
            throw DataConflictException.canteenAlreadyExist(dateCant);
        }

        insertCanteen.setDateCant(dateCant);
        insertCanteen.setFood(food);
        insertCanteen.setDescription(description);
        insertCanteen.setValueSold(valueSold);
        insertCanteen.setAnnotations(annotations);
        insertCanteen.setExpenses(expenses);

        for (DebtorDTO dto : debtors) {
            Debtors debtor = new Debtors();
            debtor.setNameDebtors(dto.name());
            debtor.setCanteen(insertCanteen);
            debtor.setAmount(dto.amount());
            insertCanteen.getDebtors().add(debtor);
        }

        Canteen saved = canteenRepository.save(insertCanteen);
        double profit = calculateProfit(saved.getValueSold(), saved.getExpenses());
        publishSafely(() -> financialEventPublisher.onCanteenCreated(saved.getId(), saved.getDateCant(), saved.getValueSold(), saved.getExpenses(), profit), "onCanteenCreated", saved.getId());

        return toDTO(saved);
    }

    public List<CanteenDTO> findAllCanteens() {
        return canteenRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CanteenDTO findByDateCant(LocalDate dateCant) {
        Canteen canteen = canteenRepository.findByDateCant(dateCant)
                .orElseThrow(() -> ResourceNotFoundException.CanteenNotFound(dateCant));
        return toDTO(canteen);
    }

    public List<CanteenDTO> findByMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Mês inválido. Informe um valor entre 1 e 12.");
        }

        int year = LocalDate.now().getYear();
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return canteenRepository.findByDateCantBetween(start, end)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<DebtorWithCanteenDTO> findDebtorsWithCanteenInfo(String nameDebtors) {
        List<Debtors> debtors = debtorRepository.findByNameDebtors(nameDebtors);

        if (debtors.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum devedor encontrado com este nome: " + nameDebtors);
        }

        return debtors.stream()
                .map(debt -> new DebtorWithCanteenDTO(debt.getNameDebtors(), debt.getAmount(), debt.getCanteen().getDateCant()))
                .collect(Collectors.toList());
    }

    private CanteenDTO toDTO(Canteen canteen) {
        List<DebtorDTO> debtors = canteen.getDebtors()
                .stream()
                .map(debt -> new DebtorDTO(debt.getNameDebtors(), debt.getAmount()))
                .toList();

        return CanteenDTO.fromEntity(canteen, calculateProfit(canteen.getValueSold(), canteen.getExpenses()), debtors);
    }

    private double calculateProfit(double valueSold, double expenses) {
        return valueSold - expenses;
    }

    private void publishSafely(Runnable publisherCall, String action, Integer canteenId) {
        try {
            publisherCall.run();
        } catch (Exception ex) {
            LOGGER.error("Failed to publish financial event '{}' for canteenId={}", action, canteenId, ex);
            // TODO: persist failed financial event for retry (outbox pattern)
        }
    }
}
