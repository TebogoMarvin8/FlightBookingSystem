
package za.co.mie.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import za.co.mie.dao.CityDAO;
import za.co.mie.model.City;

public class CityDAOImpl implements CityDAO {
    private Connection connection;

    public CityDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<City> getAllCities() {
        List<City> cities = new ArrayList<City>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cities");
            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("id"));
                city.setName(resultSet.getString("name"));
                cities.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    
    @Override
     public String getCityNameById(int cityId) {
        String cityName = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM Cities WHERE id = ?");
            statement.setInt(1, cityId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cityName = resultSet.getString("name");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cityName;
    }

    @Override
    public City getCityById(int id) {
        City city = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM cities WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                city = new City();
                city.setId(resultSet.getInt("id"));
                city.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public void insertCity(City city) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO cities (name) VALUES (?)");
            statement.setString(1, city.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCity(City city) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE cities SET name = ? WHERE id = ?");
            statement.setString(1, city.getName());
            statement.setInt(2, city.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCity(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM cities WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //----------------------------------------------------------------------------------
    
    public static void main(String[] args) {
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/blueskiesairline?useSSL=false", "root", "root");
//            
//            CityDAOImpl cityDAO = new CityDAOImpl(connection);
//
////            // Test getAllCities method
//            System.out.println("Testing getAllCities method:");
//            for (City city : cityDAO.getAllCities()) {
//                System.out.println("ID: " + city.getId() + ", Name: " + city.getName());
//            }
////
//            // Test getCityById method
//            System.out.println("\nTesting getCityById method:");
//            City city = cityDAO.getCityById(2);
//            if (city != null) {
//                System.out.println("ID: " + city.getId() + ", Name: " + city.getName());
//            }

//            // Test insertCity method
//            System.out.println("\nTesting insertCity method:");
//          City newCity = new City();
//            newCity.setName("Durban");
//            cityDAO.insertCity(newCity);
//
//            // Test updateCity method
//            System.out.println("\nTesting updateCity method:");
//            newCity.setName("Brazil");
//            cityDAO.updateCity(newCity);

//            // Test deleteCity method
//            System.out.println("\nTesting deleteCity method:");
//            cityDAO.deleteCity(1);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        System.out.println(UUID.randomUUID().toString().substring(0, 8));
    }
}
