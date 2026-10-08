
# USE CASE: 1 Produce a Report on the Salary of All Employees

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to produce a report on the salary of all employees* so that *I can support financial reporting of the organisation.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The database contains current employee and salary information.

### Success End Condition

A report containing the current salaries of all employees is available for HR to provide to finance.

### Failed End Condition

No report is produced.

### Primary Actor

HR Advisor.

### Trigger

A request for employee salary information is sent to HR.

## MAIN SUCCESS SCENARIO

1. Finance requests salary information for all employees.
2. HR advisor accesses the employee salary records.
3. HR advisor retrieves the current salary information for all employees.
4. HR advisor produces the salary report.
5. HR advisor provides the report to finance.

## EXTENSIONS

3. **No employee salary records found**:
    1. HR advisor checks whether the required salary information is available.
    2. HR advisor informs finance that the report cannot be produced.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
