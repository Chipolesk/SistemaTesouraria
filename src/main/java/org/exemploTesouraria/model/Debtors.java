package org.exemploTesouraria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "debtors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Debtors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debtors_id", nullable = false)
    private Integer id;

    private String nameDebtors;

    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_canteen")
    private Canteen canteen;


}
