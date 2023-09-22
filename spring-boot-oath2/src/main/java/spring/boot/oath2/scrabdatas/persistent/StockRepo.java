package spring.boot.oath2.scrabdatas.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.boot.oath2.scrabdatas.entity.StockEntity;
import spring.boot.oath2.scrabdatas.entity.pk.StockEntityPk;

@Repository
public interface StockRepo extends JpaRepository<StockEntity,StockEntityPk>{

}
