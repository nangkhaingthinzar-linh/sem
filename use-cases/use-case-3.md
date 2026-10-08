
# USE CASE: 3 Produce a Report on the Salary of Employees in My Department

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *department manager* I want *to produce a report on the salary of employees in my department* so that *I can support financial reporting for my department.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The department manager and their department are identified. The database contains current employee salary information.

### Success End Condition

A salary report for the manager's department is available to support departmental financial reporting.

### Failed End Condition

No report is produced.

### Primary Actor

Department Manager.

### Trigger

The department manager needs salary information for departmental financial reporting.

## MAIN SUCCESS SCENARIO

1. Department manager requests salary information for their department.
2. The HR system identifies the manager's department.
3. The system retrieves current salary information for employees in that department.
4. The system produces the department salary report.
5. Department manager reviews the report for financial reporting purposes.

## EXTENSIONS

2. **Department cannot be identified**:
    1. The system cannot determine the manager's department.
    2. The manager is informed that the report cannot be generated.

3. **No salary records found**:
    1. The system reports that no salary information is available.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
