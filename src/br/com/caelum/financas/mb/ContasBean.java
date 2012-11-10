package br.com.caelum.financas.mb;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import br.com.caelum.financas.dao.ContaDAO;
import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Conta;

@ViewScoped
@ManagedBean
public class ContasBean {
	private Conta conta = new Conta();
	private List<Conta> contas;

	public void grava() {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		ContaDAO contaDao = new ContaDAO(em);
		
		if(this.conta.getId() == null) {
			contaDao.adiciona(conta);
		}else{
			contaDao.altera(conta);
		}
		
		this.contas = contaDao.lista();
		em.getTransaction().commit();
		em.close();

		limpaFormularioDoJSF();
	}

	public void remove() {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		ContaDAO contaDao = new ContaDAO(em);
		Conta remover = contaDao.busca(this.conta.getId());
		contaDao.remove(remover);
		
		this.contas = contaDao.lista();
		
		em.getTransaction().commit();
		em.close();
		
		limpaFormularioDoJSF();
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public List<Conta> getContas() {
		if (contas == null) {
			EntityManager em = new JPAUtil().getEntityManager();
			ContaDAO contaDao = new ContaDAO(em);
			this.contas = contaDao.lista();
			em.close();
		}
		return contas;
	}

	/**
	 * Esse método apenas limpa o formulário da forma com que o JSF espera.
	 * Invoque-o no momento em que precisar do formulário vazio.
	 */
	private void limpaFormularioDoJSF() {
		this.conta = new Conta();
	}
}
