import java.util.InputMismatchException;
import java.util.Scanner;

public class Information {
    static Scanner sc = new Scanner(System.in);
    public static void infor() {
        System.out.println("--- Welcome Movie Rent System ---");
        System.out.println("1. Shopkeeper");
        System.out.println("2. Customer");
        int a;
         while (true) {
            System.out.print("--- Enter the choice number : ");
            try {
                a = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("--- Please enter an integer value ---");
                sc.next();
                Information.infor(); 
            }
        }
        switch (a) {
            case 1:
                System.out.println("--- Login Account ---");
                MovieRent.ShopkeeperLogin();
                break;
            case 2:
                Customer();
                break;
            default:
                System.out.println("--- Please enter valid number ---");
                infor();
                break;
        }
    }

    public static void Customer() {
        System.out.println("1. Sign-up Account");
        System.out.println("2. Login Account");
        int b;
         while (true) {
            System.out.print("--- Enter the number : ");
            try {
                b = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("--- Please enter an int value ---");
                sc.next();
            }
        }
        switch (b) {
            case 1:
                Customer.Customersign();
                break;
            case 2:
                Customer.CustomerLogin();
                break;
            default:
                System.out.println("--- Please enter valid number ---");
                Customer();
                break;
        }
    }
}
