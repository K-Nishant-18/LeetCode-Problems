# Write your MySQL query statement below
SELECT unique_id, name
FROM Employees e
Left Join EmployeeUNI u
On e.id=u.id;
