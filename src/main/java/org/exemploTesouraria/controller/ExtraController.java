package org.exemploTesouraria.controller;

import jakarta.validation.Valid;
import org.exemploTesouraria.DTO.ExtraDTO;
import org.exemploTesouraria.DTO.ExtraRequestDTO;
import org.exemploTesouraria.service.ExtraService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/extras")
public class ExtraController {

    private final ExtraService extraService;

    public ExtraController(ExtraService extraService) {
        this.extraService = extraService;
    }

    @PostMapping
    public ResponseEntity<ExtraDTO> createExtra(@RequestBody @Valid ExtraRequestDTO requestDTO) {
        ExtraDTO createdExtra = extraService.createExtra(
                requestDTO.valueSold(),
                requestDTO.expenses(),
                requestDTO.description(),
                requestDTO.dateExtra()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdExtra);
    }

    @GetMapping
    public ResponseEntity<List<ExtraDTO>> findAll() {
        return ResponseEntity.ok(extraService.findAllExtras());
    }

    @GetMapping("/{month}")
    public ResponseEntity<List<ExtraDTO>> findByMonth(@PathVariable int month) {
        return ResponseEntity.ok(extraService.findByMonth(month));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<ExtraDTO> findByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(extraService.findByDateExtra(date));
    }
}
