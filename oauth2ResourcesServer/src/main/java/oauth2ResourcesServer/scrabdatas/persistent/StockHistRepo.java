package oauth2ResourcesServer.scrabdatas.persistent;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import oauth2ResourcesServer.scrabdatas.entity.SecuritiesEntity;
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

			queryPredicateList.add(criteriaBuilder.between(root.get("date"), startDt, endDt));

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
			queryPredicateList.add(criteriaBuilder.between(root.get("date"), startDt, endDt));
			return criteriaBuilder.and(queryPredicateList.toArray(new Predicate[0]));
		};
		List<StockHistEntity> resultList=null;
		try{
			resultList=findAll(specific);
		}catch(Exception e){
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
			return criteriaBuilder.and(criteriaBuilder.equal(root.get("date"), aDt));
		};
		return findAll(specific);
	}
	default List<StockHistEntity> findStkallPeriodDay(String startDt,String endDt) {
		Specification<StockHistEntity> specific = (root, query, criteriaBuilder) -> {
			root.fetch("securitiesEntity");
			Join<StockHistEntity, SecuritiesEntity> securJoin = root.join("securitiesEntity", JoinType.INNER);
			query.multiselect(root, securJoin.get("securityName"));
			List<Predicate> queryPredicateList = new ArrayList<>();
			queryPredicateList.add(criteriaBuilder.and(criteriaBuilder.equal(securJoin.get("securityCode"), root.get("stockCode"))));
			queryPredicateList.add(criteriaBuilder.between(root.get("date"), startDt, endDt));
			return criteriaBuilder.and(queryPredicateList.toArray(new Predicate[0]));
		};

		return findAll(specific);
	}


}
