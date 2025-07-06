import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;

public class api {
    public static void main(String[] args) {
        try {
            int flag = 0;
            Scanner s = new Scanner(System.in);
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/database_1";
            String user = "postgres";
            String password = "Vishvam@0917";
            String sql;
            ResultSet rs;
            Connection conn = DriverManager.getConnection(url, user, password);

            Statement stmt = conn.createStatement();
            stmt.execute("SET search_path TO public");

            System.out.println("Enter the query number that you want to execute (1 to 20) (enter 0 to exit): ");
            System.out.println("1. Top 10 places having most expensive dishes");
            System.out.println("2. Top 5 Restaurants with Highest Average Rating");
            System.out.println("3. Customers Who Have Used Coupons");
            System.out.println("4. Orders by Customers in Specific Area");
            System.out.println("5. Delivery Personnel with the Highest Average Rating");
            System.out.println("6.  List of Orders with Pending Payment Status");
            System.out.println("7. Customer Feedback with Below Average Ratings");
            System.out.println("8. Top 5 Most Ordered Dishes");
            System.out.println("9. Most Popular Payment Method");
            System.out.println("10. Orders Delivered by a Specific Delivery Personnel");
            System.out.println("11. Average Order Value by Restaurant");
            System.out.println("12. Total Number of Orders for Each Day in the Last Week");
            System.out.println("13. Restaurants That Serve the Same Dish");
            System.out.println("14. Percentage of Orders with Each Payment Method");
            System.out.println("15. Most Popular Cuisine Type");
            System.out.println("16. Most Common Delivery Locations");
            System.out.println("17. Customers with No Active Coupons");
            System.out.println("18. Restaurants with the Highest Number of Menu Items");
            System.out.println("19. Average Rating Per Cuisine Type");
            System.out.println("20. Restaurants with the Longest Operating Hours");

            while (true) {
                int choice = s.nextInt();

                switch (choice) {
                    case 0:
                        System.out.println("Thank You! Exiting...");
                        flag = 1;
                        break;
                    case 1:
                        sql = "SELECT r.Name AS Restaurant, m.Dish_name, m.Price\r\n" + //
                                "FROM Restaurant r\r\n" + //
                                "JOIN Menu m ON r.Restaurant_id = m.Restaurant_id\r\n" + //
                                "ORDER BY m.Price DESC\r\n" + //
                                "LIMIT 10";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String name = rs.getString("Restaurant");
                            String dish_name = rs.getString("dish_name");
                            int price = rs.getInt("price");
                            System.out
                                    .println("Restaurant: " + name + ", dish name: " + dish_name + ", price: " + price);
                        }
                        break;

                    case 2:
                        sql = "SELECT r.Name AS Restaurant, AVG(rr.Rating) AS Average_Rating\r\n" + //
                                "FROM Restaurant r\r\n" + //
                                "JOIN Ratings_and_Review rr ON r.Restaurant_id = rr.Restaurant_id\r\n" + //
                                "GROUP BY r.Name\r\n" + //
                                "ORDER BY Average_Rating DESC\r\n" + //
                                "LIMIT 5";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String name = rs.getString("Restaurant");
                            float avg = rs.getFloat("Average_rating");
                            System.out.println("Restaurant: " + name + ", Average rating: " + avg);
                        }
                        break;

                    case 3:
                        sql = "SELECT c.Name AS Customer, cu.CouponCode, cu.DiscountAmount\r\n" + //
                                "FROM Customer c\r\n" + //
                                "JOIN Coupons cu ON c.Customer_id = cu.Customer_id";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String name = rs.getString("Customer");
                            String cc = rs.getString("Couponcode");
                            float amt = rs.getFloat("Discountamount");
                            System.out
                                    .println("Customer: " + name + ", Coupon code: " + cc + ", Discount amount" + amt);
                        }
                        break;

                    case 4:
                        sql = "SELECT o.Order_id, c.Name AS Customer, o.Item_Ordered, o.Total_Amount, " +
                                "o.Order_date_and_time " +
                                "FROM Orders o " +
                                "JOIN Customer c ON o.Customer_id = c.Customer_id " +
                                "JOIN CustomerAddresses ca ON c.Customer_id = ca.CustomerID " +
                                "WHERE ca.DeliveryAddress LIKE '%Kalawad Road%'";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            int orderId = rs.getInt("Order_id");
                            String customerName = rs.getString("Customer");
                            String itemOrdered = rs.getString("Item_Ordered");
                            double totalAmount = rs.getDouble("Total_Amount");
                            Timestamp orderDateTime = rs.getTimestamp("Order_date_and_time");

                            System.out.println("Order ID: " + orderId +
                                    ", Customer: " + customerName +
                                    ", Item Ordered: " + itemOrdered +
                                    ", Total Amount: " + totalAmount +
                                    ", Order Date and Time: " + orderDateTime);
                        }

                        break;

