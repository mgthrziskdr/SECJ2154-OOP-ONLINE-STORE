package com.g07.onlinestore.Person.Staff;

public class RegularStaff extends Staff {

  public RegularStaff(String personId, String name, String email,
      String phone, String staffId, double salary) {
    super(personId, name, email, phone, staffId, salary);
  }

  // Calculate payroll
  @Override
  public double calculatePayroll() {
    return getSalary();
  }

  // Role description
  @Override
  public String getRoleDuties() {
    return "A regular staff.";
  }
}