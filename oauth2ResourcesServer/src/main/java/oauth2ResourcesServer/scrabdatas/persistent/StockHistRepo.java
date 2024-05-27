package oauth2ResourcesServer.scrabdatas.persistent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
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

    default List<StockHistEntity> findStkallOneDay(String aDt) {
        Specification<StockHistEntity> specific = (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(criteriaBuilder.equal(root.get("datet"), aDt));
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
            queryPredicateList.add(criteriaBuilder.between(root.get("datet"), startDt, endDt));
            return criteriaBuilder.and(queryPredicateList.toArray(new Predicate[0]));
        };

        return findAll(specific);
    }


    default Page<StockHistEntity> findBeforDatasFmCurDate(String stockCode, String curDate) {
        Specification<StockHistEntity> specific = (root, query, criteriaBuilder) -> {

            List<Predicate> queryPredicateList = new ArrayList<>();

            if (StringUtils.isNotBlank(stockCode)) {
                queryPredicateList.add(criteriaBuilder.equal(root.get("stockCode"), stockCode));
            }
            queryPredicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("datet"), curDate));
            Join<StockHistEntity, SecuritiesEntity> securJoin = root.join("securitiesEntity", JoinType.INNER);
            query.multiselect(root, securJoin.get("securityName"));
            queryPredicateList.add(criteriaBuilder.equal(root.get("stockCode"),securJoin.get("securityCode")));
            return criteriaBuilder.and(queryPredicateList.toArray(new Predicate[0]));
        };
		Page<StockHistEntity> resultList = null;
        try {
			Pageable topOneHundredTwenty = PageRequest.of(0, 120, Sort.by("datet").descending());

			resultList = findAll(specific,topOneHundredTwenty);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

}
