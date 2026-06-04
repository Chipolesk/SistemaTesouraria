package org.exemploTesouraria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.exemploTesouraria.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "monthlyFee",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_monthly_fee_user_month_year", columnNames = {"id_user", "month_fee", "year_fee"})
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyFee {
    public static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_monthlyFee", nullable = false)
    private Integer id;

    //faz com que o banco salve o enum pelas strings e nao pelos indices
    @Enumerated(EnumType.STRING)
    @Column(name = "month_fee", nullable = false)
    @NotNull(message = "o mês é obrigatório!")
    @Setter
    private MonthEnum month;

    @Column(name = "year_fee", nullable = false)
    @NotNull(message = "o ano é obrigatório!")
    @PositiveOrZero(message = "O ano deve ser positivo.")
    @Setter
    private Integer year;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2, updatable = false)
    @NotNull(message = "O valor da mensalidade é obrigatório.")
    @DecimalMin(value = "0.00", inclusive = true, message = "O valor da mensalidade não pode ser negativo.")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Informe o status de pagamento.")
    @Setter
    private PaymentStatus paymentStatus;

    @Column(name = "payment_date")
    @Setter
    private LocalDate paymentDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user")
    @Setter
    private Users users;

    public void setAmount(BigDecimal amount) {
        if (this.amount != null && amount != null && this.id != null && this.amount.compareTo(amount) != 0) {
            throw new IllegalStateException("MonthlyFee.amount is immutable after creation.");
        }
        this.amount = normalizeAmount(amount);
    }

    @PrePersist
    void prePersist() {
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        if (amount == null) {
            amount = DEFAULT_AMOUNT;
        }
        amount = normalizeAmount(amount);
    }

    @PreUpdate
    void preUpdate() {
        amount = normalizeAmount(amount);
    }

    private BigDecimal normalizeAmount(BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("MonthlyFee.amount cannot be negative.");
        }
        return amount.setScale(2, java.math.RoundingMode.UNNECESSARY);
    }
}
