
package za.co.mie.service.impl;
import za.co.mie.dao.FrequentFlyerDAO;
import za.co.mie.model.FrequentFlyer;

import java.util.List;
import za.co.mie.service.FrequentFlyerService;

public class FrequentFlyerServiceImpl implements FrequentFlyerService {
    private FrequentFlyerDAO frequentFlyerDAO;

    public FrequentFlyerServiceImpl(FrequentFlyerDAO frequentFlyerDAO) {
        this.frequentFlyerDAO = frequentFlyerDAO;
    }

    @Override
    public List<FrequentFlyer> getAllFrequentFlyers() {
        return frequentFlyerDAO.getAllFrequentFlyers();
    }

    @Override
    public FrequentFlyer getFrequentFlyerById(int id) {
        return frequentFlyerDAO.getFrequentFlyerById(id);
    }
@Override
public boolean saveFrequentFlyer(FrequentFlyer frequentFlyer) {
    boolean saved = false;
    if (frequentFlyer.getId() == 0) {
        saved = frequentFlyerDAO.insertFrequentFlyer(frequentFlyer);
    }
    return saved;
}


    @Override
    public void deleteFrequentFlyer(int id) {
        frequentFlyerDAO.deleteFrequentFlyer(id);
    }
}
