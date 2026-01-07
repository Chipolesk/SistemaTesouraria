package org.exemploTesouraria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Extras{

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id_extra", nullable = false)
private Integer id;

private double expenses;

private double valueSold;

private String description;

@Column(name = "date_extra", nullable = false, unique = true)
@NotNull(message = "A data é obrigatória")
private LocalDate dateExtra;

}
