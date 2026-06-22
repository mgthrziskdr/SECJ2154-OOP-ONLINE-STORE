package com.g07.onlinestore.Person.Staff;

public class Manager extends Staff {
  // Attributes
  private String department;
  private double bonusRate;

  // Constructor Manager
  public Manager(
    String personId,
    String name,
    String email,
    String phoneNumber,
    String staffId,
    double salary,
    String department,
    double bonusRate
  ) {
    super(
      personId,
      name,
      email,
      phoneNumber,
      staffId,
      salary
    );

    this.department = department;
    this.bonusRate = bonusRate;
  }

  // Calculate payroll
  @Override
  public double calculatePayroll() {
    return getSalary() + (getSalary() * bonusRate);
  }

  // Role description
  @Override
  public String getRoleDuties() {
    return "Manage store operations and supervise staff";
  }

  // Override getDetails to include manager-specific information
  @Override
  public void getDetails() {
    super.getDetails();
    System.out.println("Department: " + department);
    System.out.println("Bonus Rate: " + (bonusRate * 100) + "%");
  }

  // Getter methods
  public String getDepartment() {
    return department;
  }

  public double getBonusRate() {
    return bonusRate;
  }
}