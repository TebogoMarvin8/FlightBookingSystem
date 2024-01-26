
package za.co.mie.service;

import java.util.List;
import za.co.mie.model.Customer;


public interface CustomerService {
     List<Customer> getAllCustomers();
    Customer getCustomerById(int id);
    void saveCustomer(Customer customer);
    void deleteCustomer(int id);
}
