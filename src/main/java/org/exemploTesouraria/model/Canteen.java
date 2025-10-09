package org.exemploTesouraria.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Canteen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_canteen", nullable = false)
    private Integer id;

    @Column(name = "descript_canteen")
    //descrição do que é a cantina juntamente com valor
    private String description;

    @Column(name = "food_canteen")
    //alimento da canteen
    private String food;

    @Column(name = "annotations_canteen")
    //anotações
    private String annotations;

    @Column(name = "valueSold")
    //valor vendido na canteen
    private double valueSold;

    @Column(name = "expenses_canteen")
    //gastos na canteen
    private double expenses;

    @Column(name = "profit_canteen")
    //lucro obtido na canteen
    private double profit;

    @Column(name = "date_canteen", nullable = false, unique = true)
    //data da canteen
    private LocalDate dateCant;

    @OneToMany(mappedBy = "canteen",cascade = CascadeType.ALL)
    private List<Debtors> debtors = new ArrayList<>();


    public Integer getId() {
        return id;
    }


    public List<Debtors> getDebtors() {
        return debtors;
    }

    public void setDebtors(List<Debtors> debtors) {
        this.debtors = debtors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
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

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public LocalDate getDateCant() {
        return dateCant;
    }

    public void setDateCant(LocalDate dateCant) {
        this.dateCant = dateCant;
    }
}
