package org.exemploTesouraria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.exemploTesouraria.model.enums.PaymentStatus;

import java.time.LocalDate;

@Entity
@Table(name = "monthlyFee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_monthlyFee", nullable = false)
    private Integer id;

    //faz com que o banco salve o enum pelas strings e nao pelos indices
    @Enumerated(EnumType.STRING)
    @Column(name = "month", nullable = false)
    @NotNull(message = "o mês é obrigatório!")
    private MonthEnum month;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Informe o status de pagamento.")
    private PaymentStatus paymentStatus;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user")
    private Users users;


}
