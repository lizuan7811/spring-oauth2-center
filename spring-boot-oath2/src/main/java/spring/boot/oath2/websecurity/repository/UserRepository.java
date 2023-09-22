package spring.boot.oath2.websecurity.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public abstract class UserRepository<T> extends SimpleJpaRepository<T, Integer> implements JpaSpecificationExecutor<T>
{

	@Autowired
	public UserRepository(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
	}
	
	public abstract T findByUsername(String username,Class<T> cla);

	public abstract List<T> getRoleByuid(int id,Class<T> cla);

}
