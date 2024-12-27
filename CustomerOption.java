import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerOption {
    public static void Option() {
        System.out.println("--- Customer Option ---");
        System.out.println("+---------------------+--------------+----------------+");
        System.out.println("| 1 Types Movie       | 2 View Movie | 3 All Movie    |");
        System.out.println("| 4 Search Movie      | 5 Buy Movie  | 6 Return Movie |");
        System.out.println("| 7 User Rent History | 8 Message    | 9 Logout       |");
        System.out.println("+---------------------+--------------+----------------+");
        Scanner sc = new Scanner(System.in);
        int a;
        while (true) {
            System.out.print("--- Enter the choice number : ");
            try {
                a = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("--- Please enter an integer value ---");
                sc.next();
                Option();
            }
        }
        switch (a) {
            case 1:
                Customer.Type_Movie();
                break;
            case 2:
                Customer.View_Movie();
                break;
            case 3:
                Customer.All_Movies();
                break;
            case 4:
                Customer.Search_Movie();
                break;
            case 5:
                Customer.Buy_Movie();
                break;
            case 6:
                Customer.Return_Movie();
                break;
            case 7:
                Customer.User_Rent_History();
                break;
            case 8:
                Customer.Message();
            case 9:
                Information.infor();
                break;
            default:
                System.out.println("--- Please enter valid number ---");
                Option();
                break;
        }
    }
}