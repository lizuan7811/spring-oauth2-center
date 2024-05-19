package oauth2ResourcesServer.scrabdatas.persistent;

import oauth2ResourcesServer.scrabdatas.entity.SecuritiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SecuritiesRepo extends JpaRepository<SecuritiesEntity, String>, JpaSpecificationExecutor<SecuritiesEntity> {

}
