package com.g07.onlinestore.Payment;

import javax.swing.JOptionPane;

public class Payment {

  // Attributes
  private String paymentId;
  private PaymentMethod paymentMethod;
  private double amount;
  private double transactionFee;
  private String status;
  private String selectedBank;

  // ENUM (inside same file)
  public enum PaymentMethod {
    CREDIT_CARD,
    FPX_BANKING,
    CASH_ON_DELIVERY
  }

  // Constructor Payment
  public Payment(
      String paymentId,
      PaymentMethod paymentMethod,
      double amount) {
    this.paymentId = paymentId;
    this.paymentMethod = paymentMethod;
    this.amount = amount;

    this.transactionFee = 0;
    this.status = "Pending";
  }

  // Process payment
  public void processPayment() {
    try {
      if (amount <= 0) {
        throw new IllegalArgumentException("Payment amount must be greater than 0");
      }

      PaymentMethod[] options = PaymentMethod.values();

      PaymentMethod selectedMethod = (PaymentMethod) JOptionPane.showInputDialog(
          null,
          "Select Payment Method",
          "Payment",
          JOptionPane.QUESTION_MESSAGE,
          null,
          options,
          options[0]);

      if (selectedMethod == null) {
        status = "Failed";
        return;
      }

      this.paymentMethod = selectedMethod;

      switch (paymentMethod) {

        case CREDIT_CARD:
          selectBankUI();
          JOptionPane.showMessageDialog(null, "[PROCESS] Processing Credit/Debit Card Payment...");
          transactionFee = 0;
          status = "Successful";
          break;

        case FPX_BANKING:
          selectBankUI();
          JOptionPane.showMessageDialog(null, "[PROCESS] Processing FPX Banking Payment...");
          transactionFee = 0;
          status = "Successful";
          break;

        case CASH_ON_DELIVERY:
          JOptionPane.showMessageDialog(null, "[PROCESS] Sharing COD Request with Store...");
          transactionFee = 0;
          status = "Successful";
          break;
      }

    } catch (Exception e) {
      status = "Failed";
      JOptionPane.showMessageDialog(null, "[ERROR] " + e.getMessage());
    }
  }

  // Calculate final amount
  public double getFinalAmount() {
    return amount + transactionFee;
  }

  // Getters
  public String getPaymentId() {
    return paymentId;
  }

  public PaymentMethod getPaymentMethod() {
    return paymentMethod;
  }

  public double getAmount() {
    return amount;
  }

  public double getTransactionFee() {
    return transactionFee;
  }

  public String getStatus() {
    return status;
  }

  // Payment details
  public void showPaymentDetails() {
    JOptionPane.showMessageDialog(null,
        "Payment ID: " + paymentId + "\n"
            + "Method: " + paymentMethod + "\n"
            + "Bank: " + selectedBank + "\n"
            + "Amount: RM " + amount + "\n"
            + "Transaction Fee: RM " + transactionFee + "\n"
            + "Final Amount: RM " + getFinalAmount() + "\n"
            + "Status: " + status);
  }

  // Bank selection
  private void selectBankUI() {

    String[] banks = {
        "Maybank", "CIMB Bank", "Public Bank", "RHB Bank",
        "Hong Leong Bank", "AmBank", "Bank Islam",
        "Bank Rakyat", "Alliance Bank", "OCBC Bank",
        "UOB Malaysia", "HSBC Malaysia"
    };

    selectedBank = (String) JOptionPane.showInputDialog(
        null,
        "Select Bank",
        "Bank Selection",
        JOptionPane.QUESTION_MESSAGE,
        null,
        banks,
        banks[0]);
  }
}