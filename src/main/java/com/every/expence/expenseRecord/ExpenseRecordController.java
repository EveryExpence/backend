package com.every.expence.expenseRecord;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/expenseRecord")
public class ExpenseRecordController {
    private final ExpenseRecordService expenseRecordService;

    public ExpenseRecordController(ExpenseRecordService expenseRecordService) {
        this.expenseRecordService = expenseRecordService;
    }

    @GetMapping
    public String getMethodName(@RequestParam
    String param) {
        return new String();
    }

}
