package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.ExtraDTO;
import org.exemploTesouraria.exception.DataConflictException;
import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.exemploTesouraria.model.Extras;
import org.exemploTesouraria.repository.ExtraRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtraService {

    private final ExtraRepository extraRepository;

    public ExtraService(ExtraRepository extraRepository) {
        this.extraRepository = extraRepository;
    }

    public ExtraDTO createExtra(double valueSold, double expenses, String description, LocalDate dateExtra) {
        if (extraRepository.findByDateExtra(dateExtra).isPresent()) {
            throw DataConflictException.extraAlreadyExist(dateExtra);
        }

        Extras extra = new Extras();
        extra.setValueSold(valueSold);
        extra.setExpenses(expenses);
        extra.setDescription(description);
        extra.setDateExtra(dateExtra);

        return ExtraDTO.fromEntity(extraRepository.save(extra));
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
}
