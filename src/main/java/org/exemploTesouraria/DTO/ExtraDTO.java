package org.exemploTesouraria.DTO;

import org.exemploTesouraria.model.Extras;

import java.time.LocalDate;

public record ExtraDTO(
        Integer id,
        double valueSold,
        double expenses,
        double profit,
        String description,
        LocalDate dateExtra
) {

    public static ExtraDTO fromEntity(Extras extra) {
        return new ExtraDTO(
                extra.getId(),
                extra.getValueSold(),
                extra.getExpenses(),
                extra.getValueSold() - extra.getExpenses(),
                extra.getDescription(),
                extra.getDateExtra()
        );
    }
}
