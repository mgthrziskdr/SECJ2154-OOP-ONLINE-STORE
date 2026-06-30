package com.g07.onlinestore;

import com.g07.onlinestore.Store.Store;
import com.g07.onlinestore.Product.*;
import com.g07.onlinestore.Order.Order;
import com.g07.onlinestore.Payment.Payment;
import com.g07.onlinestore.Payment.Payment.PaymentMethod;
import com.g07.onlinestore.Person.Customer;
import com.g07.onlinestore.Person.Staff.*;
import com.g07.onlinestore.DAO.*;
import com.g07.onlinestore.config.db.DBConnect;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.util.Random;

public class SysMain {

  private static String customerId;

  public static String createRandomOrder(String prefix) {
    Random random = new Random();
    return prefix + (1000 + random.nextInt(9000));
  }

  public static void main(String[] args) {

    DBConnect dbConnect = new DBConnect();
    Connection conn = DBConnect.getConnection();
    dbConnect.DBTestConnect(conn);

    Store store = new Store("My Online Store");

    ProductDAO productDAO = new ProductDAO();
    OrderDAO orderDAO = new OrderDAO();
    PaymentDAO paymentDAO = new PaymentDAO();
    AuthDAO authDAO = new AuthDAO();
    CustomerDAO custDAO = new CustomerDAO();
    StaffDAO staffDAO = new StaffDAO();

    try {

      String[] options = { "Yes", "No" };

      int opt = JOptionPane.showOptionDialog(
          null,
          "Are you a new user?",
          "Welcome!",
          0,
          3,
          null,
          options,
          options[0]);

      // =========================
      // NEW CUSTOMER
      // =========================
      if (opt == 0) {

        String name = JOptionPane.showInputDialog(null, "Enter Name:", "New Customer", JOptionPane.PLAIN_MESSAGE);
        String email = JOptionPane.showInputDialog(null, "Enter Email:", "New Customer", JOptionPane.PLAIN_MESSAGE);
        String phone = JOptionPane.showInputDialog(null, "Enter Phone:", "New Customer", JOptionPane.PLAIN_MESSAGE);

        String personId = createRandomOrder("PRN");
        String customerIdGen = createRandomOrder("CUST");

        Customer newCustomer = new Customer(
            personId,
            name,
            email,
            phone,
            customerIdGen);

        custDAO.insertCustomer(newCustomer);

        JOptionPane.showMessageDialog(null, "Customer Registered Successfully!", "Success!",
            JOptionPane.INFORMATION_MESSAGE);
      }

      // =========================
      // LOGIN
      // =========================
      if (opt == 1) {

        String username = JOptionPane.showInputDialog(null, "Enter Name:", "Welcome Back!", JOptionPane.PLAIN_MESSAGE);
        String email = JOptionPane.showInputDialog(null, "Enter Email:", "Welcome Back!", JOptionPane.PLAIN_MESSAGE);

        String[] roleList = { "Customer", "Staff", "Manager" };

        String role = (String) JOptionPane.showInputDialog(
            null,
            "1. Customer\n2. Staff\n3. Manager\n\nEnter role:",
            "Select a Role",
            JOptionPane.PLAIN_MESSAGE,
            null,
            roleList,
            roleList[0]);

        if (role == "Customer") {
          role = "1";
        } else if (role == "Staff") {
          role = "2";
        } else if (role == "Manager") {
          role = "3";
        }

        String loginResult = authDAO.login(username, email);

        if (loginResult == null) {
          JOptionPane.showMessageDialog(null, "Login Failed!", "Fail?", JOptionPane.WARNING_MESSAGE);
          return;
        }

        if (loginResult.equals("CUSTOMER")) {
          customerId = custDAO.getCustomerId(username, email);
        }

        JOptionPane.showMessageDialog(null, "Login Success!", "Success!", JOptionPane.INFORMATION_MESSAGE);

        // =========================
        // STAFF / MANAGER MENU
        // =========================
        if (role.equals("2") || role.equals("3")) {

          while (true) {

            String menu = """
                1. Add Product
                2. Delete Product
                3. Update Product
                4. View Products
                5. View Orders
                6. Add Staff (Manager Only)
                7. Exit
                """;

            String choice = JOptionPane.showInputDialog(null, menu, "Staff & Manager Menu", JOptionPane.PLAIN_MESSAGE);
            if (choice == null)
              break;

            switch (choice) {

              case "1": {

                String type = JOptionPane.showInputDialog(null, "1. Electronic\n2. Clothing\n3. Food\n\n",
                    "Add New Product",
                    JOptionPane.PLAIN_MESSAGE);

                String name = JOptionPane.showInputDialog(null, "Name:", "Add New Product", JOptionPane.PLAIN_MESSAGE);

                double price = Double.parseDouble(
                    JOptionPane.showInputDialog(null, "Price:", "Add New Product", JOptionPane.PLAIN_MESSAGE));

                int stock = Integer.parseInt(
                    JOptionPane.showInputDialog(null, "Stock:", "Add New Product", JOptionPane.PLAIN_MESSAGE));

                Product p = null;

                if (type.equals("1")) {
                  p = new Electronic(
                      createRandomOrder("E"), name, price, stock,
                      Integer.parseInt(JOptionPane.showInputDialog(null, "Warranty:", "Electronic Category",
                          JOptionPane.PLAIN_MESSAGE)),
                      JOptionPane.showInputDialog(null, "Brand:", "Electronic Category", JOptionPane.PLAIN_MESSAGE));

                } else if (type.equals("2")) {
                  p = new Clothing(
                      createRandomOrder("C"), name, price, stock,
                      JOptionPane.showInputDialog(null, "Size:", "Clothing Category", JOptionPane.PLAIN_MESSAGE),
                      JOptionPane.showInputDialog(null, "Material:", "Clothing Category", JOptionPane.PLAIN_MESSAGE));

                } else if (type.equals("3")) {
                  p = new Food(
                      createRandomOrder("F"), name, price, stock,
                      JOptionPane.showInputDialog(null, "Expiry Date:", "Food Category", JOptionPane.PLAIN_MESSAGE),
                      JOptionPane.showInputDialog(null, "Category:", "Food Category", JOptionPane.PLAIN_MESSAGE));
                }

                if (p != null) {
                  store.addProduct(p);
                  productDAO.insertProduct(p);
                  JOptionPane.showMessageDialog(null, "Product Added!", "Add Log", JOptionPane.INFORMATION_MESSAGE);
                }

                break;
              }

              case "2": {
                String id = JOptionPane.showInputDialog(null, productDAO.getListOfProducts() + "\nProduct ID:",
                    "Delete Log", JOptionPane.PLAIN_MESSAGE);
                productDAO.deleteProduct(id);
                JOptionPane.showMessageDialog(null, "Product Deleted!", "Delete Log", JOptionPane.INFORMATION_MESSAGE);
                break;
              }

              case "3": {

                String id = JOptionPane.showInputDialog(null, productDAO.getListOfProducts() + "\nProduct ID:",
                    "Update Log",
                    JOptionPane.PLAIN_MESSAGE);

                if (!productDAO.productExists(id)) {

                  JOptionPane.showMessageDialog(
                      null,
                      "Product not found!");

                  break;
                }

                String newName = JOptionPane.showInputDialog(null, "New Name:", "Update Log",
                    JOptionPane.PLAIN_MESSAGE);
                double newPrice = Double.parseDouble(
                    JOptionPane.showInputDialog(null, "New Price:", "Update Log", JOptionPane.PLAIN_MESSAGE));
                int newStock = Integer.parseInt(
                    JOptionPane.showInputDialog(null, "New Stock:", "Update Log", JOptionPane.PLAIN_MESSAGE));

                Product updatedProduct;

                // keep original type
                String type = productDAO.getProductType(id);

                switch (type) {

                  case "Electronic":

                    String newBrand = JOptionPane.showInputDialog(null, "New Brand:", "Update Log",
                    JOptionPane.PLAIN_MESSAGE);

                    int newWarrantyPeriod = Integer.parseInt(
                    JOptionPane.showInputDialog(null, "New Warranty Period:", "Update Log", JOptionPane.PLAIN_MESSAGE));

                    updatedProduct = new Electronic(
                        id,
                        newName,
                        newPrice,
                        newStock,
                        newWarrantyPeriod,
                        newBrand);

                    break;

                  case "Clothing":
                    
                    String newSize = JOptionPane.showInputDialog(null, "New Size:", "Update Log",
                    JOptionPane.PLAIN_MESSAGE);

                    String newMaterial = JOptionPane.showInputDialog(null, "New Material:", "Update Log",
                    JOptionPane.PLAIN_MESSAGE);

                    updatedProduct = new Clothing(
                        id,
                        newName,
                        newPrice,
                        newStock,
                        newSize,
                        newMaterial);

                    break;

                  case "Food":

                    String newExpiryDate = JOptionPane.showInputDialog(null, "New Expiry Date:", "Update Log",
                    JOptionPane.PLAIN_MESSAGE);

                    String newCategory = JOptionPane.showInputDialog(null, "New Category:", "Update Log",
                    JOptionPane.PLAIN_MESSAGE);

                    updatedProduct = new Food(
                        id,
                        newName,
                        newPrice,
                        newStock,
                        newExpiryDate,
                        newCategory);

                    break;

                  default:

                    JOptionPane.showMessageDialog(
                        null,
                        "Unknown product type",
                        "Product?",
                        JOptionPane.WARNING_MESSAGE
                    );

                    updatedProduct = null;
                    break;

                }

                productDAO.updateProduct(updatedProduct);

                JOptionPane.showMessageDialog(null, "Product Updated!", "Update Log", JOptionPane.INFORMATION_MESSAGE);
                break;
              }

              case "4": {
                JOptionPane.showMessageDialog(null, productDAO.getListOfProducts(), "List Of Products",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
              }

              case "5": {
                JOptionPane.showMessageDialog(null, orderDAO.getAllOrders(), "Incoming Orders",
                    JOptionPane.INFORMATION_MESSAGE);

                System.out.println(orderDAO.getAllOrders());
                break;
              }

              case "6": {

                if (!role.equals("3")) {
                  JOptionPane.showMessageDialog(null, "Only Manager allowed!", "Warning!", JOptionPane.WARNING_MESSAGE);
                  break;
                }

                String staffType = JOptionPane.showInputDialog(null, "1.Manager\n2.Delivery", "",
                    JOptionPane.PLAIN_MESSAGE);

                String name = JOptionPane.showInputDialog(null, "Name:", "Enter Staff Name", JOptionPane.PLAIN_MESSAGE);
                String emailStaff = JOptionPane.showInputDialog(null, "Email:", "Enter Staff Email",
                    JOptionPane.PLAIN_MESSAGE);
                String phone = JOptionPane.showInputDialog(null, "Phone:", "Enter Staff H.P",
                    JOptionPane.PLAIN_MESSAGE);
                double salary = Double.parseDouble(
                    JOptionPane.showInputDialog(null, "Salary:", "Enter Staff Salary", JOptionPane.PLAIN_MESSAGE));

                String personId = createRandomOrder("PRN");
                String staffId = createRandomOrder("STF");

                Staff s = null;

                if (staffType.equals("1")) {
                  String department = JOptionPane.showInputDialog(null, "Department:", "Enter Staff Department",
                      JOptionPane.PLAIN_MESSAGE);
                  double bonusRate = Double.parseDouble(JOptionPane.showInputDialog(null, "Salary:",
                      "Enter Staff Bonus Rate", JOptionPane.PLAIN_MESSAGE));

                  s = new Manager(personId, name, emailStaff, phone, staffId, salary, department, bonusRate);

                } else if (staffType.equals("2")) {
                  String zone = JOptionPane.showInputDialog(null, "Delivery Zone:", "Enter a Delivery Zone",
                      JOptionPane.PLAIN_MESSAGE);
                  s = new DeliveryStaff(personId, name, emailStaff, phone, staffId, salary,
                      createRandomOrder("VAN"), zone);
                }

                if (s != null) {
                  staffDAO.insertStaff(s);
                  JOptionPane.showMessageDialog(null, "Staff Added!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                }

                break;
              }

              case "7":
                return;
            }
          }
        }

        // =========================
        // CUSTOMER MENU
        // =========================
        else if (role.equals("1")) {

          Order order = null;

          while (true) {

            String menu = """
                1. View Products
                2. Create Order
                3. Make Payment
                4. View Order
                5. Order History
                6. Exit
                """;

            String choice = JOptionPane.showInputDialog(null, menu, "Customer Menu", JOptionPane.PLAIN_MESSAGE);
            if (choice == null)
              break;

            switch (choice) {

              case "1": {
                JOptionPane.showMessageDialog(null, productDAO.getListOfProducts(), "List Of Products",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
              }

              // FIXED ORDER CREATION
              case "2": {

                order = new Order();

                String productId = JOptionPane.showInputDialog(
                    null,
                    productDAO.getListOfProducts() + "\nEnter Product ID:",
                    "Create New Order",
                    JOptionPane.PLAIN_MESSAGE);

                if (!productDAO.productExists(productId)) {
                  JOptionPane.showMessageDialog(null, "Invalid Product!", "Invalid!", JOptionPane.WARNING_MESSAGE);
                  break;
                }

                Product p = new Electronic(
                    productId,
                    "ORDER_ITEM",
                    0,
                    1,
                    0,
                    "N/A");

                order.addItem(p);

                store.addOrder(order);
                orderDAO.insertOrder(order, customerId);

                JOptionPane.showMessageDialog(null, "Order Created!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                break;
              }

              case "3": {

                if (order == null) {
                  JOptionPane.showMessageDialog(null, "Create order first!", "Order?", JOptionPane.WARNING_MESSAGE);
                  break;
                }

                double customerTotal = orderDAO.calculateCustomerTotal(customerId);

                Payment payment = new Payment(
                    paymentDAO.generatePaymentId(),
                    PaymentMethod.FPX_BANKING,
                    customerTotal);

                payment.processPayment();

                paymentDAO.insertPayment(payment, order.getOrderId());

                JOptionPane.showMessageDialog(null, "Payment Successful!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                break;
              }

              case "4": {
                if (order == null) {
                  JOptionPane.showMessageDialog(null, "No order yet!", "Order?", JOptionPane.WARNING_MESSAGE);
                } else {
                  JOptionPane.showMessageDialog(null,
                      "Order ID: " + order.getOrderId() +
                          "\nTotal: " + order.calculateTotal(),
                      "Total Payment", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
              }

              case "5": {
                JOptionPane.showMessageDialog(
                    null,
                    orderDAO.getCustomerOrderHistory(customerId),
                    "Order History",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
              }

              case "6":
                return;
            }
          }
        }
      }

    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
    }
  }
}