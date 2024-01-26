
package za.co.mie.service;

import java.util.List;
import za.co.mie.model.FrequentFlyer;


public interface FrequentFlyerService {
    List<FrequentFlyer> getAllFrequentFlyers();
    FrequentFlyer getFrequentFlyerById(int id);
    boolean saveFrequentFlyer(FrequentFlyer frequentFlyer);
    void deleteFrequentFlyer(int id);
}
