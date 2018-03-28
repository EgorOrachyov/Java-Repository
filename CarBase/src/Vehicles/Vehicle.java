package Vehicles;

/**
 * Base realization of the vehicle class
 * with common methods
 */
public class Vehicle
{
    // initialization block
    {
        name = "none";
        model = "none";
        vendor = "none";
        country = "none";
        category = "none";
        numOfFields = 5;
    }

    public Vehicle()
    {

    }

    public Vehicle(String name, String model, String vendor, String country, String category)
    {
        this.name = name;
        this.model = model;
        this.vendor = vendor;
        this.country = country;
        this.category = category;
    }

    public String[] getInfo()
    {
        String[] data = new String[numOfFields];

        data[0] = "Brand name: " + name;
        data[1] = "Model name: " + model;
        data[2] = "Manufacturer: " + vendor;
        data[3] = "Manufacturing country: " + country;
        data[4] = "Category policy: " + category;

        return data;
    }

    public String getName()
    {
        return name;
    }

    public String getModel()
    {
        return model;
    }

    public String getVendor()
    {
        return vendor;
    }

    public String getCountry()
    {
        return country;
    }

    public String getCategory()
    {
        return category;
    }

    public String toString()
    {
        return getClass().getName() +
                "[" +
                "name=" + name +
                ", model=" + model +
                ", vendor=" + vendor +
                ", country=" + country +
                ", category=" + category +
                "]";
    }

    private String name;
    private String model;
    private String vendor;
    private String country;
    private String category;
    private int numOfFields;
}
