package org.example.finance_backend.controllers;

import jakarta.validation.Valid;
import org.example.finance_backend.entity.FinanceRecord;
import org.example.finance_backend.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FinanceRecord> addRecord(@Valid @RequestBody FinanceRecord record) {
        FinanceRecord saved=recordService.createRecord(record);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public ResponseEntity<List<FinanceRecord>> getAllRecords(
            @RequestParam(required = false) FinanceRecord.TransactionType type,
            @RequestParam(required = false) String category) {
        List<FinanceRecord> records=recordService.getAllRecords(type, category);

        if(records.isEmpty()){
            return ResponseEntity.noContent().build();   // 204
        }
        return ResponseEntity.ok(records);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<FinanceRecord> updateRecord(
            @PathVariable Long id,
            @Valid @RequestBody FinanceRecord updatedrecord){
        FinanceRecord updated=recordService.updateRecord(id, updatedrecord);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<String> deleteRecord(@PathVariable Long id){
        recordService.deleteRecord(id);
        return  ResponseEntity.ok("Record Deleted Successfully");
    }
}
