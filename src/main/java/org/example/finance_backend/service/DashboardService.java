package org.example.finance_backend.service;

import org.example.finance_backend.entity.FinanceRecord;
import org.example.finance_backend.repository.FinanceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final FinanceRecordRepository recordRepository;

    public DashboardService(FinanceRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public Map<String,Object> getSummary(){

        List<FinanceRecord> records=recordRepository.findAll();

        double totIncome=0;
        double totExpense=0;

        // Map to store totals per category
        Map<String,Double> categoryTot=new HashMap<>();

        for(FinanceRecord record : records){
            double amount=record.getAmount();

            if(record.getType()== FinanceRecord.TransactionType.INCOME){
                totIncome+=amount;
            }else{
                totExpense+=amount;
            }

            categoryTot.put(record.getCategory(), categoryTot.getOrDefault(record.getCategory(),0.0)+amount);
        }
        Map<String,Object> res=new HashMap<>();
        res.put("totalIncome",totIncome);
        res.put("totalExpense",totExpense);
        res.put("netBalance",totIncome+totExpense);
        res.put("categoryWiseTotal",categoryTot);
        return res;
    }
}
