package org.exemploTesouraria.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MonthlyFeePolicyDomainTest {

    @Test
    void shouldDefineActivePolicyForYearAndPositiveAmount() {
        MonthlyFeePolicy policy = MonthlyFeePolicy.define(2026, new BigDecimal("30.00"));

        assertEquals(2026, policy.getYear());
        assertEquals(new BigDecimal("30.00"), policy.getAmount().toBigDecimal());
        assertTrue(policy.isActive());
        assertNotNull(policy.getCreatedAt());
    }

    @Test
    void shouldRejectInvalidPolicyData() {
        assertThrows(NullPointerException.class, () -> MonthlyFeePolicy.define(null, new BigDecimal("30.00")));
        assertThrows(IllegalArgumentException.class, () -> MonthlyFeePolicy.define(1999, new BigDecimal("30.00")));
        assertThrows(IllegalArgumentException.class, () -> MonthlyFeePolicy.define(2026, BigDecimal.ZERO));
    }
}
