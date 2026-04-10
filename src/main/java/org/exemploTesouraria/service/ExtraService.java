package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.ExtraDTO;
import org.exemploTesouraria.exception.DataConflictException;
import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.exemploTesouraria.financial.FinancialEventPublisher;
import org.exemploTesouraria.model.Extras;
import org.exemploTesouraria.repository.ExtraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtraService.class);

    private final ExtraRepository extraRepository;
    private final FinancialEventPublisher financialEventPublisher;

    public ExtraService(ExtraRepository extraRepository, FinancialEventPublisher financialEventPublisher) {
        this.extraRepository = extraRepository;
        this.financialEventPublisher = financialEventPublisher;
    }

    @Transactional
    public ExtraDTO createExtra(double valueSold, double expenses, String description, LocalDate dateExtra) {
        if (extraRepository.findByDateExtra(dateExtra).isPresent()) {
            throw DataConflictException.extraAlreadyExist(dateExtra);
        }

        Extras extra = new Extras();
        extra.setValueSold(valueSold);
        extra.setExpenses(expenses);
        extra.setDescription(description);
        extra.setDateExtra(dateExtra);

        Extras savedExtra = extraRepository.save(extra);
        double profit = calculateProfit(savedExtra.getValueSold(), savedExtra.getExpenses());
        publishSafely(() -> financialEventPublisher.onExtraCreated(savedExtra.getId(), savedExtra.getDateExtra(), savedExtra.getValueSold(), savedExtra.getExpenses(), profit), "onExtraCreated", savedExtra.getId());

        return ExtraDTO.fromEntity(savedExtra);
    }

    public List<ExtraDTO> findAllExtras() {
        return extraRepository.findAll()
                .stream()
                .map(ExtraDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ExtraDTO findByDateExtra(LocalDate dateExtra) {
        Extras extra = extraRepository.findByDateExtra(dateExtra)
                .orElseThrow(() -> ResourceNotFoundException.extraNotFound(dateExtra));

        return ExtraDTO.fromEntity(extra);
    }

    public List<ExtraDTO> findByMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Mês inexistente");
        }

        int year = LocalDate.now().getYear();
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return extraRepository.findByDateExtraBetween(start, end)
                .stream()
                .map(ExtraDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private double calculateProfit(double valueSold, double expenses) {
        return valueSold - expenses;
    }

    private void publishSafely(Runnable publisherCall, String action, Integer extraId) {
        try {
            publisherCall.run();
        } catch (Exception ex) {
            LOGGER.error("Failed to publish financial event '{}' for extraId={}", action, extraId, ex);
            // TODO: persist failed financial event for retry (outbox pattern)
        }
    }
}
