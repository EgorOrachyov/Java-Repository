package Vehicles;

/**
 * Class Car extends class Vehicle and adds
 * some fields specified for this vehicles
 */
public class Car extends Vehicle
{
    {
        maxSpeed = 0;
        power = 0;
        numOfSeats = 0;
        averagePrice = 0;
        numOfFields = 4;
    }

    public Car()
    {

    }

    public Car(String name, String model, String vendor, String country,
               int maxSpeed, int power, int numOfSeats, int averagePrice)
    {
        super(name, model, vendor, country, "B class");

        this.maxSpeed = maxSpeed;
        this.power = power;
        this.numOfSeats = numOfSeats;
        this.averagePrice = averagePrice;
    }

    public String[] getInfo()
    {
        String[] oldData = super.getInfo();
        String[] data = new String[numOfFields + oldData.length];

        int i;
        for(i = 0; i < oldData.length; i++)
        {
            data[i] = oldData[i];
        }

        data[i + 0] = "Maximum speed: " + Integer.toString(maxSpeed) + " km/h";
        data[i + 1] = "Horses' power: " + Integer.toString(power);
        data[i + 2] = "Number of seats: " + Integer.toString(numOfSeats);
        data[i + 3] = "Average price in USD: " + Integer.toString(averagePrice) + "$";

        return data;
    }

    public int getMaxSpeed()
    {
        return maxSpeed;
    }

    public int getPower()
    {
        return power;
    }

    public int getNumOfSeats()
    {
        return numOfSeats;
    }

    public int getAveragePrice()
    {
        return averagePrice;
    }

    public String toString()
    {
        return super.toString() +
                "[" +
                "max speed=" + maxSpeed +
                ", power=" + power +
                ", num of seats=" + numOfSeats +
                ", average price=" + averagePrice +
                "]";
    }

    private int maxSpeed;
    private int power;
    private int numOfSeats;
    private int averagePrice;
    private int numOfFields;
}
