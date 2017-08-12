package org.jboss.tools.example.springmvc.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.tools.example.springmvc.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class MemberDaoImpl implements MemberDao
{
//	@PersistenceUnit
//    private EntityManagerFactory entityManagerFactory;
	
	@PersistenceContext
	private EntityManager em;
//    
    public Member findById(Long id)
    {
    	return em.find(Member.class, id);
        //return entityManagerFactory.createEntityManager().find(Member.class, id);
    }

    public Member findByEmail(String email)
    {
//    	EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Member> criteria = builder.createQuery(Member.class);
        Root<Member> member = criteria.from(Member.class);

        /*
         * Swap criteria statements if you would like to try out type-safe criteria queries, a new
         * feature in JPA 2.0 criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
         */

        criteria.select(member).where(builder.equal(member.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Member> findAllOrderedByName()
    {
//    	EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
        Root<Member> member = criteria.from(Member.class);

        /*
         * Swap criteria statements if you would like to try out type-safe criteria queries, a new
         * feature in JPA 2.0 criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
         */

        criteria.select(member).orderBy(cb.asc(member.get("name")));
        List<Member> memberList = em.createQuery(criteria).getResultList();
        return memberList != null ? memberList : Collections.<Member>emptyList();
    }

    public void register(Member member)
    {
//    	EntityManager em = entityManagerFactory.createEntityManager();
    	em.persist(member);
    }
}