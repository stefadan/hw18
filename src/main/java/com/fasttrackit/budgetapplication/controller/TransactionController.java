package com.fasttrackit.budgetapplication.controller;

import com.fasttrackit.budgetapplication.model.Transaction;
import com.fasttrackit.budgetapplication.model.TransactionType;
import com.fasttrackit.budgetapplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("transactions") //http://localhost:port/transactions
public class TransactionController {
    public final TransactionService transactionService;

    //GET /transactions - get all transactions. Make it filterable by product , type, minAmount, maxAmount
    @GetMapping //GET //http://localhost:port/transactions
    public List<Transaction> getAll(@RequestParam(required = false) String product, String getMinAmount, String getMaxAmount)
    {
        if (product != null) {//http://localhost:port/transactions?product={product_name}
            return transactionService.getByProduct(product);
        }
        else if (getMinAmount != null)//http://localhost:8080/transactions?getMinAmount
            return Collections.singletonList(transactionService.getMinAmount());
        else if (getMaxAmount != null)//http://localhost:8080/transactions?getMaxAmount
            return Collections.singletonList(transactionService.getMaxAmount());
        else {
            return transactionService.getAllTransactions();
        }
    }
    //GET /transactions/{id} - get transaction with id
    @GetMapping("{id}") //GET //http://localhost:port/transactions/3
    public Transaction geById(@PathVariable int id){
        return transactionService.getById(id);
    }

    //POST /transactions - adds a new transaction
    @PostMapping //POST //http://localhost:port/transactions
    public Transaction add(@RequestBody Transaction transaction) {
        return transactionService.addTransaction(transaction);
    }

    //PUT  /transactions/{id} - replaces the transaction with id
    @PutMapping("{id}") //PUT //http://localhost:port/transactions/3
    public Transaction update(@PathVariable int id, @RequestBody Transaction transaction) {
        return transactionService.update(id, transaction);
    }

    //DELETE /transactions/{id} - deletes the transaction with id
    @DeleteMapping("{id}") //DELETE //http://localhost:port/transactions/3
    public Transaction deleteById(@PathVariable int id) {
        return transactionService.deleteById(id);
    }

    //GET /transactions/reports/type -> returns a map from type to list of transactions of that type
    /*@GetMapping //GET //http://localhost:port/transactions/reports/type?type={type_transaction}
    public Map<Integer, Transaction> getByTypeTransactionReport(@RequestParam(required = false) String typeTransaction)
    {
            return transactionService.getByTypeTransactionReport(TransactionType.valueOf(typeTransaction));
    }*/

    //GET /transactions/reports/product -> returns a map from product to list of transactions for that product



}
