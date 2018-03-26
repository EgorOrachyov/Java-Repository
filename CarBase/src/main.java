import Vehicles.Car;
import java.util.Scanner;

public class main
{
    public static void main(String[] args)
    {
        int numOfCars = 4;
        int exitApp = numOfCars + 1;
        Car[] cars = new Car[numOfCars];

        // cars initialization
        {
            cars[0] = new Car("BMW", "M3", "BMW Group", "Germany", 230, 310, 5, 45000);
            cars[1] = new Car("Mercedes-Benz", "E-class", "Daimler AG", "Germany", 275, 510, 4, 115000);
            cars[2] = new Car("Porsche", "Panamera", "Volkswagen Group", "Germany", 320, 525, 4, 150000);
            cars[3] = new Car("Lexus", "LFA", "Lexus", "Japan", 365, 670, 2, 350000);
        }

        System.out.println("*** Cars Data Base ***");
        Scanner in = new Scanner(System.in);

        boolean isDone = false;
        while (!isDone)
        {
            boolean isOk = false;
            boolean showCarInfo = false;
            int carIndex = 0;

            while(!isOk)
            {
                System.out.println("*** Choose action ***");
                for(int i = 0; i < numOfCars; i++)
                {
                    System.out.println((i + 1) + ". Get info about " + cars[i].getName() + " " + cars[i].getModel());
                }
                System.out.println((exitApp) + ". Exit application");

                int action = in.nextInt();
                if (action == exitApp)
                {
                    isDone = true;
                    isOk = true;
                    System.out.println("Exit the application");
                }
                else if (1 <= action && action <= numOfCars)
                {
                    isOk = true;
                    showCarInfo = true;
                    carIndex = action - 1;
                }
            }

            if (showCarInfo)
            {
                String[] data = cars[carIndex].getInfo();
                for(String s: data)
                {
                    System.out.println(s);
                }
            }

            System.out.println();
        }
    }
}
