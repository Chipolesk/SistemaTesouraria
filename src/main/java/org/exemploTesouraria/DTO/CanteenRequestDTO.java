package org.exemploTesouraria.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.List;

public record CanteenRequestDTO(
     @NotBlank(message = "O campo 'food' é obrigatório.")
     String food,
     @NotNull(message = "O campo 'dateCant' é obrigatório.")
     LocalDate dateCant,
     String description,
     String annotation,
     @PositiveOrZero(message = "O campo 'valueSold' não pode ser negativo.")
     double valueSold,
     @PositiveOrZero(message = "O campo 'expenses' não pode ser negativo.")
     double expenses,
    List<DebtorDTO> debtors) {}
