package br.com.caelum.financas.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.modelo.Tag;

public class TagDAO {
	
	private EntityManager em;
	private final DAO<Tag> dao;
	
	public TagDAO(EntityManager em) {
		this.em = em;
		this.dao = new DAO<Tag>(em, Tag.class);
	}
	
	public Tag adicionaOuBuscaTagNome(String nome) {
		String jpql = "SELECT t FROM Tag t WHERE t.nome = :nome";
		TypedQuery<Tag> query = this.em.createQuery(jpql, Tag.class);
		query.setParameter("nome", nome);
		Tag tag = null;
		try {
			tag = query.getSingleResult();
		}catch(NoResultException e) {
			tag = new Tag();
			tag.setNome(nome);
			em.persist(tag);
		}
		
		return tag;
	}


}
