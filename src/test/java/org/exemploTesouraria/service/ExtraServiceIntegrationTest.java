package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.ExtraDTO;
import org.exemploTesouraria.financial.FinancialEventPublisher;
import org.exemploTesouraria.repository.ExtraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ExtraServiceIntegrationTest {

    @Autowired
    private ExtraService extraService;

    @Autowired
    private ExtraRepository extraRepository;

    @MockBean
    private FinancialEventPublisher financialEventPublisher;

    @BeforeEach
    void setUp() {
        extraRepository.deleteAll();
    }

    @Test
    void shouldCreateExtraWithCalculatedProfit() {
        ExtraDTO created = extraService.createExtra(90.0, 20.0, "Venda adicional", LocalDate.of(2026, 4, 4));

        assertNotNull(created.id());
        assertEquals(70.0, created.profit());
        verify(financialEventPublisher).onExtraCreated(anyInt(), any(LocalDate.class), eq(90.0), eq(20.0), eq(70.0));
    }

    @Test
    void shouldCreateExtraEvenIfPublisherFails() {
        doThrow(new RuntimeException("falha publisher")).when(financialEventPublisher)
                .onExtraCreated(anyInt(), any(LocalDate.class), anyDouble(), anyDouble(), anyDouble());

        ExtraDTO created = assertDoesNotThrow(() -> extraService.createExtra(40.0, 10.0, "Falha publisher", LocalDate.of(2026, 4, 5)));

        assertNotNull(created.id());
        assertTrue(extraRepository.findByDateExtra(LocalDate.of(2026, 4, 5)).isPresent());
    }

}
