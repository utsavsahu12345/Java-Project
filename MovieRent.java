import java.util.Scanner;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MovieRent {
    private static final String url = "jdbc:mysql://localhost:3306/movierent";
    private static final String username = "root";
    private static final String password = "utsav9343";
    static Scanner sc = new Scanner(System.in);
    static Scanner in = new Scanner(System.in);
    static int n = 3;
    static int m = 0;

    public static void main(String[] aregs) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Information.infor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void ShopkeeperLogin() {
            while (true) {
                System.out.print("--- Enter your username : ");
                String usernameInput = sc.nextLine();
                System.out.print("--- Enter your password : ");
                String passwordInput = sc.nextLine();
                String query = "select * from shopkeeper where id = ? And password = ?";
    
                try (Connection connection = DriverManager.getConnection(url, username, password);
                        PreparedStatement ps = connection.prepareStatement(query)) {
    
                    ps.setString(1, usernameInput);
                    ps.setString(2, passwordInput);
                    ResultSet rs = ps.executeQuery();
    
                    if (rs.next()) {
                        String u = rs.getString("id");
                        String p = rs.getString("Password");
                        if (usernameInput.equals(u) && passwordInput.equals(p)) {
                            System.out.println("--- Login Successful ---");
                            m = 0;
                            Shopkeeper.Option();
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
            Shopkeeper.Option();
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
                Shopkeeper.Option();
            }
            if (A.matches("[a-zA-Z\\s]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter a valid string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
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
            Shopkeeper.Option();
    
        } catch (SQLException e) {
            System.out.println("--- This type of movie is not available ---");
            Shopkeeper.Option();
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
                    Shopkeeper.Option();
                }
            }
            Shopkeeper.Option();
        } catch (SQLException e) {
            Shopkeeper.Option();
        }
    }
  
    public static void Add_type() {
        String movietype;
        while (true) {
            System.out.print("--- Enter movie type to add / back 1 : ");
            movietype = sc.next();
            if (movietype.equals("1")) {
                Shopkeeper.Option();
            }
            if (movietype.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
                }
            }
        }
        System.out.print("--- Confirm add movie type (yes or no) : ");
        String buyConfirmation = sc.next();
        if ("yes".equalsIgnoreCase(buyConfirmation)) {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                String addview = "INSERT INTO type (view) VALUES (?)";
                PreparedStatement ps = connection.prepareStatement(addview);
                ps.setString(1, movietype);
                int rowsInserted = ps.executeUpdate();
                String addtable = "CREATE TABLE " + movietype + " ( movie VARCHAR(50), price INT, available INT )";
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(addtable);
                if (rowsInserted > 0) {
                    System.out.println("--- Successfully Added Movie Type ---");
                } else {
                    System.out.println("--- Failed to Add Movie Type ---");
                    Shopkeeper.Option();
                }
                Scanner i = new Scanner(System.in);
                System.out.print("--- Add movie (yes or no) : ");
                String Confirmation = i.nextLine();
                if ("yes".equalsIgnoreCase(Confirmation)) {
                    Add_Movie_Type(movietype);
                } else {
                    Shopkeeper.Option();
                }
            } catch (SQLException e) {
                System.out.println("--- Already Exist Movie Type ---");
            }
        } else {
            System.out.println("--- Movie Type Add cancelled ---");
            Shopkeeper.Option();
        }
    }
    public static void Add_Movie_Type(String movietype) {
        String movieName;
        while (true) {
            System.out.print("--- Enter movie name / back 1 : ");
            movieName = sc.next();
            if (movieName.equals("1")) {
                Shopkeeper.Option();
            }
            if (movieName.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
                }
            }
        }
        int price;
        while (true) {
            System.out.print("--- Enter the price / back 1 : ");
            String Price = in.nextLine();
            if (Price.equals("1")) {
                Shopkeeper.Option();
            }
            try {
                price = Integer.parseInt(Price);
                if (price >= 0) {
                    m = 0;
                    break;
                } else {
                    System.out.println("--- Invalid new price. The price must be non-negative ---");
                    m++;
                    if (n == m) {
                        m = 0;
                        Shopkeeper.Option();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("--- Invalid input. Please enter a valid integer ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
                }
            }
        }
        int available;
        while (true) {
            System.out.print("--- Enter the available / back 1 : ");
            String Available = in.nextLine();
            if (Available.equals("1")) {
                Shopkeeper.Option();
            }
            try {
                available = Integer.parseInt(Available);
                if (available > 0) {
                    m = 0;
                    break;
                } else {
                    System.out.println("--- Invalid new price. The price must be non-negative ---");
                    m++;
                    if (n == m) {
                        m = 0;
                        Shopkeeper.Option();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("--- Invalid input. Please enter a valid integer ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
                }
            }
        }
        Scanner t = new Scanner(System.in);
        System.out.print("--- Add Movie (yes or no) : ");
        String add = t.next();
        if (add.equalsIgnoreCase("yes") || add.equalsIgnoreCase("no")) {
        } else {
            Shopkeeper.Option();
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO " + movietype + "(movie, price, available) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, movieName);
            preparedStatement.setInt(2, price);
            preparedStatement.setInt(3, available);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("--- Movie Add Successfully ---");
                Shopkeeper.Option();
            } else {
                System.out.println("--- No movie add ---");
                Shopkeeper.Option();
            }
        } catch (Exception e) {
            System.out.println("--- Please Enter valid Details Movietype or Moviename ---");
            Shopkeeper.Option();
        }
    }

    public static void Add_Movie() {
        String movietype;
        while (true) {
            System.out.print("--- Enter movie type / back 1 : ");
            movietype = sc.next();
            if (movietype.equals("1")) {
                Shopkeeper.Option();
            }
            if (movietype.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter character values ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
                }
            }
        }
        String movieName;
        while (true) {
            System.out.print("--- Enter movie name / back 1 : ");
            movieName = sc.next();
            if (movieName.equals("1")) {
                Shopkeeper.Option();
            }
            if (movieName.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
                }
            }
        }
        int price;
        while (true) {
            System.out.print("--- Enter the price / back 1 : ");
            String Price = in.nextLine();
            if (Price.equals("1")) {
                Shopkeeper.Option();
            }
            try {
                price = Integer.parseInt(Price);
                if (price >= 0) {
                    m = 0;
                    break;
                } else {
                    System.out.println("--- Invalid new price. The price must be non-negative ---");
                    m++;
                    if (n == m) {
                        m = 0;
                        Shopkeeper.Option();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("--- Invalid input. Please enter a valid integer ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
                }
            }
        }
        int available;
        while (true) {
            System.out.print("--- Enter the available / back 1 : ");
            String Available = in.nextLine();
            if (Available.equals("1")) {
                Shopkeeper.Option();
            }
            try {
                available = Integer.parseInt(Available);
                if (available > 0) {
                    m = 0;
                    break;
                } else {
                    System.out.println("--- Invalid new price. The price must be non-negative ---");
                    m++;
                    if (n == m) {
                        m = 0;
                        Shopkeeper.Option();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("--- Invalid input. Please enter a valid integer ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
                }
            }
        }
        Scanner q = new Scanner(System.in);
        System.out.print("--- Add Movie (yes or no) : ");
        String add = q.nextLine();
        if (add.equalsIgnoreCase("yes")) {
        } else {
            System.out.println("--- Movie Not Add ---");
            Shopkeeper.Option();
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO " + movietype + "(movie, price, available) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, movieName);
            preparedStatement.setInt(2, price);
            preparedStatement.setInt(3, available);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("--- Movie Add Successfully ---");
                Shopkeeper.Option();
            } else {
                System.out.println("--- No movie add ---");
                Shopkeeper.Option();
            }
        } catch (Exception e) {
            System.out.println("--- Please Enter valid Details Movietype or Moviename ---");
            Shopkeeper.Option();
        }
    }

    public static void Delete_Movie() {
        String movietype;
        while (true) {
            System.out.print("--- Enter movie type / back 1 : ");
            movietype = sc.next();
            if (movietype.equals("1")) {
                Shopkeeper.Option();
            }
            if (movietype.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
                }
            }
        }
        String sql = "DELETE FROM " + movietype + " WHERE movie = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            String movieName;
            while (true) {
                System.out.print("--- Enter movie name / back 1 : ");
                movieName = sc.next();
                if (movieName.equals("1")) {
                    Shopkeeper.Option();
                }
                if (movieName.matches("[a-zA-Z]+")) {
                    m = 0;
                    break;
                } else {
                    System.out.println("--- Please enter string value ---");
                    m++;
                    if (n == m) {
                        m = 0;
                        Shopkeeper.Option();
                    }
                }
            }
            System.out.print("--- Delecte Movie (yes or no) : ");
            String buyConfirmation = sc.next();
            if ("yes".equalsIgnoreCase(buyConfirmation)) {
            } else {
                System.out.println("--- Not Delecte Movie ---");
                Shopkeeper.Option();
            }
            statement.setString(1, movieName);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("--- Deleted Successfully ---");
                Shopkeeper.Option();
            } else {
                System.out.println("--- No movie found ---");
                Delete_Movie();
            }
        } catch (SQLException e) {
            System.out.println("--- This type movie not available ---");
            Shopkeeper.Option();
        }
    }

    public static void Update_Price() {
        String movietype;
        while (true) {
            System.out.print("--- Enter movie type / back 1 : ");
            movietype = sc.next();
            if (movietype.equals("1")) {
                Shopkeeper.Option();
            }
            if (movietype.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
                }
            }
        }
        String movieName;
        while (true) {
            System.out.print("--- Enter movie name / back 1 : ");
            movieName = sc.next();
            if (movieName.equals("1")) {
                Shopkeeper.Option();
            }
            if (movieName.matches("[a-zA-Z]+")) {
                m = 0;
                break;
            } else {
                System.out.println("--- Please enter string value ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
                }
            }
        }
        int newprice;
        while (true) {
            System.out.print("--- Enter the new price / back 1 : ");
            String price = in.nextLine();
            if (price.equals("1")) {
                Shopkeeper.Option();
            }
            try {
                newprice = Integer.parseInt(price);
                if (newprice >= 0) {
                    m = 0;
                    break;
                } else {
                    System.out.println("--- Invalid new price. The price must be non-negative ---");
                    m++;
                    if (n == m) {
                        m = 0;
                        Shopkeeper.Option();
                    }
                    System.out.print("--- Update Movie Price (yes or no) : ");
                    String buyConfirmation = sc.next();
                    if ("yes".equalsIgnoreCase(buyConfirmation)) {
                    } else {
                        Shopkeeper.Option();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("--- Invalid input. Please enter a valid integer ---");
                m++;
                if (n == m) {
                    m = 0;
                    Shopkeeper.Option();
                }
            }
        }
        String query = "UPDATE " + movietype + " SET price = ? WHERE movie = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, newprice);
            ps.setString(2, movieName);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("--- Price Updated Successfully ---");
                Shopkeeper.Option();
            } else {
                System.out.println("--- No movie found ---");
                Shopkeeper.Option();
            }
        } catch (Exception e) {
            System.out.println("--- This type movie not available ---");
            Shopkeeper.Option();
        }
    }

    public static void Message() {
        LocalDate currentDate = LocalDate.now();
        boolean foundOverdue = false;
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT username, moviename, returndate, amount FROM customerbill WHERE returnmovie IS NULL";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                String Username = resultSet.getString("username");
                String returndateStr = resultSet.getString("returndate");
                String moviename = resultSet.getString("moviename");
                int amount = resultSet.getInt("amount");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate returndate = LocalDate.parse(returndateStr, formatter);

                if (currentDate.isAfter(returndate)) {
                    foundOverdue = true;
                    long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(returndate, currentDate);
                    Message_Print(Username, moviename, amount, returndate, daysOverdue);
                }
            }

            if (!foundOverdue) {
                System.out.println("--- No Message ---");
            }

        } catch (Exception e) {
        }

        Shopkeeper.Option();
    }

    public static void Message_Print(String Username, String moviename, int amount, LocalDate returndate,
            long daysOverdue) {
        int extra = (int) (daysOverdue * amount);
        System.out.println("--- Message ---");
        System.out.println("--- User Name    : " + Username);
        System.out.println("--- Movie Name   : " + moviename);
        System.out.println("--- Extra Dueo   : " + extra);
    }

    public static void View_Bill() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM customerbill";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                System.out.println(
                        "+------------+------------+------------+------------+------------+--------+-----+---------+-------------+-------------+");
                System.out.println(
                        "| Username   | Movietype  | Moviename  | Buydate    | Returndate | Amount | Day | Payment | Extracharge | Returnmovie |");
                System.out.println(
                        "+------------+------------+------------+------------+------------+--------+-----+---------+-------------+-------------+");
                while (resultSet.next()) {
                    String Username = resultSet.getString("username");
                    String movietype = resultSet.getString("movietype");
                    String moviename = resultSet.getString("moviename");
                    String buydate = resultSet.getString("buydate");
                    String returndate = resultSet.getString("returndate");
                    int amount = resultSet.getInt("amount");
                    int day = resultSet.getInt("day");
                    int payment = resultSet.getInt("payment");
                    int extracharge = resultSet.getInt("extracharge");
                    String returnmovie = resultSet.getString("returnmovie");
                    System.out.printf(
                            "| %-10s | %-10s | %-10s | %-10s | %-10s | %-6d | %-3d | %-7d | %-11d | %-11s |\n",
                            Username, movietype, moviename, buydate, returndate, amount, day, payment, extracharge,
                            returnmovie);
                }
                System.out.println(
                        "+------------+------------+------------+------------+------------+--------+-----+---------+-------------+-------------+");
            } else {
                System.out.println("--- No Transation ---");
                Shopkeeper.Option();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        Shopkeeper.Option();
    }
}
