package org.example.paymentservice.service;

import org.example.paymentservice.entities.Bank;
import org.example.paymentservice.repositories.BankRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository){
        this.bankRepository = bankRepository;
    }

    boolean minusBalance(Long numberCard, Double sum) {
        Bank bank = bankRepository.getBankByNumberCard(numberCard);
        System.out.println(bank);
        if (bank == null || bank.getBalance() < sum) {
            return false;
        }
        bank.setBalance(bank.getBalance() - sum);
        bankRepository.save(bank);
        return true;
    }

    boolean addBalance(Double sum) {
        Optional<Bank> bank = bankRepository.findById(0L);
        if (bank.isEmpty()) {
            return false;
        }else{
            bank.get().setBalance(bank.get().getBalance() + sum);
            bankRepository.save(bank.get());
            return true;
        }
    }

    void addBankAccount(Long numberCard) {
        Bank bank = new Bank();
        bank.setNumberCard(numberCard);
        bankRepository.save(bank);
    }

    public void changeBalance(Long numberCard, Double balance) {
        Bank bank = bankRepository.getBankByNumberCard(numberCard);
        bank.setBalance(balance);
        bankRepository.save(bank);
    }
}
