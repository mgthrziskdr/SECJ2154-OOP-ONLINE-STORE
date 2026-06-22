package com.g07.onlinestore.Person;

public abstract class Person {
  // Attributes
  private String personId;
  private String name;
  private String email;
  private String phoneNumber;

  // Constructor Person
  public Person(String personId, String name, String email, String phoneNumber) {
    this.personId = personId;
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  // Abstract methods
  public abstract void getDetails();
  public abstract String getRoleDuties();

  // Getter methods
  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPersonId() {
    return personId;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}