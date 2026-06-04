package org.exemploTesouraria.model;

import jakarta.persistence.*;
import org.exemploTesouraria.model.valueobject.Money;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "monthly_fee_policy",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_monthly_fee_policy_year", columnNames = "policy_year")
        }
)
@Access(AccessType.FIELD)
public class MonthlyFeePolicy {
    private static final int MIN_POLICY_YEAR = 2000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_monthly_fee_policy", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "policy_year", nullable = false, updatable = false)
    private Integer year;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "amount", nullable = false, precision = 19, scale = 2))
    private Money amount;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected MonthlyFeePolicy() {
        // JPA only.
    }

    private MonthlyFeePolicy(Integer year, Money amount, boolean active) {
        this.year = validateYear(year);
        this.amount = Objects.requireNonNull(amount, "Monthly fee policy amount is required.");
        this.active = active;
        this.createdAt = OffsetDateTime.now();
    }

    public static MonthlyFeePolicy define(Integer year, BigDecimal amount) {
        return new MonthlyFeePolicy(year, Money.positive(amount), true);
    }

    public void deactivate() {
        this.active = false;
    }

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }

    public Integer getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public Money getAmount() {
        return amount;
    }

    public boolean isActive() {
        return active;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    private Integer validateYear(Integer year) {
        Objects.requireNonNull(year, "Monthly fee policy year is required.");
        if (year < MIN_POLICY_YEAR) {
            throw new IllegalArgumentException("Monthly fee policy year must be greater than or equal to " + MIN_POLICY_YEAR + ".");
        }
        return year;
    }
}
