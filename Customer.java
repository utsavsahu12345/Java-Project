import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

public class Customer {
    private static final String url = "jdbc:mysql://localhost:3306/movierent";
    private static final String username = "root";
    private static final String password = "utsav9343";
    static String Username;
    static int n = 3;
    static int m = 0;
    static Scanner sc = new Scanner(System.in);
    static Scanner in = new Scanner(System.in);

    public static void Customersign() {
        String firstname;
        while (true) {
            System.out.print("--- Enter the First Name : ");
            firstname = sc.nextLine();
            if (firstname.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    Information.Customer();
                }
            }
        }
        String lastname;
        while (true) {
            System.out.print("--- Enter the Last Name : ");
            lastname = sc.nextLine();
            if (lastname.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    Information.Customer();
                }
            }
        }
        String contact;
        while (true) {
            System.out.print("--- Enter the Contact Number : ");
            contact = sc.nextLine();
            if (contact.matches("[6-9]\\d{9}")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Invalid contact number. Please enter a 10-digit number ---");
                m++;
                if (n == m) {
                    m = 0;
                    Information.Customer();
                }
            }
        }
        String gender;
        while (true) {
            System.out.print("--- Enter the Gender M/F : ");
            gender = sc.nextLine();
            if (gender.matches("[mMfF]")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter M or F ---");
                m++;
                if (n == m) {
                    m = 0;
                    Information.Customer();
                }
            }
        }
        String Password;
        while (true) {
            System.out.print("--- Enter the Password : ");
            Password = sc.nextLine();
            if (isStrongPassword(Password)) {
                m = 0;
                break;
            } else {
                System.out.println("--- Password 10 characters uppercase, lowercase, digit, special character ---");
                m++;
                if (n == m) {
                    m = 0;
                    Information.Customer();
                }
            }
        }
        int a = 1;
        while (a > 0) {
            Username = firstname + a + "@";
            try (Connection connection = DriverManager.getConnection(url, username, password);
                    PreparedStatement signUpStatement = connection
                            .prepareStatement("INSERT INTO customerlogin (username,password) VALUES (?,?)")) {
                signUpStatement.setString(1, Username);
                signUpStatement.setString(2, Password);
                signUpStatement.executeUpdate();
                System.out.println("--- Username : " + Username + " ---");
                signup(firstname, lastname, contact, gender);
                break;
            } catch (SQLException e) {
                a++;
            }
        }
    }

    public static boolean isStrongPassword(String password) {
        return password.length() >= 10 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()-_+=].*");
    }

    public static void signup(String firstname, String lastname, String contact, String gender) {
        String signUpQuery = "INSERT INTO customersign (firstname, lastname, contact, gender) VALUES (?, ?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement signUpStatement = connection.prepareStatement(signUpQuery);
            signUpStatement.setString(1, firstname);
            signUpStatement.setString(2, lastname);
            signUpStatement.setString(3, contact);
            signUpStatement.setString(4, gender);
            int signUpRowsInserted = signUpStatement.executeUpdate();
            if (signUpRowsInserted > 0) {
                System.out.println("--- Sign-up Completed Successfully ---");
                CustomerOption.Option();
            }
        } catch (Exception e) {
            System.out.println(e);
            Customersign();
        }
    }

    public static void CustomerLogin() {
        while (true) {
            System.out.print("--- Enter your username : ");
            String usernameInput = sc.nextLine();
            System.out.print("--- Enter your password : ");
            String passwordInput = sc.nextLine();
            String query = "SELECT * FROM customerlogin WHERE Username = ? AND Password = ?";

            try (Connection connection = DriverManager.getConnection(url, username, password);
                    PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setString(1, usernameInput);
                ps.setString(2, passwordInput);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String u = rs.getString("Username");
                    String p = rs.getString("Password");
                    if (usernameInput.equals(u) && passwordInput.equals(p)) {
                        Username = usernameInput;
                        System.out.println("--- Login Successful ---");
                        m = 0;
                        CustomerOption.Option();
                        break;
                    } else {
                        System.out.println("--- You entered the wrong ID and password ---");
                        System.out.println("---      PLEASE TRY AGAIN     ---");
                        m++;
                        if (m >= n) {
                            m = 0;
                            Information.infor();
                        }
                    }
                } else {
                    System.out.println("--- You entered the wrong ID and password ---");
                    System.out.println("---      PLEASE TRY AGAIN     ---");
                    m++;
                    if (m >= n) {
                        m = 0;
                        Information.infor();
                    }
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    public static void Type_Movie() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from type");
            while (resultSet.next()) {
                String view = resultSet.getString("view");
                System.out.println("* " + view);
            }
            CustomerOption.Option();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void View_Movie() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from type");
            while (resultSet.next()) {
                String view = resultSet.getString("view");
                System.out.println("* " + view);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        String A;
        while (true) {
            System.out.print("--- Enter movie type / back 1 : ");
            A = sc.next();
            if (A.equals("1")) {
                CustomerOption.Option();
            }
            if (A.matches("[a-zA-Z\\s]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter a valid string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    CustomerOption.Option();
                    return;
                }
            }
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + A);
                ResultSet resultSet = ps.executeQuery()) {

            if (resultSet.next()) {
                System.out.println("+---------------+----------+-----------+");
                System.out.println("| Moviename     | Price    | Available |");
                System.out.println("+---------------+----------+-----------+");

                do {
                    String movie = resultSet.getString("movie");
                    int price = resultSet.getInt("price");
                    int available = resultSet.getInt("available");
                    System.out.printf("| %-13s | %-8d | %-9d |\n", movie, price, available);
                } while (resultSet.next());

                System.out.println("+---------------+----------+-----------+");
            } else {
                System.out.println("--- No movies found for this type ---");
            }
            CustomerOption.Option();

        } catch (SQLException e) {
            System.out.println("--- This type of movie is not available ---");
            CustomerOption.Option();
        }
    }
    public static void All_Movies() {
        String query = "SELECT * FROM type"; 
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
  
            while (rs.next()) {
                String mtype = rs.getString("view"); 
                String Q = "SELECT movie, price, available FROM " + mtype +"";
                try (Connection con = DriverManager.getConnection(url, username, password);
                     PreparedStatement ps1 = con.prepareStatement(Q)) { 
                    ResultSet rs1 = ps1.executeQuery();
                    System.out.println(  "            --- "+mtype+" ---           ");
                    System.out.println("+---------------+----------+-----------+");
                    System.out.println("| Moviename     | Price    | Available |");
                    System.out.println("+---------------+----------+-----------+");
                    while (rs1.next()) {
                        String movie = rs1.getString("movie");
                        String price = rs1.getString("price");
                        String available = rs1.getString("available");
                       System.out.printf("| %-13s | %-8s | %-9s |\n", movie, price, available);   
                    }
                    System.out.println("+---------------+----------+-----------+");
                    System.out.println();
                   
                } catch (SQLException e) {
                    CustomerOption.Option();
                }
            }
            CustomerOption.Option();
        } catch (SQLException e) {
            CustomerOption.Option();
        }
    }
    public static void Search_Movie() {

        String moviename;

        while (true) {
            System.out.print("--- Enter movie name / back 1 : ");
            moviename = sc.next();
            if (moviename.equals("1")) {
                CustomerOption.Option();
            }
            if (moviename.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter a valid string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    CustomerOption.Option();
                    return;
                }
            }
        }

        String query = "SELECT * FROM type";

        try (Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            boolean movieFound = false;

            while (rs.next()) {
                String mtype = rs.getString("view");
                String Q = "SELECT movie, price, available FROM " + mtype + " WHERE movie = ?";
                try (Connection con = DriverManager.getConnection(url, username, password);
                        PreparedStatement ps1 = con.prepareStatement(Q)) {
                    ps1.setString(1, moviename);
                    ResultSet rs1 = ps1.executeQuery();
                    if (rs1.next()) {
                        String movie = rs1.getString("movie");
                        String price = rs1.getString("price");
                        String available = rs1.getString("available");
                        System.out.println("+---------------+----------+-----------+");
                        System.out.println("| Moviename     | Price    | Available |");
                        System.out.println("+---------------+----------+-----------+");
                        System.out.printf("| %-13s | %-8s | %-9s |\n", movie, price, available);
                        System.out.println("+---------------+----------+-----------+");
                        movieFound = true;
                        CustomerOption.Option();
                    }
                    if (!movieFound) {

                    }
                } catch (SQLException e) {
                }
            }
            if (!movieFound) {
                System.out.println("--- Movie Not Found ---");
                CustomerOption.Option();
            }
        } catch (SQLException e) {
        }
    }
