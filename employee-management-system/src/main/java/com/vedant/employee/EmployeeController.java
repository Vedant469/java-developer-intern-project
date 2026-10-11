package com.vedant.employee;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeController {
    private final EmployeeRepository employees;

    public EmployeeController(EmployeeRepository employees) { this.employees = employees; }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("employees", employees.findAll());
        return "employees";
    }

    @GetMapping("/employees/new")
    public String newEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("pageTitle", "Add employee");
        return "employee-form";
    }

    @GetMapping("/employees/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Employee employee = employees.findById(id).orElseThrow();
        model.addAttribute("employee", employee);
        model.addAttribute("pageTitle", "Edit employee");
        return "employee-form";
    }

    @PostMapping("/employees/save")
    public String save(@Valid @ModelAttribute Employee employee, BindingResult result,
                       Model model, RedirectAttributes redirect) {
        employees.findByEmail(employee.getEmail()).ifPresent(existing -> {
            if (employee.getId() == null || !existing.getId().equals(employee.getId())) {
                result.rejectValue("email", "duplicate", "This email is already registered");
            }
        });
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", employee.getId() == null ? "Add employee" : "Edit employee");
            return "employee-form";
        }
        employees.save(employee);
        redirect.addFlashAttribute("message", "Employee saved successfully.");
        return "redirect:/";
    }

    @PostMapping("/employees/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        employees.deleteById(id);
        redirect.addFlashAttribute("message", "Employee deleted.");
        return "redirect:/";
    }
}
