package oath2authorizeserver.scrabdatas.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import oath2authorizeserver.scrabdatas.entity.StockcodeTypeEntity;
@Repository
public interface StockTypeCodeRepo extends JpaRepository<StockcodeTypeEntity, String>, JpaSpecificationExecutor<StockcodeTypeEntity> {

}