public static void Buy_Movie() {
        LocalDate today = LocalDate.now();
        String movietype;
        while (true) {
            System.out.print("--- Enter movie type / back 1 : ");
            movietype = sc.next();
            if (movietype.equals("1")) {
                CustomerOption.Option();
            }
            if (movietype.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    CustomerOption.Option();
                }
            }
        }
        String movieName;
        while (true) {
            System.out.print("--- Enter movie name / back 1 : ");
            movieName = sc.next();
            if (movieName.equals("1")) {
                CustomerOption.Option();
            }
            if (movieName.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    CustomerOption.Option();
                }
            }
        }
        String query = "select returnmovie, moviename from customerbill where username = ? and moviename = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, Username);
            preparedStatement.setString(2, movieName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String returnmovie = resultSet.getString("returnmovie");
                if (returnmovie == null) {
                    System.out.println("--- Already Bought Movie ---");
                    CustomerOption.Option();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        String sqlQuery = "SELECT available, price FROM " + movietype + " WHERE movie = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, movieName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int availability = resultSet.getInt("available");
                if (availability > 0) {
                    System.out.println("--- Movie " + movieName + " is available ---");
    
                    while (n > m) {
                        System.out.print("--- Enter your return date (DD-MM-YYYY) / back 1 : ");
                        m++;
                        String endDateInput = sc.next();
                        if (endDateInput.equals("1")) {
                            CustomerOption.Option();
                        }
                        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
                        try {
                            LocalDate date = LocalDate.parse(endDateInput, inputFormatter);
                            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            String endDate = date.format(outputFormatter);
    
                            try {
                                LocalDate rDate = LocalDate.parse(endDate);
                                if (!rDate.isBefore(today)) {
                                    m = 0;
                                    int price = resultSet.getInt("price");
                                    long daysBetween = ChronoUnit.DAYS.between(today, rDate);
                                    long totalAmount = daysBetween * price;
                                    if (daysBetween == 0) {
                                        totalAmount = price;
                                        System.out.println("--- Your Buying Days: " + daysBetween);
                                        System.out.println("--- Movie Price: " + price);
                                        System.out.println("--- Total Amount: " + totalAmount);
                                    } else {
                                        System.out.println("--- Your Buying Days: " + daysBetween);
                                        System.out.println("--- Movie Price: " + price);
                                        System.out.println("--- Total Amount: " + totalAmount);
                                    }
                                    System.out.print("--- Buy Movie (yes or no) : ");
                                    String buyConfirmation = sc.next();
                                    if ("yes".equalsIgnoreCase(buyConfirmation)) {
                                    } else {
                                        System.out.println("--- Transaction cancelled ---");
                                        CustomerOption.Option();
                                    }
                                    System.out.println("--- Your Bill ---");
                                    String insertQuery = "INSERT INTO customerbill (username, movietype, moviename, amount, day, buydate, returndate, payment) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                                        insertStatement.setString(1, Username);
                                        insertStatement.setString(2, movietype);
                                        insertStatement.setString(3, movieName);
                                        insertStatement.setInt(4, price);
                                        insertStatement.setLong(5, daysBetween);
                                        insertStatement.setDate(6, Date.valueOf(today));
                                        insertStatement.setDate(7, Date.valueOf(endDate));
                                        insertStatement.setLong(8, totalAmount);
                                        insertStatement.executeUpdate();
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                        String todays = today.format(formatter);
                                        System.out.println(
                                                "+------------+------------+------------+------------+------------+------+------------+--------------+");
                                        System.out.println(
                                                "| Username   | Movietype  | Moviename  | Buydate    | Returndate | Days | Movieprice | Totalpayment |");
                                        System.out.println(
                                                "+------------+------------+------------+------------+------------+------+------------+--------------+");
                                        System.out.printf(
                                                "| %-10s | %-10s | %-10s | %-10s | %-10s | %-4d | %-10d | %-12d |\n",
                                                Username, movietype, movieName, todays, endDateInput, daysBetween, price,
                                                totalAmount);
                                        System.out.println(
                                                "+------------+------------+------------+------------+------------+------+------------+--------------+");
                                    }
                                    String updateQuery = "UPDATE " + movietype
                                            + " SET available = available-1 WHERE movie = ?";
                                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                        updateStatement.setString(1, movieName);
                                        updateStatement.executeUpdate();
                                        break;
                                    }
                                } else {
                                    System.out.println("--- Invalid return date. It must be after or on today's date ---");
                                }
                            } catch (Exception e) {
                                System.out.println("--- Invalid return date format. Please enter in YYYY-MM-DD format ---");
                            }
                        } catch (Exception e) {
                            System.out.println(" Please Enter Date in DD-MM-YYYY format");
                        }
                    }
                } else {
                    System.out.println("--- Movie " + movieName + " is not available ---");
                    CustomerOption.Option();
                }
            } else {
                System.out.println("--- Movie not found ---");
                CustomerOption.Option();
            }
        } catch (SQLException e) {
            System.out.println("--- Error Movie Type ---");
            CustomerOption.Option();
        }
        m = 0;
        CustomerOption.Option();
    }


    public static void Return_Movie() {
        String moviename;
        while (true) {
            System.out.print("--- Enter your buying movie name / back 1 : ");
            moviename = sc.next();
            if (moviename.equals("1")) {
                CustomerOption.Option();
            }
            if (moviename.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    CustomerOption.Option();
                }
            }
        }
        LocalDate date = LocalDate.now();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "select returndate,amount,movietype, returnmovie from customerbill where moviename = ? and username = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, moviename);
            ps.setString(2, Username);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Date sqlReturnDate = resultSet.getDate("returndate");
                int amount = resultSet.getInt("amount");
                String movietype = resultSet.getString("movietype");
                String returnmovie = resultSet.getString("returnmovie");
                LocalDate returndate = sqlReturnDate.toLocalDate();
                long daysLate = ChronoUnit.DAYS.between(returndate, date);
                int extracharge = (int) daysLate;
                if (returnmovie.equals("yes")) {
                    System.out.println("--- Already Returned Movie ---");
                    CustomerOption.Option();
                }
                String insertQuery = "update customerbill set extracharge = ?, returnmovie = ? where username = ?";
                if (extracharge <= 0) {
                    System.out.println("Extracharge : 0");
                    System.out.print("Return Movie yes or no : ");
                    String a = sc.next();
                    if ("yes".equalsIgnoreCase(a)) {
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setInt(1, 0);
                        preparedStatement.setString(2, a);
                        preparedStatement.setString(3, Username);
                        int rowsInserted = preparedStatement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("--- Return Successfully ---");
                            String updateQuery = "UPDATE " + movietype + " SET available = available+1 WHERE movie = ?";
                            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                updateStatement.setString(1, moviename);
                                updateStatement.executeUpdate();
                            }
                            CustomerOption.Option();
                        }
                    } else {
                        CustomerOption.Option();
                    }
                } else {
                    int extra = extracharge * amount;
                    System.out.println("--- Extracharge : " + extra + " ---");
                    System.out.print("--- Return Movie yes or no : ");
                    String a = sc.next();
                    if ("yes".equalsIgnoreCase(a)) {
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setInt(1, extra);
                        preparedStatement.setString(2, a);
                        preparedStatement.setString(3, Username);
                        int rowsInserted = preparedStatement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("--- Return Successfully ---");
                            String updateQuery = "UPDATE " + movietype
                                    + " SET available = available + 1 WHERE movie = ?";
                            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                updateStatement.setString(1, moviename);
                                updateStatement.executeUpdate();
                            }
                            CustomerOption.Option();
                        }
                    } else {
                        CustomerOption.Option();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("--- Movie not found ---");
        CustomerOption.Option();
    }

    public static void User_Rent_History() {
        String query = "SELECT * FROM customerbill where username = ?";
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, Username);
            ResultSet rs = ps.executeQuery();
            boolean hash = false;
            while (rs.next()) {
                hash = true;
                if (n < 4) {
                    System.out.println(
                            "+------------+------------+------------+------------+------------+--------+-----+---------+-------------+-------------+");
                    System.out.println(
                            "| Username   | Movietype  | Moviename  | Buydate    | Returndate | Amount | Day | Payment | Extracharge | Returnmovie |");
                    System.out.println(
                            "+------------+------------+------------+------------+------------+--------+-----+---------+-------------+-------------+");
                    n++;
                }
                String username = rs.getString(1);
                String movietype = rs.getString(2);
                String moviename = rs.getString(3);
                String buydate = rs.getString(4);
                String returndate = rs.getString(5);
                int amount = rs.getInt(6);
                int day = rs.getInt(7);
                int payment = rs.getInt(8);
                int extracharge = rs.getInt(9);
                String returnmovie = rs.getString(10);
                System.out.printf(
                        "| %-10s | %-10s | %-10s | %-10s | %-10s | %-6d | %-3d | %-7d | %-11d | %-11s |\n", username,
                        movietype, moviename, buydate, returndate, amount, day, payment, extracharge, returnmovie);
                System.out.println(
                        "+------------+------------+------------+------------+------------+--------+-----+---------+-------------+-------------+");
            }
            if (!hash) {
                System.out.println("--- No History ---");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        n = 3;
        CustomerOption.Option();
    }

    public static void Message() {
        LocalDate currentDate = LocalDate.now();
        boolean foundOverdue = false;
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT username, moviename, returndate, amount FROM customerbill WHERE username = ? AND returnmovie IS NULL";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, Username);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String usernameFromDB = resultSet.getString("username");
                String returndateStr = resultSet.getString("returndate");
                String moviename = resultSet.getString("moviename");
                int amount = resultSet.getInt("amount");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate returndate = LocalDate.parse(returndateStr, formatter);

                if (currentDate.isAfter(returndate)) {
                    foundOverdue = true;
                    long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(returndate, currentDate);
                    Message_Print(usernameFromDB, moviename, amount, returndate, daysOverdue);
                }
            }

            if (!foundOverdue) {
                System.out.println("--- No Message ---");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            CustomerOption.Option();
        }
    }

    public static void Message_Print(String Username, String moviename, int amount, LocalDate returndate,
            long daysOverdue) {
        int extra = (int) (daysOverdue * amount);
        System.out.println("--- User Name    : " + Username);
        System.out.println("--- Movie Name   : " + moviename);
        System.out.println("--- Return Date  : " + returndate);
        System.out.println("--- Extra Due    : " + extra);
    }
}