import java.util.InputMismatchException;
import java.util.Scanner;

public class Shopkeeper {
    static int n = 2;
    static int m = 0;
    public static void Option() {
        System.out.println("--- Shopkeeper Option ---");
        System.out.println("+----------------+----------------+--------------+------------+-------------+");
        System.out.println("| 1 Types Movie  | 2 View Movie   | 3 All Movies | 4 Add Type | 5 Add Movie |");
        System.out.println("| 6 Delete Movie | 7 Update Price | 8 View Bill  | 9 Message  | 10 Logout   |");
        System.out.println("+----------------+----------------+--------------+------------+-------------+");
        Scanner sc = new Scanner(System.in);
        int a;
        while (true) {
            System.out.print("--- Enter the choice number : ");
            try {
                a = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("--- Please enter number ---");
                sc.next();
                Option();
            }
        }
        switch (a) {
            case 1:
            m = 0;
            MovieRent.Type_Movie();
                break;
            case 2:
            m = 0;
            MovieRent.View_Movie();
                break;
            case 3:
            m = 0;
            MovieRent.All_Movies();
            case 4:
            m = 0;
            MovieRent.Add_type();
                break;
            case 5:
            m = 0;
            MovieRent.Add_Movie();
                break;
            case 6:
            m = 0;
            MovieRent.Delete_Movie();
            case 7:
            m = 0;
            MovieRent.Update_Price();
                break;
            case 8:
            m = 0;
            MovieRent.View_Bill();
                break;
            case 9:
            m = 0;
            MovieRent.Message();
                break;
            case 10:
            m = 0;
            Information.infor();
                break;
            default:
            while (n > m) {
                System.out.println("--- Please enter valid number ---");
                m++;
                Option();
            }
            m = 0;
            Information.infor();
                break;
        }
    }
}
