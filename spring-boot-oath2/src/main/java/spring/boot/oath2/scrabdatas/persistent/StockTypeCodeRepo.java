package spring.boot.oath2.scrabdatas.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import spring.boot.oath2.scrabdatas.entity.StockcodeTypeEntity;
@Repository
public interface StockTypeCodeRepo extends JpaRepository<StockcodeTypeEntity, String>, JpaSpecificationExecutor<StockcodeTypeEntity> {

}
