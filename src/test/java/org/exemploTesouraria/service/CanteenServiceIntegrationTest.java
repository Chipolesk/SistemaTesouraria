package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.CanteenDTO;
import org.exemploTesouraria.DTO.DebtorDTO;
import org.exemploTesouraria.financial.FinancialEventPublisher;
import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.repository.CanteenRepository;
import org.exemploTesouraria.repository.DebtorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CanteenServiceIntegrationTest {

    @Autowired
    private CanteenService canteenService;

    @Autowired
    private CanteenRepository canteenRepository;

    @Autowired
    private DebtorRepository debtorRepository;

    @MockBean
    private FinancialEventPublisher financialEventPublisher;

    @BeforeEach
    void setUp() {
        debtorRepository.deleteAll();
        canteenRepository.deleteAll();
    }

    @Test
    void shouldCreateCanteenWithDebtorsAndCalculateProfit() {
        CanteenDTO created = canteenService.createCanteen(
                "Salgados",
                "Cantina da semana",
                220.0,
                LocalDate.of(2026, 4, 1),
                70.0,
                "anotacao",
                List.of(new DebtorDTO("Maria", 10.0), new DebtorDTO("Paulo", 5.0))
        );

        assertNotNull(created.id());
        assertEquals(150.0, created.profit());
        assertEquals(2, created.debtors().size());
        verify(financialEventPublisher).onCanteenCreated(anyInt(), any(LocalDate.class), eq(220.0), eq(70.0), eq(150.0));
    }

    @Test
    @Transactional
    void shouldCreateCanteenEvenIfPublisherFails() {
        doThrow(new RuntimeException("falha na publicacao")).when(financialEventPublisher)
                .onCanteenCreated(anyInt(), any(LocalDate.class), anyDouble(), anyDouble(), anyDouble());

        CanteenDTO created = canteenService.createCanteen(
                "Bolos",
                "Cantina com erro no publisher",
                100.0,
                LocalDate.of(2026, 4, 2),
                30.0,
                "",
                List.of(new DebtorDTO("Carlos", 15.0))
        );

        assertNotNull(created.id());
        assertTrue(canteenRepository.findByDateCant(LocalDate.of(2026, 4, 2)).isPresent());

        Canteen persisted = canteenRepository.findByDateCant(LocalDate.of(2026, 4, 2)).orElseThrow();
        assertEquals(1, persisted.getDebtors().size());
        assertEquals(1, debtorRepository.count());
    }
}
