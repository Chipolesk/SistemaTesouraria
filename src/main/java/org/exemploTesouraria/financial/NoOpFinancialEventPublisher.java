package org.exemploTesouraria.financial;

import org.exemploTesouraria.model.enums.MonthEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class NoOpFinancialEventPublisher implements FinancialEventPublisher {
    @Override
    public void onCanteenCreated(Integer canteenId, LocalDate date, double valueSold, double expenses, double profit) {
        // Integração futura com ledger.
    }

    @Override
    public void onExtraCreated(Integer extraId, LocalDate date, double valueSold, double expenses, double profit) {
        // Integração futura com ledger.
    }

    @Override
    public void onDebtorPaid(Integer canteenId, String debtorName, double paidValue, double amountBefore, double amountAfter, boolean fullyPaid) {
        // Integração futura com ledger.
    }

    @Override
    public void onMonthlyFeePaid(Integer monthlyFeeId, String username, MonthEnum month, LocalDate paymentDate) {
        // Integração futura com ledger.
    }
}
