package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.DebtorDTO;
import org.exemploTesouraria.financial.FinancialEventPublisher;
import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.model.Debtors;
import org.exemploTesouraria.repository.CanteenRepository;
import org.exemploTesouraria.repository.DebtorRepository;
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
class DebtorServiceIntegrationTest {

    @Autowired
    private DebtorService debtorService;

    @Autowired
    private CanteenRepository canteenRepository;

    @Autowired
    private DebtorRepository debtorRepository;

    @MockBean
    private FinancialEventPublisher financialEventPublisher;

    private Canteen canteen;

    @BeforeEach
    void setUp() {
        debtorRepository.deleteAll();
        canteenRepository.deleteAll();

        canteen = canteenRepository.save(Canteen.builder()
                .food("Pizza")
                .dateCant(LocalDate.of(2026, 4, 3))
                .valueSold(200.0)
                .expenses(80.0)
                .build());
    }

    @Test
    void shouldPayDebtorPartially() {
        Debtors debtor = debtorRepository.save(Debtors.builder()
                .nameDebtors("Joana")
                .amount(50.0)
                .canteen(canteen)
                .build());

        debtorService.payDebtor(canteen.getId(), "Joana", 20.0);

        Debtors updated = debtorRepository.findById(debtor.getId()).orElseThrow();
        assertEquals(30.0, updated.getAmount());
        verify(financialEventPublisher).onDebtorPaid(eq(canteen.getId()), eq("Joana"), eq(20.0), eq(50.0), eq(30.0), eq(false));
    }

    @Test
    void shouldDeleteDebtorWhenPaymentCoversFullAmount() {
        debtorRepository.save(Debtors.builder()
                .nameDebtors("Marcos")
                .amount(40.0)
                .canteen(canteen)
                .build());

        debtorService.payDebtor(canteen.getId(), "Marcos", 40.0);

        assertTrue(debtorRepository.findByNameDebtorsAndCanteen("Marcos", canteen).isEmpty());
        verify(financialEventPublisher).onDebtorPaid(eq(canteen.getId()), eq("Marcos"), eq(40.0), eq(40.0), eq(0.0), eq(true));
    }


    @Test
    void shouldKeepDebtorUpdateEvenIfPublisherFails() {
        Debtors debtor = debtorRepository.save(Debtors.builder()
                .nameDebtors("Julia")
                .amount(60.0)
                .canteen(canteen)
                .build());

        doThrow(new RuntimeException("falha publisher")).when(financialEventPublisher)
                .onDebtorPaid(eq(canteen.getId()), eq("Julia"), eq(10.0), anyDouble(), anyDouble(), anyBoolean());

        assertDoesNotThrow(() -> debtorService.payDebtor(canteen.getId(), "Julia", 10.0));

        Debtors updated = debtorRepository.findById(debtor.getId()).orElseThrow();
        assertEquals(50.0, updated.getAmount());
    }
    @Test
    void shouldCreateDebtor() {
        DebtorDTO created = debtorService.createDebtors(canteen.getId(), "Nina", 18.0);
        assertEquals("Nina", created.name());
        assertEquals(18.0, created.amount());
    }
}
