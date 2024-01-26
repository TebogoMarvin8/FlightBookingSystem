
package za.co.mie.service.impl;


import za.co.mie.dao.CityDAO;
import za.co.mie.model.City;

import java.util.List;
import za.co.mie.service.CityService;

public class CityServiceImpl implements CityService {
    private CityDAO cityDAO;

    public CityServiceImpl(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Override
    public List<City> getAllCities() {
        return cityDAO.getAllCities();
    }

    @Override
    public City getCityById(int id) {
        return cityDAO.getCityById(id);
    }

    @Override
    public void saveCity(City city) {
        if (city.getId() == 0) {
            cityDAO.insertCity(city);
        } else {
            cityDAO.updateCity(city);
        }
    }

    @Override
    public void deleteCity(int id) {
        cityDAO.deleteCity(id);
    }
}
