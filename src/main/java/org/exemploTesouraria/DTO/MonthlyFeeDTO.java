package org.exemploTesouraria.DTO;

import org.exemploTesouraria.model.MonthlyFee;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.exemploTesouraria.model.enums.PaymentStatus;

public class MonthlyFeeDTO {
    private Integer id;
    private String username;
    private MonthEnum  month;
    private PaymentStatus  paymentStatus;

    public MonthlyFeeDTO() {}

    public MonthlyFeeDTO(Integer id, String username, MonthEnum month, PaymentStatus paymentStatus) {
        this.id = id;
        this.username = username;
        this.month = month;
        this.paymentStatus = paymentStatus;
    }

    public MonthlyFeeDTO(MonthlyFee monthlyFee) {
       this.id = monthlyFee.getId();
       this.month = monthlyFee.getMonth();
       this.paymentStatus = monthlyFee.getPaymentStatus();
       this.username = monthlyFee.getUsers().getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MonthEnum getMonth() {
        return month;
    }

    public void setMonth(MonthEnum month) {
        this.month = month;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
