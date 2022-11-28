package com.fasttrackit.budgetapplication.service;

import com.fasttrackit.budgetapplication.exceptions.ResourcesNotFoundException;
import com.fasttrackit.budgetapplication.model.Transaction;
import com.fasttrackit.budgetapplication.model.TransactionType;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    TransactionReader transactionReader;
    List<Transaction> transactions;

    public TransactionService(TransactionReader transactionReader) {
        this.transactionReader = transactionReader;
        transactions = transactionReader.getTransactions();
        System.out.println("Finishing reading transactions");
    }

    //GET /transactions - get all transactions. Make it filterable by product , type, minAmount, maxAmount
    public List<Transaction> getAllTransactions() {
        return transactions.stream().toList();
    }

    public List<Transaction> getByProduct(String product) {
        System.out.println("product "+product);
        return transactions.stream().filter(c -> c.getProduct().toLowerCase().equals(product.toLowerCase())).toList();
    }

    public List<Transaction> getByTypeTransaction(TransactionType transactionType) {
        return transactions.stream().filter(c -> c.getTransactionType().equals(transactionType)).toList();
    }

    public Transaction getMinAmount()
    {
        Comparator<Transaction> comparator = Comparator.comparing( Transaction::getAmount );
        // Get Min or Max Object
        Transaction minObject = transactions.stream().min(comparator).get();
        return minObject;
    }

    public Transaction getMaxAmount()
    {
        Comparator<Transaction> comparator = Comparator.comparing( Transaction::getAmount );
        // Get Min or Max Object
        Transaction minObject = transactions.stream().max(comparator).get();
        return minObject;
    }


    //GET /transactions/{id} - get transaction with id
    public Transaction getById(int id) {
        return transactions.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourcesNotFoundException("Transaction not found", id));
    }

    //POST /transactions - adds a new transaction
    public Transaction addTransaction(Transaction transaction) {
        transaction.setId(TransactionReader.productId++);
        transactions.add(transaction);
        return transaction;
    }

    //PUT  /transactions/{id} - replaces the transaction with id
    public Transaction update(int id, Transaction transaction){
        Transaction transactionToUpdate = getById(id);
        transactionToUpdate.setProduct(transaction.getProduct());
        transactionToUpdate.setTransactionType(transaction.getTransactionType());
        transactionToUpdate.setAmount(transaction.getAmount());
        return transactionToUpdate;
    }

    //DELETE /transactions/{id} - deletes the transaction with id
    public Transaction deleteById(int id) {
        Transaction transactionToDelete = getById(id);
        transactions.remove(transactionToDelete);
        return transactionToDelete;
    }

    //GET /transactions/reports/type -> returns a map from type to list of transactions of that type
    public List<Transaction> getReportByTransactionType(TransactionType transactionType) {
        return transactions.stream().filter(c -> c.getTransactionType().equals(transactionType)).toList();
    }

    public Map<Integer, Transaction> getByTypeTransactionReport(TransactionType trasactionType ){
        Map<Integer, Transaction> collect = new HashMap<>();
        collect = transactions.stream()
                .filter(map -> map.getTransactionType().equals(trasactionType))
                .collect(Collectors
                        .toMap(p -> p.getId(),
                                p -> new Transaction(p.getId(), p.getProduct(), p.getTransactionType(), p.getAmount())));
        return collect;
    }

    //GET /transactions/reports/product -> returns a map from product to list of transactions for that product


}
