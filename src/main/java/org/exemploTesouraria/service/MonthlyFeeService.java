package org.exemploTesouraria.service;

import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.exemploTesouraria.model.MonthlyFee;
import org.exemploTesouraria.model.Users;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.exemploTesouraria.model.enums.PaymentStatus;
import org.exemploTesouraria.repository.MonthlyFeeRepository;
import org.exemploTesouraria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthlyFeeService {
    @Autowired
    private MonthlyFeeRepository monthlyFeeRepository;

    @Autowired
    private UserRepository userRepository;

    public MonthlyFee recordMonthly(String name, MonthEnum month, PaymentStatus status){
        Users users = userRepository.findByName(name)
                .orElseThrow(() -> ResourceNotFoundException.userNotFound(name));

        List<MonthlyFee> monthlyFeeExists = monthlyFeeRepository.findByUsers(users);

        boolean isExists = monthlyFeeExists.stream()
                .anyMatch(mensalidade -> mensalidade.getMonth() == month);

        if (isExists){
        throw new RuntimeException("Este usuario já pagou a mensalidade deste mês");
        }
        MonthlyFee insertMonthlyFee = new MonthlyFee();
        insertMonthlyFee.setUsers(users);
        insertMonthlyFee.setMonth(month);
        insertMonthlyFee.setPaymentStatus(status);

        return monthlyFeeRepository.save(insertMonthlyFee);
    }

    public String checkMonthlyFee(String name, MonthEnum month){
        Users users = userRepository.findByName(name)
                .orElseThrow(() -> ResourceNotFoundException.userNotFound(name));

        MonthlyFee monthlyFee = monthlyFeeRepository.findByUsersAndMonth(users, month)
                .orElseThrow(() -> ResourceNotFoundException.monthlyFeeNotFound(name, month.toString()));

        return users.getName() + " - " + monthlyFee.getMonth().toString() + " - " + monthlyFee.getPaymentStatus().toString();
    }


}
