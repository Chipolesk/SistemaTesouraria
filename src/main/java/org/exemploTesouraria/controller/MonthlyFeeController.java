package org.exemploTesouraria.controller;

import jakarta.validation.Valid;
import org.exemploTesouraria.DTO.MonthlyFeeDTO;
import org.exemploTesouraria.DTO.CreateMonthlyFeeRequestDTO;
import org.exemploTesouraria.DTO.PayMonthlyFeeRequestDTO;
import org.exemploTesouraria.DTO.UsersDebtorsMonthlyFeeDTO;
import org.exemploTesouraria.service.MonthlyFeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/monthly-fees")
public class MonthlyFeeController {
    private final MonthlyFeeService monthlyFeeService;

    public MonthlyFeeController(MonthlyFeeService monthlyFeeService) {
        this.monthlyFeeService = monthlyFeeService;
    }

    @GetMapping("/debtors")
    public ResponseEntity<List<UsersDebtorsMonthlyFeeDTO>> findAllUsersDebtors(){
        return  ResponseEntity.ok(monthlyFeeService.findAllUsersDebtorsByAllMonths());
    }
    @PostMapping
    public ResponseEntity<MonthlyFeeDTO> createMonthlyFee(@Valid @RequestBody CreateMonthlyFeeRequestDTO createMonthlyFeeRequestDTO){
        return ResponseEntity.ok(
                monthlyFeeService.createMonthly(createMonthlyFeeRequestDTO.username(), createMonthlyFeeRequestDTO.month()
                )
        );
    }
    @PutMapping("/pay")
    public ResponseEntity<MonthlyFeeDTO> payMonthlyFee(@Valid @RequestBody PayMonthlyFeeRequestDTO payMonthlyFeeRequestDTO){
        return ResponseEntity.ok(
                monthlyFeeService.payMonthly(payMonthlyFeeRequestDTO.username(), payMonthlyFeeRequestDTO.month())
        );

    }

}
