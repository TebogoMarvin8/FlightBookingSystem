
package za.co.mie.model;


public class Customer {
    private int id;
    private String name;
    private String address;
    private String creditCardNumber;

    public Customer(int id, String name, String address, String creditCardNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.creditCardNumber = creditCardNumber;
    }

    public Customer(String name, String address, String creditCardNumber) {
        this.name = name;
        this.address = address;
        this.creditCardNumber = creditCardNumber;
    }

    
    public Customer() {
        this("Unknwon","Unknown","Unknown");
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
    
    
}
