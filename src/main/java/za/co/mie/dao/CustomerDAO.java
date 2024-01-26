
package za.co.mie.dao;

import java.util.List;
import za.co.mie.model.Customer;


public interface CustomerDAO {
   List<Customer> getAllCustomers();
    Customer getCustomerById(int id);
    //boolean insertCustomer(Customer customer);
    int insertCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int id); 
    boolean isAddressExists(String address);
}