                    case 5:
                        sql = "SELECT dp.Name AS Delivery_Personnel, " +
                                "AVG(fdp.Delivery_Person_Rating) AS Average_Rating " +
                                "FROM Delivery_Personnel dp " +
                                "JOIN Feedback_for_Delivery_Personnel fdp ON dp.Delivery_Person_id = fdp.Delivery_Person_id "
                                +
                                "GROUP BY dp.Name " +
                                "ORDER BY Average_Rating DESC " +
                                "LIMIT 5";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String deliveryPersonnel = rs.getString("Delivery_Personnel");
                            double averageRating = rs.getDouble("Average_Rating");
                            System.out.println(
                                    "Delivery Personnel: " + deliveryPersonnel + ", Average Rating: " + averageRating);
                        }

                        break;

                    case 6:
                        sql = "SELECT o.Order_id, c.Name AS Customer, o.Total_Amount, o.Payment_Status " +
                                "FROM Orders o " +
                                "JOIN Customer c ON o.Customer_id = c.Customer_id " +
                                "WHERE o.Payment_Status = 'COD'";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            int orderId = rs.getInt("Order_id");
                            String customerName = rs.getString("Customer");
                            double totalAmount = rs.getDouble("Total_Amount");
                            String paymentStatus = rs.getString("Payment_Status");

                            System.out.println("Order ID: " + orderId +
                                    ", Customer: " + customerName +
                                    ", Total Amount: " + totalAmount +
                                    ", Payment Status: " + paymentStatus);
                        }

                        break;

                    case 7:
                        sql = "SELECT c.Name AS Customer, r.Name AS Restaurant, rr.Rating, rr.Review_text " +
                                "FROM Ratings_and_Review rr " +
                                "JOIN Customer c ON rr.Customer_id = c.Customer_id " +
                                "JOIN Restaurant r ON rr.Restaurant_id = r.Restaurant_id " +
                                "WHERE rr.Rating < 3";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String customerName = rs.getString("Customer");
                            String restaurantName = rs.getString("Restaurant");
                            int rating = rs.getInt("Rating");
                            String reviewText = rs.getString("Review_text");

                            System.out.println("Customer: " + customerName +
                                    ", Restaurant: " + restaurantName +
                                    ", Rating: " + rating +
                                    ", Review: " + reviewText);
                        }

                        break;

                    case 8:
                        sql = "SELECT o.Item_Ordered, COUNT(o.Order_id) AS Times_Ordered " +
                                "FROM Orders o " +
                                "GROUP BY o.Item_Ordered " +
                                "ORDER BY Times_Ordered DESC " +
                                "LIMIT 5";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String itemOrdered = rs.getString("Item_Ordered");
                            int timesOrdered = rs.getInt("Times_Ordered");

                            System.out.println("Item Ordered: " + itemOrdered + ", Times Ordered: " + timesOrdered);
                        }

                        break;

                    case 9:
                        sql = "SELECT o.Payment_Status AS Payment_Method, COUNT(o.Order_id) AS Number_of_Transactions "
                                +
                                "FROM Orders o " +
                                "GROUP BY o.Payment_Status " +
                                "ORDER BY Number_of_Transactions DESC";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String paymentMethod = rs.getString("Payment_Method");
                            int transactionCount = rs.getInt("Number_of_Transactions");

                            System.out.println("Payment Method: " + paymentMethod +
                                    ", Number of Transactions: " + transactionCount);
                        }

                        break;

                    case 10:
                        sql = "SELECT o.Order_id, o.Item_Ordered, o.Order_date_and_time, o.Total_Amount " +
                                "FROM Orders o " +
                                "WHERE o.Delivery_Person_id = 3";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            int orderId = rs.getInt("Order_id");
                            String itemOrdered = rs.getString("Item_Ordered");
                            Timestamp orderDateTime = rs.getTimestamp("Order_date_and_time");
                            double totalAmount = rs.getDouble("Total_Amount");

                            System.out.println("Order ID: " + orderId +
                                    ", Item Ordered: " + itemOrdered +
                                    ", Order Date and Time: " + orderDateTime +
                                    ", Total Amount: $" + totalAmount);
                        }

                        break;

                    case 11:
                        sql = "SELECT r.Name AS Restaurant, AVG(o.Total_Amount) AS Average_Order_Value " +
                                "FROM Restaurant r " +
                                "JOIN Orders o ON r.Restaurant_id = o.Restaurant_id " +
                                "GROUP BY r.Name " +
                                "ORDER BY Average_Order_Value DESC";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String restaurantName = rs.getString("Restaurant");
                            double avgOrderValue = rs.getDouble("Average_Order_Value");

                            System.out.println("Restaurant: " + restaurantName +
                                    ", Average Order Value: $" + avgOrderValue);
                        }

                        break;

                    case 12:
                        sql = "SELECT CAST(o.Order_date_and_time AS DATE) AS Order_Date, " +
                                "COUNT(o.Order_id) AS Total_Orders " +
                                "FROM Orders o " +
                                "WHERE o.Order_date_and_time >= NOW() - INTERVAL '30 days' " +
                                "GROUP BY Order_Date " +
                                "ORDER BY Order_Date";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            Date orderDate = rs.getDate("Order_Date");
                            int totalOrders = rs.getInt("Total_Orders");

                            System.out.println("Date: " + orderDate + ", Total Orders: " + totalOrders);
                        }

                        break;

                    case 13:
                        sql = "SELECT r.Name AS Restaurant, m.Dish_name " +
                                "FROM Restaurant r " +
                                "JOIN Menu m ON r.Restaurant_id = m.Restaurant_id " +
                                "WHERE m.Dish_name = 'Gulab Jamun'";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String restaurantName = rs.getString("Restaurant");
                            String dishName = rs.getString("Dish_name");

                            System.out.println("Restaurant: " + restaurantName + ", Dish: " + dishName);
                        }

                        break;

                    case 14:
                        sql = "SELECT o.Payment_Status AS Payment_Method, " +
                                "(COUNT(o.Order_id) * 100.0 / (SELECT COUNT(*) FROM Orders)) AS Percentage " +
                                "FROM Orders o " +
                                "GROUP BY o.Payment_Status";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String paymentMethod = rs.getString("Payment_Method");
                            double percentage = rs.getDouble("Percentage");

                            System.out.println("Payment Method: " + paymentMethod + ", Percentage: "
                                    + String.format("%.2f", percentage) + "%");
                        }

                        break;

                    case 15:
                        sql = "SELECT o.Payment_Status AS Payment_Method, " +
                                "(COUNT(o.Order_id) * 100.0 / (SELECT COUNT(*) FROM Orders)) AS Percentage " +
                                "FROM Orders o " +
                                "GROUP BY o.Payment_Status";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String paymentMethod = rs.getString("Payment_Method");
                            double percentage = rs.getDouble("Percentage");

                            System.out.println("Payment Method: " + paymentMethod + ", Percentage: "
                                    + String.format("%.2f", percentage) + "%");
                        }

                        break;

                    case 16:
                        sql = "SELECT ca.DeliveryAddress, COUNT(o.Order_id) AS Total_Orders " +
                                "FROM Orders o " +
                                "JOIN CustomerAddresses ca ON o.Customer_id = ca.CustomerID " +
                                "GROUP BY ca.DeliveryAddress " +
                                "ORDER BY Total_Orders DESC";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String deliveryAddress = rs.getString("DeliveryAddress");
                            int totalOrders = rs.getInt("Total_Orders");

                            System.out
                                    .println("Delivery Address: " + deliveryAddress + ", Total Orders: " + totalOrders);
                        }

                        break;

                    case 17:
                        sql = "SELECT c.Name AS Customer " +
                                "FROM Customer c " +
                                "LEFT JOIN Coupons cu ON c.Customer_id = cu.Customer_id AND cu.ExpirationDate >= CURRENT_DATE "
                                +
                                "WHERE cu.Coupon_id IS NULL";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String customerName = rs.getString("Customer");
                            System.out.println("Customer without active coupons: " + customerName);
                        }

                        break;

                    case 18:
                        sql = "SELECT r.Name AS Restaurant, COUNT(m.Dish_name) AS Number_of_Dishes " +
                                "FROM Restaurant r " +
                                "JOIN Menu m ON r.Restaurant_id = m.Restaurant_id " +
                                "GROUP BY r.Name " +
                                "ORDER BY Number_of_Dishes DESC " +
                                "LIMIT 5";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String restaurantName = rs.getString("Restaurant");
                            int numberOfDishes = rs.getInt("Number_of_Dishes");

                            System.out
                                    .println("Restaurant: " + restaurantName + ", Number of Dishes: " + numberOfDishes);
                        }

                        break;

                    case 19:
                        sql = "SELECT r.Name AS Restaurant, COUNT(m.Dish_name) AS Number_of_Dishes " +
                                "FROM Restaurant r " +
                                "JOIN Menu m ON r.Restaurant_id = m.Restaurant_id " +
                                "GROUP BY r.Name " +
                                "ORDER BY Number_of_Dishes DESC " +
                                "LIMIT 5";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String restaurantName = rs.getString("Restaurant");
                            int numberOfDishes = rs.getInt("Number_of_Dishes");

                            System.out
                                    .println("Restaurant: " + restaurantName + ", Number of Dishes: " + numberOfDishes);
                        }

                        break;

                    case 20:
                        sql = "SELECT r.Name AS Restaurant, r.Operating_hours " +
                                "FROM Restaurant r " +
                                "ORDER BY r.Operating_hours DESC " +
                                "LIMIT 5";

                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            String restaurantName = rs.getString("Restaurant");
                            String operatingHours = rs.getString("Operating_hours");

                            System.out
                                    .println("Restaurant: " + restaurantName + ", Operating Hours: " + operatingHours);
                        }

                        break;
                }
                if (flag == 1)
                    break;
            }
            s.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}

/*
 * 
 * cd "D:\Java Programs\jdk-21.0.1"
 * javac --release 8 -cp ".;D:\Java Programs\jdbc\postgresql-42.7.4.jar"
 * api.java
 * 
 * java -cp ".;D:\Java Programs\jdbc\postgresql-42.7.4.jar" api
 * 
 */