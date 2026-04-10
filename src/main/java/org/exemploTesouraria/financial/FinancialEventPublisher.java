package org.exemploTesouraria.financial;

import org.exemploTesouraria.model.enums.MonthEnum;

import java.time.LocalDate;

public interface FinancialEventPublisher {
    void onCanteenCreated(Integer canteenId, LocalDate date, double valueSold, double expenses, double profit);

    void onExtraCreated(Integer extraId, LocalDate date, double valueSold, double expenses, double profit);

    void onDebtorPaid(Integer canteenId, String debtorName, double paidValue, double amountBefore, double amountAfter, boolean fullyPaid);

    void onMonthlyFeePaid(Integer monthlyFeeId, String username, MonthEnum month, LocalDate paymentDate);
}
