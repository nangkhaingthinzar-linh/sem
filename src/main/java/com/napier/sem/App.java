
package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

/**
 * Main application for employee information and salary reports.
 */
public class App
{
    /**
     * Connection to the MySQL employees database.
     */
    private Connection con = null;

    /**
     * Main method.
     */
    public static void main(String[] args)
    {
        App a = new App();

        // Connect to the database
        a.connect();

        // Display employee details using employee number
        Employee emp = a.getEmployee(255530);
        a.displayEmployee(emp);

        // Lab 04: Salary report for a given role
        // a.displaySalariesByRole("Engineer");

        // Lab 04: Salary report for all employees
        // a.displaySalariesAllEmployees();

        // Lab 05: Salary report for a department
        // Temporarily disabled to make Exercise 3 testing faster.
        // a.displayDepartmentSalaryReport("Sales");

        // Lab 05 Exercise 3: Search employee by first and last name
        System.out.println("\nEmployee Search by Name");

        Employee searchedEmployee =
                a.getEmployeeByName("Ronghao", "Garigliano");

        a.displayEmployee(searchedEmployee);

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;

        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");

            try
            {
                // Wait for the database to start
                Thread.sleep(30000);

                con = DriverManager.getConnection(
                        "jdbc:mysql://db:3306/employees?useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "example"
                );

                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println(
                        "Failed to connect to database attempt " + (i + 1)
                );
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted");
                return;
            }
        }
    }

    /**
     * Retrieve an employee by their employee number.
     *
     * @param ID Employee number.
     * @return Employee object or null if not found.
     */
    public Employee getEmployee(int ID)
    {
        if (con == null)
        {
            System.out.println("No database connection");
            return null;
        }

        String strSelect =
                "SELECT e.emp_no, e.first_name, e.last_name, " +
                        "t.title, s.salary, d.dept_no, d.dept_name, " +
                        "m.emp_no AS manager_no, " +
                        "m.first_name AS manager_first_name, " +
                        "m.last_name AS manager_last_name " +
                        "FROM employees e " +
                        "JOIN titles t ON e.emp_no = t.emp_no " +
                        "AND t.to_date = '9999-01-01' " +
                        "JOIN salaries s ON e.emp_no = s.emp_no " +
                        "AND s.to_date = '9999-01-01' " +
                        "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "AND de.to_date = '9999-01-01' " +
                        "JOIN departments d ON de.dept_no = d.dept_no " +
                        "LEFT JOIN dept_manager dm ON de.dept_no = dm.dept_no " +
                        "AND dm.to_date = '9999-01-01' " +
                        "LEFT JOIN employees m ON dm.emp_no = m.emp_no " +
                        "WHERE e.emp_no = ?";

        try (PreparedStatement stmt = con.prepareStatement(strSelect))
        {
            stmt.setInt(1, ID);

            try (ResultSet rset = stmt.executeQuery())
            {
                if (rset.next())
                {
                    Employee emp = new Employee();

                    emp.emp_no = rset.getInt("emp_no");
                    emp.first_name = rset.getString("first_name");
                    emp.last_name = rset.getString("last_name");
                    emp.title = rset.getString("title");
                    emp.salary = rset.getInt("salary");

                    // Create department object
                    Department dept = new Department();
                    dept.dept_no = rset.getString("dept_no");
                    dept.dept_name = rset.getString("dept_name");

                    // Create manager object
                    int managerNo = rset.getInt("manager_no");

                    if (!rset.wasNull())
                    {
                        Employee manager = new Employee();

                        manager.emp_no = managerNo;
                        manager.first_name =
                                rset.getString("manager_first_name");
                        manager.last_name =
                                rset.getString("manager_last_name");

                        emp.manager = manager;
                        dept.manager = manager;
                    }

                    emp.dept = dept;

                    return emp;
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
        }

        return null;
    }

    /**
     * Display an employee's information.
     *
     * @param emp Employee to display.
     */
    public void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            String departmentName =
                    emp.dept != null
                            ? emp.dept.dept_name
                            : "Unknown department";

            String managerName =
                    emp.manager != null
                            ? emp.manager.first_name + " "
                            + emp.manager.last_name
                            : "Unknown manager";

            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + departmentName + "\n"
                            + "Manager: " + managerName + "\n"
            );
        }
        else
        {
            System.out.println("Employee not found.");
        }
    }

    /**
     * Display current salaries for employees with a given role.
     *
     * @param role Job title to search.
     */
    public void displaySalariesByRole(String role)
    {
        if (con == null)
        {
            System.out.println("No database connection");
            return;
        }

        String strSelect =
                "SELECT employees.emp_no, employees.first_name, " +
                        "employees.last_name, salaries.salary " +
                        "FROM employees, salaries, titles " +
                        "WHERE employees.emp_no = salaries.emp_no " +
                        "AND employees.emp_no = titles.emp_no " +
                        "AND salaries.to_date = '9999-01-01' " +
                        "AND titles.to_date = '9999-01-01' " +
                        "AND titles.title = ? " +
                        "ORDER BY employees.emp_no ASC";

        System.out.println("\nSalary Report for Role: " + role);
        System.out.println(
                "Employee Number | First Name | Last Name | Salary"
        );

        try (PreparedStatement stmt = con.prepareStatement(strSelect))
        {
            stmt.setString(1, role);

            try (ResultSet rset = stmt.executeQuery())
            {
                int count = 0;

                while (rset.next())
                {
                    System.out.printf(
                            "%-15d %-18s %-20s %d%n",
                            rset.getInt("emp_no"),
                            rset.getString("first_name"),
                            rset.getString("last_name"),
                            rset.getInt("salary")
                    );

                    count++;
                }

                if (count == 0)
                {
                    System.out.println(
                            "No employees found for this role."
                    );
                }

                System.out.println("Total employees: " + count);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salaries by role");
        }
    }

    /**
     * Display current salary information for all employees.
     */
    public void displaySalariesAllEmployees()
    {
        if (con == null)
        {
            System.out.println("No database connection");
            return;
        }

        String strSelect =
                "SELECT e.emp_no, e.first_name, e.last_name, s.salary " +
                        "FROM employees e " +
                        "JOIN salaries s ON e.emp_no = s.emp_no " +
                        "WHERE s.to_date = '9999-01-01' " +
                        "ORDER BY e.emp_no ASC";

        System.out.println("\nSalary Report for All Employees");
        System.out.println(
                "Employee Number | First Name | Last Name | Salary"
        );

        try (PreparedStatement stmt = con.prepareStatement(strSelect);
             ResultSet rset = stmt.executeQuery())
        {
            int count = 0;

            while (rset.next())
            {
                System.out.printf(
                        "%-15d %-18s %-20s %d%n",
                        rset.getInt("emp_no"),
                        rset.getString("first_name"),
                        rset.getString("last_name"),
                        rset.getInt("salary")
                );

                count++;
            }

            if (count == 0)
            {
                System.out.println("No current salary records found.");
            }

            System.out.println("Total employees: " + count);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salaries for all employees");
        }
    }

    /**
     * Retrieve a department by its name.
     *
     * @param dept_name Department name.
     * @return Department object or null if not found.
     */
    public Department getDepartment(String dept_name)
    {
        if (con == null)
        {
            System.out.println("No database connection");
            return null;
        }

        String strSelect =
                "SELECT d.dept_no, d.dept_name, " +
                        "m.emp_no AS manager_no, " +
                        "m.first_name AS manager_first_name, " +
                        "m.last_name AS manager_last_name " +
                        "FROM departments d " +
                        "LEFT JOIN dept_manager dm ON d.dept_no = dm.dept_no " +
                        "AND dm.to_date = '9999-01-01' " +
                        "LEFT JOIN employees m ON dm.emp_no = m.emp_no " +
                        "WHERE d.dept_name = ?";

        try (PreparedStatement stmt = con.prepareStatement(strSelect))
        {
            stmt.setString(1, dept_name);

            try (ResultSet rset = stmt.executeQuery())
            {
                if (rset.next())
                {
                    Department dept = new Department();

                    dept.dept_no = rset.getString("dept_no");
                    dept.dept_name = rset.getString("dept_name");

                    // Retrieve current department manager
                    int managerNo = rset.getInt("manager_no");

                    if (!rset.wasNull())
                    {
                        Employee manager = new Employee();

                        manager.emp_no = managerNo;
                        manager.first_name =
                                rset.getString("manager_first_name");
                        manager.last_name =
                                rset.getString("manager_last_name");

                        dept.manager = manager;
                    }

                    return dept;
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get department");
        }

        return null;
    }

    /**
     * Retrieve current salaries of employees in a department.
     *
     * @param dept Department to search.
     * @return List of employees and their salaries.
     */
    public ArrayList<Employee> getSalariesByDepartment(Department dept)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        if (con == null || dept == null)
        {
            System.out.println("No database connection or department");
            return employees;
        }

        String strSelect =
                "SELECT e.emp_no, e.first_name, e.last_name, s.salary " +
                        "FROM employees e " +
                        "JOIN salaries s ON e.emp_no = s.emp_no " +
                        "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "WHERE s.to_date = '9999-01-01' " +
                        "AND de.to_date = '9999-01-01' " +
                        "AND de.dept_no = ? " +
                        "ORDER BY e.emp_no ASC";

        try (PreparedStatement stmt = con.prepareStatement(strSelect))
        {
            stmt.setString(1, dept.dept_no);

            try (ResultSet rset = stmt.executeQuery())
            {
                while (rset.next())
                {
                    Employee emp = new Employee();

                    emp.emp_no = rset.getInt("emp_no");
                    emp.first_name = rset.getString("first_name");
                    emp.last_name = rset.getString("last_name");
                    emp.salary = rset.getInt("salary");
                    emp.dept = dept;

                    employees.add(emp);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salaries by department");
        }

        return employees;
    }

    /**
     * Display the salary report for a given department.
     *
     * @param departmentName Department name.
     */
    public void displayDepartmentSalaryReport(String departmentName)
    {
        Department dept = getDepartment(departmentName);

        if (dept == null)
        {
            System.out.println("Department not found.");
            return;
        }

        System.out.println(
                "\nSalary Report for Department: " + dept.dept_name
        );

        System.out.println(
                "Employee Number | First Name | Last Name | Salary"
        );

        ArrayList<Employee> employees =
                getSalariesByDepartment(dept);

        for (Employee employee : employees)
        {
            System.out.printf(
                    "%-15d %-18s %-20s %d%n",
                    employee.emp_no,
                    employee.first_name,
                    employee.last_name,
                    employee.salary
            );
        }

        System.out.println(
                "Total employees: " + employees.size()
        );
    }

    /**
     * Find an employee by first name and last name.
     *
     * If multiple employees have the same name,
     * the employee with the lowest employee number is returned.
     *
     * @param firstName Employee's first name.
     * @param lastName Employee's last name.
     * @return Employee object or null if not found.
     */
    public Employee getEmployeeByName(String firstName, String lastName)
    {
        if (con == null)
        {
            System.out.println("No database connection");
            return null;
        }

        String strSelect =
                "SELECT emp_no " +
                        "FROM employees " +
                        "WHERE first_name = ? " +
                        "AND last_name = ? " +
                        "ORDER BY emp_no ASC " +
                        "LIMIT 1";

        try (PreparedStatement stmt = con.prepareStatement(strSelect))
        {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);

            int employeeNumber = -1;

            try (ResultSet rset = stmt.executeQuery())
            {
                if (rset.next())
                {
                    employeeNumber = rset.getInt("emp_no");
                }
            }

            if (employeeNumber != -1)
            {
                return getEmployee(employeeNumber);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to find employee by name");
        }

        return null;
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                con.close();
                con = null;
            }
            catch (SQLException e)
            {
                System.out.println(
                        "Error closing connection to database"
                );
            }
        }
    }
}
