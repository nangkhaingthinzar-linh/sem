
# USE CASE: 6 View an Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to view an employee's details* so that *the employee's promotion request can be supported.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The employee's ID is known. The database contains the employee's current employment information.

### Success End Condition

The employee's details are successfully retrieved and displayed for HR review.

### Failed End Condition

The employee's information cannot be retrieved or displayed.

### Primary Actor

HR Advisor.

### Trigger

An employee submits a promotion request requiring HR to review their employment details.

## MAIN SUCCESS SCENARIO

1. HR advisor receives an employee's promotion request.
2. HR advisor identifies the employee using their employee ID.
3. HR advisor requests the employee's details from the HR system.
4. The system retrieves the employee's current information from the database.
5. The system displays the employee's name, job title, salary, department and manager.
6. HR advisor reviews the information to support the promotion request.

## EXTENSIONS

4. **Employee record does not exist**:
    1. The system cannot find the requested employee.
    2. HR advisor is informed that no employee details are available.

4. **Database connection fails**:
    1. The system cannot retrieve the employee's information.
    2. HR advisor is informed that the information is temporarily unavailable.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
