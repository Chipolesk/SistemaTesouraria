package org.exemploTesouraria.DTO;

import org.exemploTesouraria.model.Canteen;

import java.time.LocalDate;
import java.util.List;

public record CanteenDTO(Integer id,
                         String food,
                         LocalDate dateCant,
                         String description,
                         String annotation,
                         double valueSold,
                         double expenses,
                         double profit,
                         List<DebtorDTO> debtors) {

    public static CanteenDTO fromEntity(Canteen canteen, double profit, List<DebtorDTO> debtors) {
        return new CanteenDTO(
                canteen.getId(),
                canteen.getFood(),
                canteen.getDateCant(),
                canteen.getDescription(),
                canteen.getAnnotations(),
                canteen.getValueSold(),
                canteen.getExpenses(),
                profit,
                debtors
        );
    }
}
