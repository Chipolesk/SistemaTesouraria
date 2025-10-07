package org.exemploTesouraria.model;

import jakarta.persistence.*;

@Entity
@Table
public class Debtors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debtors_id")
    private Integer id;

    private String nameDebtors;
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "id_canteen")
    private Canteen canteen;

    public Debtors() {}

    public Debtors(String nameDebtors, Double amount, Canteen canteen) {
        this.nameDebtors = nameDebtors;
        this.amount = amount;
        this.canteen = canteen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameDebtors() {
        return nameDebtors;
    }

    public void setNameDebtors(String nameDebtors) {
        this.nameDebtors = nameDebtors;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Canteen getCanteen() {
        return canteen;
    }

    public void setCanteen(Canteen canteen) {
        this.canteen = canteen;
    }
}
