package oath2authorizeserver.scrabdatas.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oath2authorizeserver.scrabdatas.entity.StockEntity;
import oath2authorizeserver.scrabdatas.entity.pk.StockEntityPk;

@Repository
public interface StockRepo extends JpaRepository<StockEntity,StockEntityPk>{

}
