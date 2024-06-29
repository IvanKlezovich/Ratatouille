package org.example.paymentservice.service;

import org.example.paymentservice.entities.Bank;
import org.example.paymentservice.repositories.BankRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The {@code BankService} class is a service responsible for handling operations related to bank accounts.
 * It interacts with the {@link BankRepository} to perform CRUD operations on bank entities.
 */
@Service
public class BankService {

    private final BankRepository bankRepository;

    /**
     * Constructs a new {@code BankService} with the specified {@link BankRepository}.
     *
     * @param bankRepository The repository used for accessing bank data.
     */
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    /**
     * Deducts the specified sum from the balance of the bank account associated with the given card number.
     *
     * @param numberCard The card number associated with the bank account.
     * @param sum The amount to be deducted from the balance.
     * @return {@code true} if the deduction was successful, {@code false} if the account does not exist or has insufficient balance.
     */
    public boolean minusBalance(Long numberCard, Double sum) {
        Bank bank = bankRepository.getBankByNumberCard(numberCard);
        if (bank == null || bank.getBalance() < sum) {
            return false;
        }
        bank.setBalance(bank.getBalance() - sum);
        bankRepository.save(bank);
        return true;
    }

    /**
     * Adds the specified sum to the balance of the bank account associated with the default card number (0L).
     *
     * @param sum The amount to be added to the balance.
     * @return {@code true} if the addition was successful, {@code false} if the account does not exist.
     */
    public boolean addBalance(Double sum) {
        Optional<Bank> bank = Optional.ofNullable(bankRepository.getBankByNumberCard(0L));
        if (bank.isEmpty()) {
            return false;
        } else {
            bank.get().setBalance(bank.get().getBalance() + sum);
            bankRepository.save(bank.get());
            return true;
        }
    }

    /**
     * Adds a new bank account with the specified card number and initializes its balance to zero.
     *
     * @param numberCard The card number for the new bank account.
     */
    public void addBankAccount(Long numberCard) {
        Bank bank = new Bank();
        bank.setNumberCard(numberCard);
        bank.setBalance(0D);
        bankRepository.save(bank);
    }

    /**
     * Changes the balance of the bank account associated with the given card number to the specified balance.
     *
     * @param numberCard The card number associated with the bank account.
     * @param balance The new balance for the bank account.
     */
    public void changeBalance(Long numberCard, Double balance) {
        Bank bank = bankRepository.getBankByNumberCard(numberCard);
        bank.setBalance(balance);
        bankRepository.save(bank);
    }
}
