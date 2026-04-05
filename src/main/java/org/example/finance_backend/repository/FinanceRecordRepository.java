package org.example.finance_backend.repository;

import org.example.finance_backend.entity.FinanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinanceRecordRepository  extends JpaRepository<FinanceRecord,Long> {
    List<FinanceRecord> findByType(FinanceRecord.TransactionType type);

    List<FinanceRecord> findByCategory(String category);

    List<FinanceRecord> findByCategoryAndType(String category, FinanceRecord.TransactionType type);

}
