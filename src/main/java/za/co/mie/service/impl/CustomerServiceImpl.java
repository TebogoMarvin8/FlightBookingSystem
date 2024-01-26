
package za.co.mie.service.impl;


import za.co.mie.dao.CustomerDAO;
import za.co.mie.model.Customer;

import java.util.List;
import za.co.mie.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDAO customerDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerDAO.getCustomerById(id);
    }

    @Override
    public void saveCustomer(Customer customer) {
        if (customer.getId() == 0) {
            customerDAO.insertCustomer(customer);
        } else {
            customerDAO.updateCustomer(customer);
        }
    }

    @Override
    public void deleteCustomer(int id) {
        customerDAO.deleteCustomer(id);
    }
}
