package oath2authorizeserver.websecurity.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import oath2authorizeserver.websecurity.repository.UserRepository;

@Component
public class UserRepositoryImpl<T> extends UserRepository<T>{
	
	@Autowired
	private EntityManager entityManager;

	public UserRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
	}

	public T findByUsername(String username,Class<T> cla) {
		CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cQuery=criteriaBuilder.createQuery(cla);
		Root<T> root=cQuery.from(cla);
		Predicate predName =criteriaBuilder.equal(root.get("username"), username);
		
		cQuery.where(predName);
		TypedQuery<T> typeQuery=entityManager.createQuery(cQuery);
		return (T)typeQuery.getResultList().get(0);
	}

	public List<T> getRoleByuid(int id,Class<T> cla) {
		CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cQuery=criteriaBuilder.createQuery(cla);
		Root<T> root=cQuery.from(cla);
		Predicate predName =criteriaBuilder.equal(root.get("id"), id);
		cQuery.where(predName);
		TypedQuery<T> typeQuery=entityManager.createQuery(cQuery);
		return (List<T>)typeQuery.getResultList();
	}

	public List<T> getRoleByuid(String userName,Class<T> cla) {
		
//		User.id==UserRole.uid & UserRole.rid==Role.id
//		UserRole.uid不為pk，所以會有多個。
		
		CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cQuery=criteriaBuilder.createQuery(cla);
		Root<T> root=cQuery.from(cla);
		Predicate predName =criteriaBuilder.equal(root.get("username"), userName);
		cQuery.where(predName);
		TypedQuery<T> typeQuery=entityManager.createQuery(cQuery);
		return (List<T>)typeQuery.getResultList();
	}
	
}
