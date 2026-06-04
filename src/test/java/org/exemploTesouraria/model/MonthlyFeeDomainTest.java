package org.exemploTesouraria.model;

import org.exemploTesouraria.model.enums.MonthEnum;
import org.exemploTesouraria.model.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MonthlyFeeDomainTest {

    @Test
    void shouldAcceptYearAndCreationAmountSnapshot() {
        MonthlyFee monthlyFee = new MonthlyFee();

        monthlyFee.setMonth(MonthEnum.JUNHO);
        monthlyFee.setYear(2026);
        monthlyFee.setAmount(new BigDecimal("30.00"));
        monthlyFee.setPaymentStatus(PaymentStatus.EM_ABERTO);

        assertEquals(2026, monthlyFee.getYear());
        assertEquals(new BigDecimal("30.00"), monthlyFee.getAmount());
    }

    @Test
    void shouldRejectNegativeCreationAmount() {
        MonthlyFee monthlyFee = new MonthlyFee();

        assertThrows(IllegalArgumentException.class, () -> monthlyFee.setAmount(new BigDecimal("-0.01")));
    }
}
