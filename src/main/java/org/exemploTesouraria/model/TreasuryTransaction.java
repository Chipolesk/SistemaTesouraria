package org.exemploTesouraria.model;

import jakarta.persistence.*;
import org.exemploTesouraria.model.enums.TransactionType;
import org.exemploTesouraria.model.valueobject.Money;
import org.exemploTesouraria.model.valueobject.TransactionReference;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "treasury_transaction")
@Access(AccessType.FIELD)
public class TreasuryTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_treasury_transaction", nullable = false, updatable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, updatable = false, length = 40)
    private TransactionType transactionType;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "amount", nullable = false, updatable = false, precision = 19, scale = 2))
    private Money amount;

    @Column(name = "description", nullable = false, updatable = false, length = 255)
    private String description;

    @Column(name = "transaction_date", nullable = false, updatable = false)
    private LocalDate transactionDate;

    @Embedded
    private TransactionReference reference;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected TreasuryTransaction() {
        // JPA only.
    }

    private TreasuryTransaction(TransactionType transactionType, Money amount, String description, LocalDate transactionDate, TransactionReference reference) {
        this.transactionType = Objects.requireNonNull(transactionType, "Transaction type is required.");
        this.amount = Objects.requireNonNull(amount, "Transaction amount is required.");
        this.description = normalizeDescription(description);
        this.transactionDate = Objects.requireNonNull(transactionDate, "Transaction date is required.");
        this.reference = Objects.requireNonNull(reference, "Transaction reference is required.");
        this.createdAt = OffsetDateTime.now();
    }

    public static TreasuryTransaction register(TransactionType transactionType, BigDecimal amount, String description, LocalDate transactionDate, TransactionReference reference) {
        return new TreasuryTransaction(transactionType, Money.positive(amount), description, transactionDate, reference);
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Money getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public TransactionReference getReference() {
        return reference;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    private String normalizeDescription(String description) {
        String normalizedDescription = Objects.requireNonNull(description, "Transaction description is required.").trim();
        if (normalizedDescription.isBlank()) {
            throw new IllegalArgumentException("Transaction description cannot be blank.");
        }
        return normalizedDescription;
    }
}
