package br.com.caelum.financas.mb;

import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;

import br.com.caelum.financas.dao.ContaDAO;
import br.com.caelum.financas.dao.MovimentacaoDAO;
import br.com.caelum.financas.dao.TagDAO;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.Tag;
import br.com.caelum.financas.modelo.TipoMovimentacao;


@ManagedBean
public class MovimentacoesBean {
	private List<Movimentacao> movimentacoes;
	private Movimentacao movimentacao = new Movimentacao();
	private Integer contaId;
	private String tags;
	@ManagedProperty(name="em",value="#{requestScope.em}")
	private EntityManager em;
	
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public void grava() {
		MovimentacaoDAO dao = new MovimentacaoDAO(em);
		ContaDAO contaDao = new ContaDAO(em);
		
		Conta relacao = contaDao.busca(contaId);
		movimentacao.setConta(relacao);
		gravaEAssociaAsTags(em);
		dao.adiciona(movimentacao);
		this.movimentacoes = dao.lista();
		
		limpaFormularioDoJSF();
	}
	

	public void remove() {
		
		MovimentacaoDAO dao = new MovimentacaoDAO(em);
		
		Movimentacao movRemovi = dao.busca(this.movimentacao.getId());
		dao.remove(movRemovi);
		this.movimentacoes = dao.lista();
		
		limpaFormularioDoJSF();
	}

	public List<Movimentacao> getMovimentacoes() {
		if(movimentacoes == null) {
			MovimentacaoDAO dao = new MovimentacaoDAO(em);
			this.movimentacoes = dao.lista();
		}
		return movimentacoes;
	}
	
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	

	public Movimentacao getMovimentacao() {
		if(movimentacao.getData()==null) {
			movimentacao.setData(Calendar.getInstance());
		}
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public Integer getContaId() {
		return contaId;
	}

	public void setContaId(Integer contaId) {
		this.contaId = contaId;
	}

	/**
	 * Esse método apenas limpa o formulário da forma com que o JSF espera.
	 * Invoque-o no momento em que precisar do formulário vazio.
	 */
	private void limpaFormularioDoJSF() {
		this.movimentacao = new Movimentacao();
		this.tags = null;
	}

	public TipoMovimentacao[] getTiposDeMovimentacao() {
		return TipoMovimentacao.values();
	}
	
	private void gravaEAssociaAsTags(EntityManager em) {
		String[]  nomeDasTags = this.tags.split(" ");
		TagDAO tagDao = new TagDAO(em);
		for(String nome : nomeDasTags) { 
			Tag tag = tagDao.adicionaOuBuscaTagNome(nome);
			movimentacao.getTags().add(tag);
		}
	}
}
