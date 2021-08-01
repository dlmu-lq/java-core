package concurrent.juc.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class GenericDao {

    private static final Logger log = LoggerFactory.getLogger(GenericDao.class);

    public <T> T queryOne(Class<T> type, String sql, Object...args){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("query One from dataBase");
        return (T) "test data";
    }

    public <T> void updateOne(Class<T> type, String sql, Object...args){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("update One to dataBase");
    }
}
