
package za.co.mie.dao;

import java.util.List;
import za.co.mie.model.FrequentFlyer;


public interface FrequentFlyerDAO {
    List<FrequentFlyer> getAllFrequentFlyers();
    FrequentFlyer getFrequentFlyerById(int id);
    boolean insertFrequentFlyer(FrequentFlyer frequentFlyer);
    void updateFrequentFlyer(FrequentFlyer frequentFlyer);
    void deleteFrequentFlyer(int id);
    boolean checkFrequentFlyerStatus(int id, int customer_id);
    boolean customerIdExists(int customerId);
    FrequentFlyer getFrequentFlyerByCustomerId(int customerId);
}
