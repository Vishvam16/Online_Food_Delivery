
CREATE SCHEMA ONLINE_DELIVERY_SYSTEM;

create table Restaurant(
    Restaurant_id INT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Address VARCHAR(500) NOT NULL,
    Contact_information VARCHAR(15) UNIQUE NOT NULL,
    Operating_hours VARCHAR(50) NOT NULL,
    Cuisine VARCHAR(50),
    Ratings_and_reviews VARCHAR(500)
	);

create table Customer(
	Customer_id INT PRIMARY KEY,
	Name VARCHAR(100) NOT NULL,
	Phone_number VARCHAR(15) NOT NULL,
	Address VARCHAR(500) NOT NULL
	
);

create table CustomerEmails (
    EmailAddress VARCHAR(100) PRIMARY KEY,
    CustomerID INT NOT NULL,
    FOREIGN KEY (CustomerID) REFERENCES Customer(Customer_id) ON DELETE CASCADE
);


create table Menus(
	Menu_id SERIAL PRIMARY KEY, 
    Dish_name VARCHAR(100) ,     
    Description VARCHAR(500),               
    Price INT CHECK (price>=0) NOT NULL,                     
    Restaurant_id INT,                      
    FOREIGN KEY (Restaurant_id) REFERENCES Restaurant(Restaurant_id) 
	ON DELETE CASCADE
	ON UPDATE CASCADE
	
);

CREATE TABLE Delivery_Personnel (
    Delivery_Person_id INT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Phone_number VARCHAR(15) UNIQUE NOT NULL
);

create table Orders(
    Order_id BIGINT PRIMARY KEY,
    Order_date_and_time TIMESTAMP NOT NULL,
    Item_Ordered VARCHAR(100) NOT NULL,
    Total_Amount INT CHECK (total_amount >= 0),
	Payment_Status varchar(30) check (Payment_status in('Credit Card','Debit Card', 'UPI','Net banking','COD')),
	Restaurant_id INT,
	Customer_id INT,
    Delivery_Person_id INT,
    FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id)
    ON DELETE RESTRICT
	ON UPDATE CASCADE,
 	FOREIGN KEY (Restaurant_id) REFERENCES Restaurant(Restaurant_id)
	ON DELETE RESTRICT
	ON UPDATE CASCADE,
     FOREIGN KEY (Delivery_Person_id) REFERENCES Delivery_Personnel(Delivery_Person_id)
    ON DELETE RESTRICT
	ON UPDATE CASCADE
);

create table CustomerAddresses (
	AddressID SERIAL PRIMARY KEY,
    DeliveryAddress VARCHAR(255) ,
    Customer_id INT NOT NULL,
    FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id) ON UPDATE CASCADE ON DELETE CASCADE
);




create table Payments(
    Payment_id INT PRIMARY KEY,
    Payment_method VARCHAR(100) check (Payment_method in('Credit Card','Debit Card','UPI','Net banking','COD')),
    Payment_date_and_time TIMESTAMP NOT NULL,
	Order_id INT,
    FOREIGN KEY (Order_id) REFERENCES Orders(Order_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

create table Ratings_and_Review(
    Review_id INT PRIMARY KEY,
    Rating FLOAT CHECK (Rating BETWEEN 0 AND 5),
    Review_text TEXT,
    Review_date_and_time TIMESTAMP NOT NULL,
    Restaurant_id INT,
	Customer_id INT,
    FOREIGN KEY (Restaurant_id) REFERENCES Restaurant(restaurant_id)
	ON DELETE SET NULL
	ON UPDATE CASCADE,
	FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
	);

create table Coupons (
    Coupon_id INT PRIMARY KEY,
    CouponCode VARCHAR(20) NOT NULL UNIQUE,
    DiscountAmount DECIMAL(4,2) CHECK (DiscountAmount >= 0),
    ExpirationDate DATE NOT NULL,
	MinimumOrderValue DECIMAL(10,2) CHECK (MinimumOrderValue >= 0),
    Customer_id INT NOT NULL,
    FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id) 
	ON DELETE CASCADE 
	ON UPDATE CASCADE
);

create table Feedback_for_Delivery_Personnel (
    Feedback_id INT PRIMARY KEY,
    Delivery_Person_Rating FLOAT CHECK  ( Delivery_Person_Rating BETWEEN 0 AND 5),
    Comments TEXT,
    Feedback_Date_And_Time TIMESTAMP NOT NULL,
	Delivery_Person_id INT,
    Customer_id INT,
    FOREIGN KEY (Delivery_Person_id) REFERENCES Delivery_Personnel(Delivery_Person_id) 
	ON DELETE SET NULL 
	ON UPDATE CASCADE,
    FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id) 
	ON DELETE CASCADE 
	ON UPDATE CASCADE
);
