package oauth2ResourcesServer.scrabdatas.persistent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import oauth2ResourcesServer.scrabdatas.entity.SecuritiesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import oauth2ResourcesServer.scrabdatas.entity.StockHistEntity;
import oauth2ResourcesServer.scrabdatas.entity.pk.StockHistEntityPk;

@Repository
public interface StockHistRepo
        extends JpaRepository<StockHistEntity, StockHistEntityPk>, JpaSpecificationExecutor<StockHistEntity> {

    default List<StockHistEntity> findByStockCodeAndDate(String stockCode, String startDt, String endDt) {
        Specification<StockHistEntity> specific = (root, query, criteriaBuilder) -> {
            List<Predicate> queryPredicateList = new ArrayList<Predicate>();

            queryPredicateList.add(criteriaBuilder.equal(root.get("stockCode"), stockCode));

            queryPredicateList.add(criteriaBuilder.between(root.get("datet"), startDt, endDt));

            return criteriaBuilder.and(queryPredicateList.toArray(new Predicate[0]));
        };
        return findAll(specific);
    }

    default List<StockHistEntity> findByPkBetweenStartDtAndEndDt(String stockCode, String startDt, String endDt) {
        Specification<StockHistEntity> specific = (root, query, criteriaBuilder) -> {
            root.fetch("securitiesEntity");
            Join<StockHistEntity, SecuritiesEntity> securJoin = root.join("securitiesEntity", JoinType.INNER);
            query.multiselect(root, securJoin.get("securityName"));
            List<Predicate> queryPredicateList = new ArrayList<Predicate>();
            queryPredicateList.add(criteriaBuilder.equal(root.get("stockCode"), stockCode));
            queryPredicateList.add(criteriaBuilder.and(criteriaBuilder.equal(securJoin.get("securityCode"), root.get("stockCode"))));
            queryPredicateList.add(criteriaBuilder.between(root.get("datet"), startDt, endDt));
            return criteriaBuilder.and(queryPredicateList.toArray(new Predicate[0]));
        };
        List<StockHistEntity> resultList = null;
        try {
            resultList = findAll(specific);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    default List<StockHistEntity> findAllByStockCode(String stockCode) {
        Specification<StockHistEntity> specific = (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(criteriaBuilder.equal(root.get("stockCode"), stockCode));
        };
        return findAll(specific);
    }

    default List<StockHistEntity> findStkallOneDay() {
        Specification<StockHistEntity> specific = (root, query, criteriaBuilder) -> {
            List<Predicate> queryPredicateList = new ArrayList<>();
            queryPredicateList.add(criteriaBuilder.equal(criteriaBuilder.length(root.get("stockCode")), 4));

            Subquery<Long> maxDateSubquery = query.subquery(Long.class);
            Root<StockHistEntity> maxDateRoot = maxDateSubquery.from(StockHistEntity.class);
            maxDateSubquery.select(criteriaBuilder.max(maxDateRoot.get("datet")));
            Predicate maxDatePredicate = criteriaBuilder.equal(root.get("datet"), maxDateSubquery);
            queryPredicateList.add(maxDatePredicate);


            return criteriaBuilder.and(queryPredicateList.toArray(new Predicate[0]));
        };
        return findAll(specific);
    }

    default List<StockHistEntity> findStkallPeriodDay(String startDt, String endDt) {
        Specification<StockHistEntity> specific = (root, query, criteriaBuilder) -> {
            root.fetch("securitiesEntity");
            Join<StockHistEntity, SecuritiesEntity> securJoin = root.join("securitiesEntity", JoinType.INNER);
            query.multiselect(root, securJoin.get("securityName"));
            List<Predicate> queryPredicateList = new ArrayList<>();
            queryPredicateList.add(criteriaBuilder.and(criteriaBuilder.equal(securJoin.get("securityCode"), root.get("stockCode"))));
            queryPredicateList.add(criteriaBuilder.equal(criteriaBuilder.length(root.get("stockCode")), 4));
            queryPredicateList.add(criteriaBuilder.between(root.get("datet"), startDt, endDt));
            return criteriaBuilder.and(queryPredicateList.toArray(new Predicate[0]));
        };

        return findAll(specific);
    }


    @Query(nativeQuery = true, value = "SELECT * FROM (SELECT sh.*, se.securityName,ROW_NUMBER() OVER (PARTITION BY sh.stockCode ORDER BY sh.datet DESC) AS row_num FROM [dbo].[STOCK_HIST_DATA] sh INNER JOIN [dbo].[Securities] se ON sh.stockCode = se.securityCode WHERE sh.datet <= FORMAT(GETDATE(),'yyy-MM-dd') AND len(sh.stockCode) = 4 AND sh.stockCode = se.securityCode) AS ranked_data WHERE row_num < 120;")
    List<StockHistEntity> findBeforDatasFmCurDate(String curDate);

}
