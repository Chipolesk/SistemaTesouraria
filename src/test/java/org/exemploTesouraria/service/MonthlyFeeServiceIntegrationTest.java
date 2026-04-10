package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.MonthlyFeeDTO;
import org.exemploTesouraria.exception.DataConflictException;
import org.exemploTesouraria.financial.FinancialEventPublisher;
import org.exemploTesouraria.model.MonthlyFee;
import org.exemploTesouraria.model.Users;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.exemploTesouraria.model.enums.PaymentStatus;
import org.exemploTesouraria.repository.MonthlyFeeRepository;
import org.exemploTesouraria.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class MonthlyFeeServiceIntegrationTest {

    @Autowired
    private MonthlyFeeService monthlyFeeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MonthlyFeeRepository monthlyFeeRepository;

    @MockBean
    private FinancialEventPublisher financialEventPublisher;

    @BeforeEach
    void setUp() {
        monthlyFeeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateValidMonthlyFee() {
        userRepository.save(Users.builder().name("Joao").build());

        MonthlyFeeDTO result = monthlyFeeService.createMonthly("Joao", MonthEnum.JANEIRO);

        assertNotNull(result);
        assertEquals("Joao", result.username());
        assertEquals(MonthEnum.JANEIRO, result.month());
        assertEquals(PaymentStatus.EM_ABERTO, result.paymentStatus());
    }

    @Test
    void shouldReturnConflictWhenCreatingDuplicateMonthlyFee() {
        userRepository.save(Users.builder().name("Joao").build());
        monthlyFeeService.createMonthly("Joao", MonthEnum.JANEIRO);

        assertThrows(DataConflictException.class, () -> monthlyFeeService.createMonthly("Joao", MonthEnum.JANEIRO));
    }

    @Test
    void shouldPersistOnlyOneRecordOnConcurrentCreation() throws InterruptedException {
        Users user = userRepository.save(Users.builder().name("Joao").build());
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch ready = new CountDownLatch(2);
        CountDownLatch start = new CountDownLatch(1);
        List<Throwable> failures = new CopyOnWriteArrayList<>();
        List<MonthlyFeeDTO> successes = new CopyOnWriteArrayList<>();

        Runnable task = () -> {
            try {
                ready.countDown();
                start.await();
                MonthlyFeeDTO dto = monthlyFeeService.createMonthly("Joao", MonthEnum.FEVEREIRO);
                successes.add(dto);
            } catch (Throwable throwable) {
                failures.add(throwable);
            }
        };

        executor.submit(task);
        executor.submit(task);

        ready.await(5, TimeUnit.SECONDS);
        start.countDown();
        executor.shutdown();
        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));

        List<MonthlyFee> persisted = new ArrayList<>();
        monthlyFeeRepository.findByUsersAndMonth(user, MonthEnum.FEVEREIRO).ifPresent(persisted::add);

        assertEquals(1, persisted.size());
        assertEquals(1, successes.size());
        assertEquals(1, failures.size());
        assertTrue(failures.getFirst() instanceof DataConflictException);
    }

    @Test
    void shouldPayValidMonthlyFee() {
        userRepository.save(Users.builder().name("Joao").build());
        monthlyFeeService.createMonthly("Joao", MonthEnum.MARCO);

        MonthlyFeeDTO paid = monthlyFeeService.payMonthly("Joao", MonthEnum.MARCO);

        assertEquals(PaymentStatus.PAGO, paid.paymentStatus());
    }


    @Test
    void shouldPayMonthlyFeeEvenIfPublisherFails() {
        userRepository.save(Users.builder().name("Joao").build());
        monthlyFeeService.createMonthly("Joao", MonthEnum.MAIO);

        doThrow(new RuntimeException("falha publisher")).when(financialEventPublisher)
                .onMonthlyFeePaid(anyInt(), anyString(), any(), any());

        MonthlyFeeDTO paid = assertDoesNotThrow(() -> monthlyFeeService.payMonthly("Joao", MonthEnum.MAIO));

        assertEquals(PaymentStatus.PAGO, paid.paymentStatus());
    }
    @Test
    void shouldReturnConflictWhenPayingAlreadyPaidMonthlyFee() {
        userRepository.save(Users.builder().name("Joao").build());
        monthlyFeeService.createMonthly("Joao", MonthEnum.ABRIL);
        monthlyFeeService.payMonthly("Joao", MonthEnum.ABRIL);

        assertThrows(DataConflictException.class, () -> monthlyFeeService.payMonthly("Joao", MonthEnum.ABRIL));
    }
}
