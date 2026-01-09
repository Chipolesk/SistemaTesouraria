package org.exemploTesouraria.controller;

import jakarta.validation.Valid;
import org.exemploTesouraria.DTO.MonthlyFeeDTO;
import org.exemploTesouraria.DTO.MonthlyFeeRequestDTO;
import org.exemploTesouraria.DTO.UserDTO;
import org.exemploTesouraria.DTO.UsersDebtorsMonthlyFeeDTO;
import org.exemploTesouraria.service.MonthlyFeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<MonthlyFeeDTO> createMonthlyFee(@Valid @RequestBody MonthlyFeeRequestDTO monthlyFeeRequestDTO){
        return ResponseEntity.ok(
                monthlyFeeService.createMonthly(
                        monthlyFeeRequestDTO.username(),
                        monthlyFeeRequestDTO.month()
                )
        );
    }

}
