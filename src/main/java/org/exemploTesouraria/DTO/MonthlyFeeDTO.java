package org.exemploTesouraria.DTO;

import org.exemploTesouraria.model.MonthlyFee;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.exemploTesouraria.model.enums.PaymentStatus;

public record MonthlyFeeDTO(Integer id,
                           String username,
                           MonthEnum  month,
                           PaymentStatus  paymentStatus) {

    public static MonthlyFeeDTO fromEntity(MonthlyFee monthlyFee) {
       return new MonthlyFeeDTO(monthlyFee.getId(), monthlyFee.getUsers().getName(), monthlyFee.getMonth(), monthlyFee.getPaymentStatus());
    }


}
