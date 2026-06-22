package com.g07.onlinestore.Person.Staff;

import com.g07.onlinestore.Person.Person;

public abstract class Staff extends Person {
  // Attributes
  private String staffId;
  private double salary;

  // Constructor Staff
  public Staff(
    String personId,
    String name,
    String email,
    String phoneNumber,
    String staffId,
    double salary
  ) {
    super(personId, name, email, phoneNumber);

    this.staffId = staffId;
    this.salary = salary;
  }

  // Abstract method
  public abstract double calculatePayroll();

  // Getter methods
  public String getStaffId() {
    return staffId;
  }

  public double getSalary() {
    return salary;
  }

  @Override
  public void getDetails() {
    System.out.println("Staff ID: " + staffId);
    System.out.println("Name: " + getName());
    System.out.println("Email: " + getEmail());
    System.out.println("Phone Number: " + getPhoneNumber());
    System.out.println("Salary: $" + salary);
  }
}