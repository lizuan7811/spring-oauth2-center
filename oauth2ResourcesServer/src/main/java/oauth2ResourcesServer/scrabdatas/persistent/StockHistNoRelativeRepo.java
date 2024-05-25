package oauth2ResourcesServer.scrabdatas.persistent;

import oauth2ResourcesServer.scrabdatas.entity.SecuritiesEntity;
import oauth2ResourcesServer.scrabdatas.entity.StockHistEntity;
import oauth2ResourcesServer.scrabdatas.entity.StockHistNoRealtiveEntity;
import oauth2ResourcesServer.scrabdatas.entity.pk.StockHistEntityNoRelativePk;
import oauth2ResourcesServer.scrabdatas.entity.pk.StockHistEntityPk;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface StockHistNoRelativeRepo
		extends JpaRepository<StockHistNoRealtiveEntity, StockHistEntityNoRelativePk>, JpaSpecificationExecutor<StockHistNoRealtiveEntity> {


}
