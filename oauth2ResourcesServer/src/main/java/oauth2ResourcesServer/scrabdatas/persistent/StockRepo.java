package oauth2ResourcesServer.scrabdatas.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oauth2ResourcesServer.scrabdatas.entity.StockEntity;
import oauth2ResourcesServer.scrabdatas.entity.pk.StockEntityPk;

@Repository
public interface StockRepo extends JpaRepository<StockEntity,StockEntityPk>{

}
