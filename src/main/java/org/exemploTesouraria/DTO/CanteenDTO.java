package org.exemploTesouraria.DTO;

import org.exemploTesouraria.model.Canteen;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CanteenDTO {
    private Integer id;
    private String food;
    private LocalDate dateCant;
    private String description;
    private String annotation;
    private double valueSold;
    private double expenses;
    private double profit;
    private List<DebtorDTO> debtors = new ArrayList<>();


    public CanteenDTO(Canteen canteen) {
        this.id = canteen.getId();
        this.food = canteen.getFood();
        this.dateCant = canteen.getDateCant();
        this.description = canteen.getDescription();
        this.annotation = canteen.getAnnotations();
        this.valueSold = canteen.getValueSold();
        this.expenses = canteen.getExpenses();
        this.profit = canteen.getProfit();
        this.debtors = canteen.getDebtors()
                                .stream()
                                .map(debt -> new DebtorDTO(debt.getAmount(), debt.getNameDebtors()))
                                .collect(Collectors.toList());
    }
}
