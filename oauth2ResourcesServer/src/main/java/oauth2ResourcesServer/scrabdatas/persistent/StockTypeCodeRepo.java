package oauth2ResourcesServer.scrabdatas.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import oauth2ResourcesServer.scrabdatas.entity.StockcodeTypeEntity;
@Repository
public interface StockTypeCodeRepo extends JpaRepository<StockcodeTypeEntity, String>, JpaSpecificationExecutor<StockcodeTypeEntity> {

}
