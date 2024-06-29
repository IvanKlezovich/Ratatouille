package org.example.paymentservice.service;

import org.example.paymentservice.entities.Bank;
import org.example.paymentservice.repositories.BankRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The {@code BankServiceTest} class is a test class for the {@link BankService} class.
 * It uses Spring Boot's testing utilities to test the bank service functionality.
 */
@SpringBootTest
class BankServiceTest {

    /**
     * The {@code BankService} instance used to perform bank service operations.
     */
    @Autowired
    private BankService bankService;

    /**
     * The {@code BankRepository} instance, mocked for the purpose of this test.
     */
    @MockBean
    private BankRepository bankRepository;

    /**
     * Tests the successful deduction of balance from a bank account.
     * Asserts that the balance is correctly deducted and the repository save method is called.
     */
    @Test
    void testMinusBalanceSuccess() {
        Bank bank = new Bank();
        bank.setNumberCard(123456789L);
        bank.setBalance(1000.0);

        when(bankRepository.getBankByNumberCard(123456789L)).thenReturn(bank);

        boolean result = bankService.minusBalance(123456789L, 500.0);
        assertTrue(result);
        assertEquals(500.0, bank.getBalance());
        verify(bankRepository, times(1)).save(bank);
    }

    /**
     * Tests the scenario where there are insufficient funds in the bank account.
     * Asserts that the balance remains unchanged and the repository save method is not called.
     */
    @Test
    void testMinusBalanceInsufficientFunds() {
        Bank bank = new Bank();
        bank.setNumberCard(123456789L);
        bank.setBalance(1000.0);

        when(bankRepository.getBankByNumberCard(123456789L)).thenReturn(bank);

        boolean result = bankService.minusBalance(123456789L, 1500.0);
        assertFalse(result);
        assertEquals(1000.0, bank.getBalance());
        verify(bankRepository, never()).save(any(Bank.class));
    }

    /**
     * Tests the successful addition of balance to a bank account.
     * Asserts that the balance is correctly added and the repository save method is called.
     */
    @Test
    void testAddBalanceSuccess() {
        Bank bank = new Bank();
        bank.setNumberCard(0L);
        bank.setBalance(1000.0);

        when(bankRepository.getBankByNumberCard(0L)).thenReturn(bank);

        boolean result = bankService.addBalance(500.0);
        assertTrue(result);
        assertEquals(1500.0, bank.getBalance());
        verify(bankRepository, times(1)).save(bank);
    }

    /**
     * Tests the scenario where the bank account is not found.
     * Asserts that the balance remains unchanged and the repository save method is not called.
     */
    @Test
    void testAddBalanceBankNotFound() {
        when(bankRepository.getBankByNumberCard(0L)).thenReturn(null);

        boolean result = bankService.addBalance(500.0);
        assertFalse(result);
        verify(bankRepository, never()).save(any(Bank.class));
    }

    /**
     * Tests the addition of a new bank account.
     * Asserts that the repository save method is called.
     */
    @Test
    void testAddBankAccount() {
        bankService.addBankAccount(987654321L);
        verify(bankRepository, times(1)).save(any(Bank.class));
    }

    /**
     * Tests the change of balance for a bank account.
     * Asserts that the balance is correctly changed and the repository save method is called.
     */
    @Test
    void testChangeBalance() {
        Bank bank = new Bank();
        bank.setNumberCard(123456789L);
        bank.setBalance(1000.0);

        when(bankRepository.getBankByNumberCard(123456789L)).thenReturn(bank);

        bankService.changeBalance(123456789L, 2000.0);
        assertEquals(2000.0, bank.getBalance());
        verify(bankRepository, times(1)).save(bank);
    }
}
