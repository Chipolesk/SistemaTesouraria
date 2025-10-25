package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.MonthlyFeeDTO;
import org.exemploTesouraria.DTO.UserDTO;
import org.exemploTesouraria.exception.DataConflictException;
import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.exemploTesouraria.model.MonthlyFee;
import org.exemploTesouraria.model.Users;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.exemploTesouraria.model.enums.PaymentStatus;
import org.exemploTesouraria.repository.MonthlyFeeRepository;
import org.exemploTesouraria.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MonthlyFeeService {

    private final MonthlyFeeRepository monthlyFeeRepository;
    private final UserRepository userRepository;

    public MonthlyFeeService(MonthlyFeeRepository monthlyFeeRepository, UserRepository userRepository) {
        this.monthlyFeeRepository = monthlyFeeRepository;
        this.userRepository = userRepository;
    }

    public MonthlyFeeDTO recordMonthly(String name, MonthEnum month, PaymentStatus status){
        Users users = userRepository.findByName(name)
                .orElseThrow(() -> ResourceNotFoundException.userNotFound(name));

        List<MonthlyFee> monthlyFeeExists = monthlyFeeRepository.findByUsers(users);

        MonthlyFee isExists = monthlyFeeExists.stream()
                .filter(mensalidade -> mensalidade.getMonth() == month)
                .findFirst()
                .orElse(null);

        if (isExists != null){
            throw DataConflictException.MonthlyFeeAlreadyExist(isExists);
        }
        MonthlyFee insertMonthlyFee = new MonthlyFee();
        insertMonthlyFee.setUsers(users);
        insertMonthlyFee.setMonth(month);
        insertMonthlyFee.setPaymentStatus(status);

        MonthlyFee monthlyFeeSaved = monthlyFeeRepository.save(insertMonthlyFee);

        return MonthlyFeeDTO.fromEntity(monthlyFeeSaved);
    }

    public MonthlyFeeDTO checkMonthlyFee(String name, MonthEnum month){
        Users users = userRepository.findByName(name)
                .orElseThrow(() -> ResourceNotFoundException.userNotFound(name));

        MonthlyFee monthlyFee = monthlyFeeRepository.findByUsersAndMonth(users, month)
                .orElseThrow(() -> ResourceNotFoundException.monthlyFeeNotFound(name, month.toString()));

        return MonthlyFeeDTO.fromEntity(monthlyFee);
    }

    public List<UserDTO> findAllUsersWhereStatusIsOpen(int month){
           if(month < 1 || month > 12) {
               throw new IllegalArgumentException("Mês inexistente");
           }

            MonthEnum monthEnum = MonthEnum.values()[month - 1]; // -1 pois o indice do values inicia em 0

            return monthlyFeeRepository.findByMonth(monthEnum)
                    .stream()
                    .filter(mF -> mF.getPaymentStatus() == PaymentStatus.EM_ABERTO) // funciona igual o if
                    .map(monthlyFee ->  UserDTO.fromEntity(monthlyFee.getUsers()))// funciona como um foreach, para cada pagamento em aberto, irá add na lista de dto o usuario respectivo
                    .collect(Collectors.toList()); //transforma em uma lista de UserDTO


    }
}
