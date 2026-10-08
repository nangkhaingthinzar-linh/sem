
# USE CASE: 7 Update an Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to update an employee's details* so that *the employee's details are kept up-to-date.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The employee already exists in the database. The HR advisor has the correct updated information.

### Success End Condition

The employee's information is successfully updated and stored in the database.

### Failed End Condition

The employee's information remains unchanged.

### Primary Actor

HR Advisor.

### Trigger

An employee's personal or employment information needs to be updated.

## MAIN SUCCESS SCENARIO

1. HR advisor receives a request to update an employee's information.
2. HR advisor identifies the employee using their employee ID.
3. The system retrieves the employee's existing details.
4. HR advisor reviews the current information.
5. HR advisor enters the updated employee information.
6. The system validates the changes.
7. The system saves the updated information in the database.
8. HR advisor receives confirmation that the changes were successful.

## EXTENSIONS

3. **Employee record does not exist**:
    1. The system cannot locate the employee.
    2. HR advisor is informed that the record cannot be updated.

6. **Invalid information entered**:
    1. The system identifies incorrect or incomplete information.
    2. HR advisor corrects the information before saving.

7. **Database update fails**:
    1. The system reports that the update was unsuccessful.
    2. HR advisor checks the information and retries when the system is available.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
