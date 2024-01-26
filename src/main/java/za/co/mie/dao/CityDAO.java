
package za.co.mie.dao;

import java.util.List;
import za.co.mie.model.City;


public interface CityDAO {
    List<City> getAllCities();
    City getCityById(int id);
    String getCityNameById(int cityId);
    void insertCity(City city);
    void updateCity(City city);
    void deleteCity(int id);
}
