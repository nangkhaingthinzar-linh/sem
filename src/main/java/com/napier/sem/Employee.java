
package com.napier.sem;

/**
 * Represents an employee in the organisation.
 */
public class Employee
{
    /**
     * Employee number.
     */
    public int emp_no;

    /**
     * Employee's first name.
     */
    public String first_name;

    /**
     * Employee's last name.
     */
    public String last_name;

    /**
     * Employee's job title.
     */
    public String title;

    /**
     * Employee's current salary.
     */
    public int salary;

    /**
     * Employee's current department.
     */
    public Department dept;

    /**
     * Employee's manager.
     */
    public Employee manager;
}