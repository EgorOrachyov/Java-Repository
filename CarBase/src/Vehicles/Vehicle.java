package Vehicles;

/**
 * Base realization of the vehicle class
 * with common methods
 */
public class Vehicle {
    // initialization block
    {
        name = "none";
        model = "none";
        vendor = "none";
        country = "none";
        category = "none";
    }

    public Vehicle() { }

    public Vehicle(String name, String model, String vendor, String country, String category) {
        this.name = name;
        this.model = model;
        this.vendor = vendor;
        this.country = country;
        this.category = category;
    }

    public String getInfo() {
        return "Brand name: " + name +
                ", Model name: " + model +
                ", Manufacturer: " + vendor +
                ", Manufacturing country: " + country +
                ", Category policy: " + category;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getVendor() {
        return vendor;
    }

    public String getCountry() {
        return country;
    }

    public String getCategory() {
        return category;
    }

    public String toString() {
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
}
