
# USE CASE: 5 Add a New Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to add a new employee's details* so that *I can ensure the new employee is paid.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The new employee's required personal, employment and salary information is available. The HR advisor can access the HR system.

### Success End Condition

The new employee's details are successfully recorded in the database and are available for payroll processing.

### Failed End Condition

The employee's details are not added to the database.

### Primary Actor

HR Advisor.

### Trigger

A new employee joins the organisation and their information needs to be recorded.

## MAIN SUCCESS SCENARIO

1. HR advisor receives the new employee's information.
2. HR advisor accesses the employee registration function.
3. HR advisor enters the employee's personal and employment details.
4. HR advisor enters the employee's salary and department information.
5. The system validates the provided information.
6. The system saves the new employee's details in the database.
7. HR advisor receives confirmation that the employee has been added.

## EXTENSIONS

5. **Required information is missing**:
    1. The system identifies the missing information.
    2. HR advisor completes the required fields.

6. **Employee record already exists**:
    1. The system identifies a duplicate employee record.
    2. HR advisor reviews the existing record before proceeding.

6. **Database error occurs**:
    1. The system reports that the employee could not be added.
    2. HR advisor checks the information and retries later.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
