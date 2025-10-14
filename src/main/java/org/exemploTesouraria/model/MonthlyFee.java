package org.exemploTesouraria.model;

import jakarta.persistence.*;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.exemploTesouraria.model.enums.PaymentStatus;

@Entity
@Table(name = "monthlyFee")
public class MonthlyFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_monthlyFee")
    private Integer id;

    //faz com que o banco salve o enum pelas strings e nao pelos indices
    @Enumerated(EnumType.STRING)
    @Column(name = "_month", nullable = false)
    private MonthEnum month;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private Users users;

    public Integer getId() {
        return id;
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
