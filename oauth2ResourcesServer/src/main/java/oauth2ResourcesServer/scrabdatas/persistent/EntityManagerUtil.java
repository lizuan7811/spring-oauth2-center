package oauth2ResourcesServer.scrabdatas.persistent;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @description: EntityManagerUtil
 * @author: Lizuan
 * @date: 2024/5/25
 * @time: 上午 01:35
 **/
@Component
@Log4j2
public class EntityManagerUtil {

    @Autowired
    private EntityManager entityManager;
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    public <T extends Object> Collection<T> bulkSave(Collection<T> entities) {
        final List<T> savedEntities = new ArrayList<T>(entities.size());
        try {
            int i = 0;
            for (T t : entities) {
                savedEntities.add(entityManager.merge(t));
                i++;
                if (i % batchSize == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }
        } catch (Exception e) {
            log.error(">>> ", e);
        }
        return savedEntities;
    }

    public void bulkInsertWithJdbc(List<Map<String, String>> stocks, String sql) {
        try {
            namedParameterJdbcTemplate.batchUpdate(sql, stocks.toArray(new Map[stocks.size()]));
        } catch (Exception e) {
            log.debug("批量更新出現異常: ", e);
        }
    }

}