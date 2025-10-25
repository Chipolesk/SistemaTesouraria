package org.exemploTesouraria.Controller;

import jakarta.validation.Valid;
import org.exemploTesouraria.DTO.CanteenDTO;

import org.exemploTesouraria.DTO.CanteenRequestDTO;
import org.exemploTesouraria.service.CanteenService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/canteen")
public class CanteenController {

    private final CanteenService canteenService;

    public CanteenController(CanteenService canteenService) {
        this.canteenService = canteenService;
    }

    @PostMapping
    public ResponseEntity<CanteenDTO> createCanteen(@RequestBody @Valid CanteenRequestDTO requestDTO){
        CanteenDTO  createdCanteen = canteenService.createCanteen(
                requestDTO.food(),
                requestDTO.description(),
                requestDTO.valueSold(),
                requestDTO.dateCant(),
                requestDTO.expenses(),
                requestDTO.annotation(),
                requestDTO.debtors());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCanteen);
    }

    @GetMapping
    public ResponseEntity<List<CanteenDTO>> findAll(){
        List<CanteenDTO> canteens = canteenService.findAllCanteens();
       return ResponseEntity.ok(canteens);
    }

    @GetMapping("/{month}")
    public ResponseEntity<List<CanteenDTO>> findByMonth(@PathVariable int month){
        List<CanteenDTO> canteens = canteenService.findByMonth(month);
        return ResponseEntity.ok(canteens);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<CanteenDTO> findByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        CanteenDTO canteenDTOS = canteenService.findByDateCant(date);
        return ResponseEntity.ok(canteenDTOS);
    }
}
