package org.exemploTesouraria.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.exemploTesouraria.model.enums.MonthEnum;


public record CreateMonthlyFeeRequestDTO(
        @NotBlank(message = "O nome do usuário é obrigatório!")
        String username,
        @NotNull(message = "O mês é obrigatório.")
        MonthEnum month) {
}
