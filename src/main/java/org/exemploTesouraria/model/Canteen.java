package org.exemploTesouraria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @NotNull(message = "A data da cantina é obrigatória")
    //data da canteen
    private LocalDate dateCant;

    @OneToMany(mappedBy = "canteen",cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Debtors> debtors = new ArrayList<>();

}
