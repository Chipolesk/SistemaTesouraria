package org.exemploTesouraria.model;

import org.exemploTesouraria.model.enums.SourceModule;
import org.exemploTesouraria.model.enums.TransactionType;
import org.exemploTesouraria.model.valueobject.TransactionReference;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TreasuryTransactionDomainTest {

    @Test
    void shouldCreateImmutableTreasuryTransactionWithPositiveAmount() {
        TreasuryTransaction transaction = TreasuryTransaction.register(
                TransactionType.MONTHLY_FEE,
                new BigDecimal("25.00"),
                "Monthly fee payment",
                LocalDate.of(2026, 6, 1),
                TransactionReference.of(SourceModule.MONTHLY_FEE, 10)
        );

        assertEquals(TransactionType.MONTHLY_FEE, transaction.getTransactionType());
        assertEquals(new BigDecimal("25.00"), transaction.getAmount().toBigDecimal());
        assertEquals(LocalDate.of(2026, 6, 1), transaction.getTransactionDate());
        assertNotNull(transaction.getCreatedAt());
    }

    @Test
    void shouldRejectZeroOrNegativeTreasuryTransactionAmount() {
        TransactionReference reference = TransactionReference.of(SourceModule.EXTRA, 1);

        assertThrows(IllegalArgumentException.class, () -> TreasuryTransaction.register(
                TransactionType.EXTRA_INCOME,
                BigDecimal.ZERO,
                "Extra income",
                LocalDate.of(2026, 6, 1),
                reference
        ));

        assertThrows(IllegalArgumentException.class, () -> TreasuryTransaction.register(
                TransactionType.EXTRA_EXPENSE,
                new BigDecimal("-1.00"),
                "Extra expense",
                LocalDate.of(2026, 6, 1),
                reference
        ));
    }
}
