package com.g07.onlinestore.Payment;

public class Payment {
  // Attributes
  private String paymentId;
  private String paymentMethod;
  private double amount;
  private double transactionFee;
  private String status;

  // Constructor Payment
  public Payment(
    String paymentId,
    String paymentMethod,
    double amount
  ) {
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

      switch (paymentMethod.toLowerCase()) {
        case "credit/debit card":
          transactionFee = amount * 0.0;
          break;

        case "fpx banking":
          transactionFee = amount * 0.0;
          break;

        case "cash on delivery":
          transactionFee = 0;
          break;

        default:
          throw new IllegalArgumentException("Invalid payment method");
      }

      status = "Successful";

    } catch (IllegalArgumentException e) {
      status = "Failed";
      System.out.println("[ERROR] " + e.getMessage());

    } catch (Exception e) {
      status = "Failed";
      System.out.println("[ERROR] Payment processing failed");
    }
  }

  // Calculate final amount
  public double getFinalAmount() {
    try {
      return amount + transactionFee;
    } catch (Exception e) {
      System.out.println("[ERROR] Cannot calculate payment amount");
      return 0;
    }
  }

  // Getters
  public String getPaymentId() {
    return paymentId;
  }

  public String getPaymentMethod() {
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
  public void printPaymentDetails() {
    try {
      System.out.println("Payment ID: " + paymentId);
      System.out.println("Method: " + paymentMethod);
      System.out.println("Amount: RM " + amount);
      System.out.println("Transaction Fee: RM " + transactionFee);
      System.out.println("Final Amount: RM " + getFinalAmount());
      System.out.println("Status: " + status);
    } catch (Exception e) {
      System.out.println("[ERROR] Unable to display payment details");
    }
  }
}

// package com.g07.onlinestore.Payment;

// import java.util.Scanner;

// public class Payment {
// private int paymentMethod;
// private int bankOption;
// private double transactionFee;

// Scanner input = new Scanner(System.in);

// public Payment(double transaction_fee) {
// this.transactionFee = transaction_fee;
// }

// public void bankSelection() {
// String[] banks = {
// "Maybank",
// "CIMB Bank",
// "Public Bank",
// "RHB Bank",
// "Hong Leong Bank",
// "AmBank",
// "Bank Islam",
// "Bank Rakyat",
// "Alliance Bank",
// "OCBC Bank",
// "UOB Malaysia",
// "HSBC Malaysia"
// };

// System.out.println("\n===========================");
// System.out.println("Select a Bank:");
// System.out.println("===========================");
// for (int i = 0; i < banks.length; i++) {
// System.out.println((i + 1) + ". " + banks[i]);
// }

// System.out.print("\nSelection: ");
// bankOption = input.nextInt();
// }

// private void returnPaymentRespond(double transaction_fee) {
// if (transaction_fee <= 0) {
// System.out.println("[WARNING] Balance Insuffient!");
// } else {
// System.out.println("[SUCCESS] Payment Successful!");
// }
// }

// public void processPayment() {
// String[] payMtd = {
// "Credit/Debit Card",
// "FPX Banking",
// "Cash on Delivery (COD)"
// };

// System.out.println("===========================");
// System.out.println("Select Payment Method:");
// System.out.println("===========================");
// for (int i = 0; i < payMtd.length; i++) {
// System.out.println((i + 1) + ". " + payMtd[i]);
// }

// System.out.print("\nSelection: ");
// paymentMethod = input.nextInt();

// switch (paymentMethod) {
// // Credit/Debit Card
// case 1:
// bankSelection();
// System.out.println("[PROCESS] Processing Credit/Debit Card Payment...");
// transactionFee += (transactionFee * 0);
// returnPaymentRespond(transactionFee);
// break;

// // FPX Banking
// case 2:
// bankSelection();
// System.out.println("[PROCESS] Processing FPX Banking Payment...");
// transactionFee += (transactionFee * 0);
// returnPaymentRespond(transactionFee);
// break;

// // Cash on Delivery (COD)
// case 3:
// System.out.println("[PROCESS] Sharing COD Request with Store...");
// break;

// default:
// break;
// }
// }

// public void printPaymentDetails() {

// }
// }
