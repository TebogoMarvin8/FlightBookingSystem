
package za.co.mie.service;

import java.util.List;
import za.co.mie.model.City;


public interface CityService {
    List<City> getAllCities();
    City getCityById(int id);
    void saveCity(City city);
    void deleteCity(int id);
}
