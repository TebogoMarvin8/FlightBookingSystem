
package za.co.mie.model;

public class FrequentFlyer{
    private int id;
    private int customerid;
    private String membershipNumber;
    private int milesBalance;

    public FrequentFlyer(int id, int customerid, String membershipNumber, int milesBalance) {
        this.id = id;
        this.customerid = customerid;
        this.membershipNumber = membershipNumber;
        this.milesBalance = milesBalance;
    }

    public FrequentFlyer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public int getMilesBalance() {
        return milesBalance;
    }

    public void setMilesBalance(int milesBalance) {
        this.milesBalance = milesBalance;
    }

    
    
}
