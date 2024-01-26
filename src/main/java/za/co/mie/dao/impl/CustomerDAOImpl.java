package za.co.mie.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import za.co.mie.dao.CustomerDAO;
import za.co.mie.manager.DbManager;
import za.co.mie.model.Customer;

public class CustomerDAOImpl implements CustomerDAO {

    private Connection connection;
    private DbManager db;
    
    
    public CustomerDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean isAddressExists(String address) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //db = new DbManager();
        db = DbManager.getInstance();
        

        try {
            connection = db.getConnection();

            String query = "SELECT COUNT(*) FROM customers WHERE address = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, address);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<Customer>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Customers");
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setAddress(resultSet.getString("address"));
                customer.setCreditCardNumber(resultSet.getString("credit_card_number"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Customer getCustomerById(int id) {
        Customer customer = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customers WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setAddress(resultSet.getString("address"));
                customer.setCreditCardNumber(resultSet.getString("credit_card_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    
    @Override
    public int insertCustomer(Customer customer) {
    int generatedCustomerId = 0;
    try {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Customers (name, address, credit_card_number) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getAddress());
        statement.setString(3, customer.getCreditCardNumber());

        int affectedRows = statement.executeUpdate();

        if (affectedRows >0 ) {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedCustomerId = generatedKeys.getInt(1);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return generatedCustomerId;
}

    
    @Override
    public void updateCustomer(Customer customer) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Customers SET name = ?, address = ?, credit_card_number = ? WHERE id = ?");
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getCreditCardNumber());
            statement.setInt(4, customer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Customers WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
