
package com.napier.sem;

import java.sql.*;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Main method.
     */
    public static void main(String[] args)
    {
        App a = new App();

        // Connect to database
        a.connect();

        // Display an employee's details
        Employee emp = a.getEmployee(255530);
        a.displayEmployee(emp);

        // Display salary report for a given role
        a.displaySalariesByRole("Engineer");

        // Display salary report for all employees
        a.displaySalariesAllEmployees();

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
                        "Failed to connect to database attempt " + i
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
     * Get an employee by their ID.
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
                        "t.title, s.salary, d.dept_name, " +
                        "CONCAT(m.first_name, ' ', m.last_name) AS manager " +
                        "FROM employees e " +
                        "JOIN titles t ON e.emp_no = t.emp_no " +
                        "AND t.to_date = '9999-01-01' " +
                        "JOIN salaries s ON e.emp_no = s.emp_no " +
                        "AND s.to_date = '9999-01-01' " +
                        "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "AND de.to_date = '9999-01-01' " +
                        "JOIN departments d ON de.dept_no = d.dept_no " +
                        "JOIN dept_manager dm ON de.dept_no = dm.dept_no " +
                        "AND dm.to_date = '9999-01-01' " +
                        "JOIN employees m ON dm.emp_no = m.emp_no " +
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
                    emp.dept_name = rset.getString("dept_name");
                    emp.manager = rset.getString("manager");

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
     * Display an employee's details.
     */
    public void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept_name + "\n"
                            + "Manager: " + emp.manager + "\n"
            );
        }
        else
        {
            System.out.println("Employee not found.");
        }
    }

    /**
     * Display current salaries of employees with a given role.
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

                System.out.println(
                        "Total employees: " + count
                );
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