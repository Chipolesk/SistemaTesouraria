package org.exemploTesouraria.model.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    private Money(BigDecimal amount) {
        this.amount = normalize(amount);
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money positive(BigDecimal amount) {
        Money money = new Money(amount);
        if (money.amount.signum() <= 0) {
            throw new IllegalArgumentException("Money amount must be greater than zero.");
        }
        return money;
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public BigDecimal toBigDecimal() {
        return amount;
    }

    private static BigDecimal normalize(BigDecimal value) {
        Objects.requireNonNull(value, "Money amount is required.");
        if (value.signum() < 0) {
            throw new IllegalArgumentException("Money amount cannot be negative.");
        }
        return value.setScale(2, RoundingMode.UNNECESSARY);
    }
}
