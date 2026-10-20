
package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    // ==========================================
    // Lab 06: Unit Tests for printSalaries()
    // ==========================================

    // Test 1: Employee list is null
    @Test
    void printSalariesTestNull()
    {
        app.printSalaries(null);
    }

    // Test 2: Employee list is empty
    @Test
    void printSalariesTestEmpty()
    {
        ArrayList<Employee> employees = new ArrayList<>();

        app.printSalaries(employees);
    }

    // Test 3: Employee list contains null
    @Test
    void printSalariesTestContainsNull()
    {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(null);

        app.printSalaries(employees);
    }

    // Test 4: Employee list contains a normal employee
    @Test
    void printSalariesTestNormal()
    {
        ArrayList<Employee> employees = new ArrayList<>();

        Employee emp = new Employee();
        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;

        employees.add(emp);

        app.printSalaries(employees);
    }

    // ==========================================
    // Lab 06: Unit Tests for displayEmployee()
    // ==========================================

    // Test 5: Employee is null
    @Test
    void displayEmployeeTestNull()
    {
        app.displayEmployee(null);
    }

    // Test 6: Employee has no department or manager
    @Test
    void displayEmployeeTestMissingDetails()
    {
        Employee emp = new Employee();

        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;

        app.displayEmployee(emp);
    }

    // Test 7: Employee has complete details
    @Test
    void displayEmployeeTestNormal()
    {
        Employee emp = new Employee();

        emp.emp_no = 2;
        emp.first_name = "Anna";
        emp.last_name = "Smith";
        emp.title = "Manager";
        emp.salary = 65000;

        // Create department
        Department dept = new Department();
        dept.dept_no = "d001";
        dept.dept_name = "Marketing";

        // Create manager
        Employee manager = new Employee();
        manager.emp_no = 3;
        manager.first_name = "John";
        manager.last_name = "Brown";

        // Assign department and manager
        emp.dept = dept;
        emp.manager = manager;

        // Display employee information
        app.displayEmployee(emp);
    }
}
