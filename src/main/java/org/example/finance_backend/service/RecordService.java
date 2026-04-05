package org.example.finance_backend.service;

import org.example.finance_backend.entity.FinanceRecord;
import org.example.finance_backend.repository.FinanceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    private final FinanceRecordRepository recordRepository;

    public RecordService(FinanceRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public FinanceRecord createRecord(FinanceRecord record){
        // Simple validation to check  whether the type is correct
        if(record.getType()!= FinanceRecord.TransactionType.INCOME && record.getType()!= FinanceRecord.TransactionType.EXPENSE){
            throw  new RuntimeException("type must be Income or Expense");
        }
        return recordRepository.save(record);
    }

    public List<FinanceRecord> getAllRecords(FinanceRecord.TransactionType type, String category){
        if(type!=null && category!=null){
            return  recordRepository.findByCategoryAndType(category,type);
        }

        if(type!=null){
            return recordRepository.findByType(type);
        }
        if(category!=null && !category.isEmpty()){
            return recordRepository.findByCategory(category);
        }
        return recordRepository.findAll();
    }

    public FinanceRecord updateRecord(Long id,FinanceRecord updatedData){

        FinanceRecord existingRecord=recordRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("record not found"));

         if(updatedData.getAmount()!=null){
             existingRecord.setAmount(updatedData.getAmount());
         }
         if(updatedData.getType()!=null){
             existingRecord.setType(updatedData.getType());
         }
         if(updatedData.getCategory()!=null && updatedData.getCategory().isEmpty()){
             existingRecord.setCategory(updatedData.getCategory());
         }
         if(updatedData.getDate()!=null){
             existingRecord.setDate(updatedData.getDate());
         }
         if(updatedData.getNotes()!=null){
             existingRecord.setNotes(updatedData.getNotes());
         }
         return recordRepository.save(existingRecord);
    }
    public void deleteRecord(Long id){
        FinanceRecord record=recordRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("record not found"));
        recordRepository.delete(record);
    }
}
