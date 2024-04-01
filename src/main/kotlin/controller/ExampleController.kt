package ar.edu.unq.desapp.grupoF.backenddesappapi.controller

import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Employee Management", description = "Operations pertaining to employee in Employee Management")
@RestController
@RequestMapping("/employee")
class EmployeeController {
    private val employees = mutableMapOf<String, String>()

    @Operation(summary = "View a specific employee")
    @GetMapping("/{id}")
    fun getEmployee(@PathVariable id: String) = employees[id]

    @Operation(summary = "View a list of all employees")
    @GetMapping("/list")
    fun getAllEmployees() = employees.values.toList()

    @Operation(summary = "Add an employee")
    @PostMapping
    fun addEmployee(@RequestBody id: String, @RequestBody name: String): String {
        employees[id] = name
        return name
    }
}