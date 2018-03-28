import Vehicles.*;
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
            cars[0] = new BMWm3();
            cars[1] = new MercedesBenzEclass();
            cars[2] = new PorschePanamera();
            cars[3] = new LexusLFA();
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
                System.out.println(cars[carIndex].toString());
            }

            System.out.println();
        }
    }
}
