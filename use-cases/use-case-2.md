
# USE CASE: 2 Produce a Report on the Salary of Employees in a Department

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to produce a report on the salary of employees in a department* so that *I can support financial reporting of the organisation.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The department is known. The database contains current employee salary and department information.

### Success End Condition

A salary report for the selected department is available for HR to provide to finance.

### Failed End Condition

No report is produced.

### Primary Actor

HR Advisor.

### Trigger

Finance requests salary information for a particular department.

## MAIN SUCCESS SCENARIO

1. Finance requests salary information for a department.
2. HR advisor identifies the required department.
3. HR advisor retrieves the current salary information for employees working in that department.
4. HR advisor prepares the department salary report.
5. HR advisor provides the report to finance.

## EXTENSIONS

3. **Department does not exist**:
    1. HR advisor informs finance that the requested department could not be found.

3. **No salary records available**:
    1. HR advisor informs finance that salary information is unavailable for the selected department.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
