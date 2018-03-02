import java.util.Scanner;
import java.lang.*;

public class QuadricEvaluation {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Write a,b,c to evaluate:");
        System.out.print("a = ");
        int a = input.nextInt();
        System.out.print("b = ");
        int b = input.nextInt();
        System.out.print("c = ");
        int c = input.nextInt();

        if (c * c == a * a + b * b)
        {
            System.out.println("It is true");
        }
        else
        {
            System.out.println("It is false");
        }
    }
}
