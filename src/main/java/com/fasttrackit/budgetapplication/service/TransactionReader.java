package com.fasttrackit.budgetapplication.service;

import com.fasttrackit.budgetapplication.model.Transaction;
import com.fasttrackit.budgetapplication.model.TransactionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransactionReader {

    static int productId;
    @Value("${fileProductPath}")
    String filePath;

    public List<Transaction> getTransactions(){
        try {
            return Files.lines(Path.of(filePath))
                    .map(this::getLineTransaction)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Transaction getLineTransaction(String line){
        String[] tokenTransaction = line.split("\\|");
        return new Transaction(
                productId++,
                tokenTransaction[0], TransactionType.valueOf(tokenTransaction[1]),
                Double.parseDouble(tokenTransaction[2])
        );
    }


}
