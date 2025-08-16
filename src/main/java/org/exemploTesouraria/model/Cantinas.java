package org.exemploTesouraria.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Cantinas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cantina", nullable = false)
    private Integer id;

    @Column(name = "descript_cantina")
    //descrição do que é a cantina juntamente com valor
    private String description;

    @Column(name = "food_cantina")
    //alimento da cantina
    private String food;

    @Column(name = "annotations_cantina")
    //anotações, como alguma divida pendente
    private String annotations;

    @Column(name = "valueSold")
    //valor vendido na cantina
    private double valueSold;

    @Column(name = "expenses_cantina")
    //gastos na cantina
    private double expenses;

    @Column(name = "profit_cantina")
    //lucro obtido na cantina
    private double profit;

    @Column(name = "date_cantina", nullable = false)
    //data da cantina
    private Date dateCant;


    public Integer getId() {
        return id;
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

    public Date getDateCant() {
        return dateCant;
    }

    public void setDateCant(Date dateCant) {
        this.dateCant = dateCant;
    }
}
