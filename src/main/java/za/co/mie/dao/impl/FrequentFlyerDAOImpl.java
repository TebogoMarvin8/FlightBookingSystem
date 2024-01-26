package za.co.mie.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import za.co.mie.dao.FrequentFlyerDAO;
import za.co.mie.model.FrequentFlyer;

public class FrequentFlyerDAOImpl implements FrequentFlyerDAO {

    private Connection connection;

    public FrequentFlyerDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<FrequentFlyer> getAllFrequentFlyers() {
        List<FrequentFlyer> frequentFlyers = new ArrayList<FrequentFlyer>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM frequentflyers");
            while (resultSet.next()) {
                FrequentFlyer frequentFlyer = new FrequentFlyer();
                frequentFlyer.setId(resultSet.getInt("id"));
                frequentFlyers.add(frequentFlyer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return frequentFlyers;
    }

    @Override
    public FrequentFlyer getFrequentFlyerById(int id) {
        FrequentFlyer frequentFlyer = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM frequentflyers WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                frequentFlyer = new FrequentFlyer();
                frequentFlyer.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return frequentFlyer;
    }

    @Override
    public FrequentFlyer getFrequentFlyerByCustomerId(int customerId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        FrequentFlyer frequentFlyer = null;

        try {
            String query = "SELECT * FROM frequentflyers WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String membershipNumber = resultSet.getString("membership_number");
                int milesBalance = resultSet.getInt("miles_balance");

                frequentFlyer = new FrequentFlyer(id, customerId, membershipNumber, milesBalance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return frequentFlyer;
    }

    @Override
    public boolean insertFrequentFlyer(FrequentFlyer frequentFlyer) {
        PreparedStatement preparedStatement = null;
        boolean inserted = false;

        try {
            String query = "INSERT INTO frequentflyers (customer_id, membership_number, miles_balance) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, frequentFlyer.getCustomerid());
            preparedStatement.setString(2, frequentFlyer.getMembershipNumber());
            preparedStatement.setInt(3, frequentFlyer.getMilesBalance());

            int rowsAffected = preparedStatement.executeUpdate();
            inserted = (rowsAffected > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return inserted;
    }

    @Override
    public void updateFrequentFlyer(FrequentFlyer frequentFlyer) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE frequentflyers SET customer_id = ?, membership_number = ?, miles_balance = ? WHERE id = ?");
            statement.setInt(1, frequentFlyer.getCustomerid());
            statement.setString(2, frequentFlyer.getMembershipNumber());
            statement.setInt(3, frequentFlyer.getMilesBalance());
            statement.setInt(4, frequentFlyer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean customerIdExists(int customerId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exists = false;

        try {
            String query = "SELECT COUNT(*) FROM frequentflyers WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                exists = (count > 0); // If count > 0, the customerId exists in the table
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exists;
    }

    @Override
    public void deleteFrequentFlyer(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM frequentflyers WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkFrequentFlyerStatus(int id, int customer_id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM frequentflyers WHERE id = ? AND customer_id = ?");
            statement.setInt(1, id);
            statement.setInt(2, customer_id);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

////-----------------------------------------------------------------------------------------------
//    public static void main(String[] args) {
//        String jdbcURL = "jdbc:mysql://localhost:3306/blueskiesairline?useSSL=false";
//        String username = "root";
//        String password = "root";
//        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
//            FrequentFlyerDAOImpl frequentFlyerDAO = new FrequentFlyerDAOImpl(connection);
//            // Test getAllFrequentFlyers method
//            System.out.println("All Frequent Flyers:");
//            for (FrequentFlyer flyer : frequentFlyerDAO.getAllFrequentFlyers()) {
//                System.out.println("ID: " + flyer.getId());
//            }
//
//            // Test getFrequentFlyerById method
//            int flyerId = 1; // Replace with an existing ID from your database
//            FrequentFlyer flyerById = frequentFlyerDAO.getFrequentFlyerById(flyerId);
//            if (flyerById != null) {
//                System.out.println("Frequent Flyer by ID " + flyerId + ":");
//                System.out.println("ID: " + flyerById.getId());
//            } else {
//                System.out.println("Frequent Flyer with ID " + flyerId + " not found.");
//            }
//            // Test insertFrequentFlyer method
//            FrequentFlyer newFrequentFlyer = new FrequentFlyer();
//            newFrequentFlyer.setMembershipNumber("ABCD123");
//            newFrequentFlyer.setMilesBalance(1000);
//            newFrequentFlyer.setCustomerid(140);
//            System.out.println("New Frequent Flyer inserted."+ frequentFlyerDAO.insertFrequentFlyer(newFrequentFlyer));
//            // Test updateFrequentFlyer method
//            int updateFlyerId = 10;
//            FrequentFlyer flyerToUpdate = frequentFlyerDAO.getFrequentFlyerById(updateFlyerId);
//            if (flyerToUpdate != null) {
//                flyerToUpdate.setMilesBalance(2000);
//                frequentFlyerDAO.updateFrequentFlyer(flyerToUpdate);
//                System.out.println("Frequent Flyer with ID " + updateFlyerId + " updated.");
//            } else {
//                System.out.println("Frequent Flyer with ID " + updateFlyerId + " not found for update.");
//            }
//
//            // Test deleteFrequentFlyer method
//            int deleteFlyerId = 10;
//            frequentFlyerDAO.deleteFrequentFlyer(deleteFlyerId);
//            System.out.println("Frequent Flyer with ID " + deleteFlyerId + " deleted.");
//
//            // Test checkFrequentFlyerStatus method
//            int checkId = 2; // Replace with an existing ID from your database
//            int checkCustomerId = 1; // Replace with an existing customer ID from your database
//            boolean isFrequentFlyer = frequentFlyerDAO.checkFrequentFlyerStatus(checkId, checkCustomerId);
//            System.out.println("Check Frequent Flyer Status for ID " + checkId + ": " + isFrequentFlyer);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
