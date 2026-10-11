package com.vedant.expense;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin
public class ExpenseController {
    private final ExpenseRepository expenses;
    public ExpenseController(ExpenseRepository expenses) { this.expenses = expenses; }

    @GetMapping
    public List<Expense> list(@RequestParam(required = false) String category) {
        if (category != null && !category.isBlank())
            return expenses.findByCategoryIgnoreCaseOrderByExpenseDateDesc(category);
        return expenses.findAllByOrderByExpenseDateDesc();
    }

    @GetMapping("/{id}")
    public Expense get(@PathVariable Long id) {
        return expenses.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expense create(@Valid @RequestBody Expense expense) {
        expense.setId(null);
        return expenses.save(expense);
    }

    @PutMapping("/{id}")
    public Expense update(@PathVariable Long id, @Valid @RequestBody Expense input) {
        Expense existing = get(id);
        existing.setDescription(input.getDescription());
        existing.setCategory(input.getCategory());
        existing.setAmount(input.getAmount());
        existing.setExpenseDate(input.getExpenseDate());
        return expenses.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { expenses.delete(get(id)); }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        List<Expense> all = expenses.findAll();
        BigDecimal total = all.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, BigDecimal> byCategory = new java.util.TreeMap<>();
        all.forEach(e -> byCategory.merge(e.getCategory(), e.getAmount(), BigDecimal::add));
        return Map.of("expenseCount", all.size(), "totalSpent", total, "byCategory", byCategory);
    }

    @GetMapping("/between")
    public List<Expense> between(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        if (end.isBefore(start)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date must be on or after start date");
        return expenses.findByExpenseDateBetweenOrderByExpenseDateDesc(start, end);
    }
}
