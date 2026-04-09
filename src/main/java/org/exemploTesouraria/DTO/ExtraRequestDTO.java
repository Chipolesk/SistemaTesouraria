package org.exemploTesouraria.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExtraRequestDTO(
        double valueSold,
        double expenses,
        String description,
        @NotNull LocalDate dateExtra
) {}
