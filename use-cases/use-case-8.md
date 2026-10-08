
# USE CASE: 8 Delete an Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to delete an employee's details* so that *the company is compliant with data retention legislation.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The employee record exists in the database. The HR advisor has confirmation that the record is eligible for deletion under the organisation's data retention policy.

### Success End Condition

The eligible employee information is successfully deleted from the database.

### Failed End Condition

The employee information remains in the database.

### Primary Actor

HR Advisor.

### Trigger

An employee's information reaches the end of its required retention period.

## MAIN SUCCESS SCENARIO

1. HR advisor identifies an employee record requiring deletion.
2. HR advisor checks that the record is eligible for deletion under the data retention policy.
3. HR advisor locates the employee record in the HR system.
4. The system displays the employee's information for confirmation.
5. HR advisor confirms the deletion request.
6. The system deletes the eligible employee information from the database.
7. HR advisor receives confirmation that the deletion was successful.

## EXTENSIONS

2. **Record must still be retained**:
    1. HR advisor determines that the employee's information must be kept.
    2. The deletion process is cancelled.

3. **Employee record does not exist**:
    1. The system cannot locate the employee record.
    2. HR advisor is informed that no record is available for deletion.

6. **Database deletion fails**:
    1. The system reports that the deletion was unsuccessful.
    2. The employee's information remains unchanged.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
