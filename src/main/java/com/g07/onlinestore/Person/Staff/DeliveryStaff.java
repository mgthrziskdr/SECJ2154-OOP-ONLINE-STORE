package com.g07.onlinestore.Person.Staff;

public class DeliveryStaff extends Staff {
  // Attributes
  private String vehicleNo;
  private String deliveryZone;

  // Constructor DeliveryStaff
  public DeliveryStaff(
    String personId,
    String name,
    String email,
    String phoneNumber,
    String staffId,
    double salary,
    String vehicleNo,
    String deliveryZone
  ) {
    super(
      personId,
      name,
      email,
      phoneNumber,
      staffId,
      salary
    );

    this.vehicleNo = vehicleNo;
    this.deliveryZone = deliveryZone;
  }

  // Calculate payroll
  @Override
  public double calculatePayroll() {
    return getSalary();
  }

  // Role description
  @Override
  public String getRoleDuties() {
    return "Deliver customer orders and manage delivery tasks";
  }

  // Override getDetails to include delivery staff-specific information
  @Override
  public void getDetails() {
    super.getDetails();
    System.out.println("Vehicle Number: " + vehicleNo);
    System.out.println("Delivery Zone: " + deliveryZone);
  }

  // Getter methods
  public String getVehicleNo() {
    return vehicleNo;
  }

  public String getDeliveryZone() {
    return deliveryZone;
  }
}