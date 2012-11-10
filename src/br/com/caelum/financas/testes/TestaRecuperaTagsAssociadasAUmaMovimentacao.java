package br.com.caelum.financas.testes;

import javax.persistence.EntityManager;

import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.Tag;

public class TestaRecuperaTagsAssociadasAUmaMovimentacao {

	public static void main(String[] args) {
		EntityManager em = new JPAUtil().getEntityManager();
		Movimentacao mov = em.find(Movimentacao.class, 4);
		for(Tag tag : mov.getTags()) {
			System.out.println(tag.getNome());
		}
		em.close();
	}
	
}
