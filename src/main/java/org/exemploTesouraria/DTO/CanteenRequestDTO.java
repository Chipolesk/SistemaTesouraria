package org.exemploTesouraria.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CanteenRequestDTO {
    private String food;
    private LocalDate dateCant;
    private String description;
    private String annotation;
    private double valueSold;
    private double expenses;
    private List<DebtorDTO> debtors = new ArrayList<>();

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public LocalDate getDateCant() {
        return dateCant;
    }

    public void setDateCant(LocalDate dateCant) {
        this.dateCant = dateCant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public double getValueSold() {
        return valueSold;
    }

    public void setValueSold(double valueSold) {
        this.valueSold = valueSold;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public List<DebtorDTO> getDebtors() {
        return debtors;
    }

    public void setDebtors(List<DebtorDTO> debtors) {
        this.debtors = debtors;
    }
}
