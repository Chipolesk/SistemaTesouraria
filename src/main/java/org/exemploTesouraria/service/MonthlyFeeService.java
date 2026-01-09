package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.MonthlyFeeDTO;
import org.exemploTesouraria.DTO.UserDTO;
import org.exemploTesouraria.DTO.UsersDebtorsMonthlyFeeDTO;
import org.exemploTesouraria.exception.DataConflictException;
import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.exemploTesouraria.model.MonthlyFee;
import org.exemploTesouraria.model.Users;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.exemploTesouraria.model.enums.PaymentStatus;
import org.exemploTesouraria.repository.MonthlyFeeRepository;
import org.exemploTesouraria.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MonthlyFeeService {

    private final MonthlyFeeRepository monthlyFeeRepository;
    private final UserRepository userRepository;

    public MonthlyFeeService(MonthlyFeeRepository monthlyFeeRepository, UserRepository userRepository) {
        this.monthlyFeeRepository = monthlyFeeRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public MonthlyFeeDTO createMonthly(String name, MonthEnum month){
        Users user = userRepository.findByName(name)
                .orElseThrow(() -> ResourceNotFoundException.userNotFound(name));

        Optional<MonthlyFee> isExists = monthlyFeeRepository.findByUsersAndMonth(user, month);

        if (isExists.isPresent()){
            throw DataConflictException.monthlyFeeAlreadyExist(isExists.get());
        }
        MonthlyFee insertMonthlyFee = new MonthlyFee();
        insertMonthlyFee.setUsers(user);
        insertMonthlyFee.setMonth(month);
        insertMonthlyFee.setPaymentStatus(PaymentStatus.EM_ABERTO);

        MonthlyFee monthlyFeeSaved = monthlyFeeRepository.save(insertMonthlyFee);

        return MonthlyFeeDTO.fromEntity(monthlyFeeSaved);
    }
    @Transactional
    public MonthlyFeeDTO payMonthly(String name, MonthEnum month){
        Users user = userRepository.findByName(name)
                .orElseThrow(() -> ResourceNotFoundException.userNotFound(name));

        MonthlyFee monthlyFee = monthlyFeeRepository.findByUsersAndMonth(user, month)
                .orElseThrow(() -> ResourceNotFoundException.monthlyFeeNotFound(name, month.name()));

        if(monthlyFee.getPaymentStatus() == PaymentStatus.PAGO){
            throw  DataConflictException.monthlyFeeAlreadyPaid(monthlyFee);
        }
        monthlyFee.setPaymentStatus(PaymentStatus.PAGO);
        monthlyFee.setPaymentDate(LocalDate.now());

        MonthlyFee updatedMonthlyFee = monthlyFeeRepository.save(monthlyFee);

        return MonthlyFeeDTO.fromEntity(updatedMonthlyFee);
    }

    public MonthlyFeeDTO checkMonthlyFee(String name, MonthEnum month){
        Users user = userRepository.findByName(name)
                .orElseThrow(() -> ResourceNotFoundException.userNotFound(name));

        MonthlyFee monthlyFee = monthlyFeeRepository.findByUsersAndMonth(user, month)
                .orElseThrow(() -> ResourceNotFoundException.monthlyFeeNotFound(name, month.name()));

        return MonthlyFeeDTO.fromEntity(monthlyFee);
    }

    public List<UserDTO> findAllUsersWhereStatusIsOpenByMonth(int month){
           if(month < 1 || month > 12) {
               throw new IllegalArgumentException("Mês inexistente");
           }

            MonthEnum monthEnum = MonthEnum.values()[month - 1];

            return monthlyFeeRepository.findByMonth(monthEnum)
                    .stream()
                    .filter(mF -> mF.getPaymentStatus() == PaymentStatus.EM_ABERTO)
                    .map(monthlyFee ->  UserDTO.fromEntity(monthlyFee.getUsers()))
                    .collect(Collectors.toList());


    }

    public List<UsersDebtorsMonthlyFeeDTO> findAllUsersDebtorsByAllMonths(){

       Map<Users, List<MonthEnum>> mapIntermediario = monthlyFeeRepository.findAll()
                .stream()
                .filter(m -> m.getPaymentStatus() == PaymentStatus.EM_ABERTO)
                .collect(Collectors.groupingBy(MonthlyFee::getUsers, Collectors.mapping(MonthlyFee::getMonth, Collectors.toList())));

       return mapIntermediario.entrySet()
                .stream()
               .map(entry -> new UsersDebtorsMonthlyFeeDTO(entry.getKey().getName(), entry.getValue()))
               .collect(Collectors.toList());


    }
}

