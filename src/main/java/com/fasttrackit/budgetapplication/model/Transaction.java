package com.fasttrackit.budgetapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Enumeration;

@Getter
@Setter
@AllArgsConstructor
public class Transaction {
    private int id;
    String product;
    TransactionType transactionType;
    double amount;
}

