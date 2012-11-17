package br.com.caelum.financas.util;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import br.com.caelum.financas.infra.JPAUtil;

public class IndexaTagsCadastradas {
	
	public static void main(String[] args) throws InterruptedException {
		EntityManager em = new JPAUtil().getEntityManager();
		FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
		ftem.createIndexer().startAndWait();
	}
}
