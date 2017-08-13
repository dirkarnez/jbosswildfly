package org.jboss.tools.example.springmvc.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.jboss.tools.example.springmvc.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MemberDaoImpl implements MemberDao {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Member findById(Long id) {
		return em.find(Member.class, id);
	}

	@Override
	public Member findByEmail(String email) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = builder.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		criteria.select(member).where(builder.equal(member.get("email"), email));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<Member> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		criteria.select(member).orderBy(cb.asc(member.get("name")));
		List<Member> memberList = em.createQuery(criteria).getResultList();
		return memberList != null ? memberList : Collections.<Member>emptyList();
	}

	@Override
	public void register(Member member) {
		em.persist(member);
	}

	@Override
	public Member findNewComeMember() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Member> cq = cb.createQuery(Member.class);
	    Root<Member> root = cq.from(Member.class);

	    Subquery<Long> sq = cq.subquery(Long.class);
	    Root<Member> sroot = sq.from(Member.class);
	    sq.select(
	        cb.greatest(sroot.get("id"))
	    );
	    
	    cq.select(root).where(cb.equal(root.get("id"), sq));

	    TypedQuery<Member> query = em.createQuery(cq);
	    return query.getSingleResult();
	}
}