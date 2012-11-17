package br.com.caelum.financas.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;

public class MovimentacaoDAO {

	private final DAO<Movimentacao> dao;

	private EntityManager em;

	public MovimentacaoDAO(EntityManager em) {
		this.em = em;
		dao = new DAO<Movimentacao>(em, Movimentacao.class);
	}

	public void adiciona(Movimentacao t) {
		dao.adiciona(t);
	}

	public Movimentacao busca(Integer id) {
		return dao.busca(id);
	}

	public List<Movimentacao> lista() {
		return dao.lista();
	}

	public void remove(Movimentacao t) {
		dao.remove(t);
	}

	public List<Movimentacao> listarTodasMovimentacoes(Conta conta) {
		String jpql = "SELECT m FROM Movimentacao m WHERE m.conta = :conta ORDER BY m.valor DESC";
		TypedQuery<Movimentacao> query = this.em.createQuery(jpql,
				Movimentacao.class);
		query.setParameter("conta", conta);
		return query.getResultList();
	}

	public List<Movimentacao> listarPorValorETipo(BigDecimal valor,
			TipoMovimentacao tipo) {
		String jpql = "SELECT m FROM Movimentacao m "
				+ "WHERE m.valor = :valor AND m.tipoMovimentacao = :tipo "
				+ "ORDER BY m.valor DESC";
		TypedQuery<Movimentacao> query = this.em.createQuery(jpql,
				Movimentacao.class);
		query.setParameter("valor", valor);
		query.setParameter("tipo", tipo);
		return query.getResultList();
	}

	public BigDecimal calculaTotalMovimentado(Conta conta, TipoMovimentacao tipo) {
		String jpql = "SELECT SUM(m.valor) FROM Movimentacao m "
				+ "WHERE m.conta = :conta AND m.tipoMovimentacao = :tipo ";
		TypedQuery<BigDecimal> query = this.em.createQuery(jpql,
				BigDecimal.class);
		query.setParameter("conta", conta);
		query.setParameter("tipo", tipo);
		return query.getSingleResult();
	}

	public List<Movimentacao> buscarTodasMovimentacoesDaConta(String titular) {
		String jpql = "SELECT m FROM Movimentacao m WHERE m.conta.titular LIKE :titular";
		TypedQuery<Movimentacao> query = this.em.createQuery(jpql,
				Movimentacao.class);
		query.setParameter("titular", "%" + titular + "%");
		return query.getResultList();
	}

	public List<Movimentacao> pesquisar(Conta conta, TipoMovimentacao tipo,
			Integer mes) {

		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<Movimentacao> cq = builder
				.createQuery(Movimentacao.class);
		Root<Movimentacao> root = cq.from(Movimentacao.class);
		Predicate conjunction = builder.conjunction();

		if (conta.getId() != null) {
			conjunction = builder.and(conjunction,
					builder.equal(root.<Conta> get("conta"), conta));
		}

		if (mes != null && mes != 0) {
			Expression<Integer> expres = builder.function("month",
					Integer.class, root.<Calendar> get("data"));
			conjunction = builder.and(conjunction, builder.equal(expres, mes));
		}

		if (tipo != null) {
			conjunction = builder.and(conjunction, builder.equal(
					root.<TipoMovimentacao> get("tipoMovimentacao"), tipo));
		}
		cq.where(conjunction);
		return this.em.createQuery(cq).getResultList();
	}
	
	public List<Movimentacao> buscaMovimentacoesBaseadoNasTags(String text) throws ParseException {
		FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
		QueryParser parser = new QueryParser(Version.LUCENE_30, "tags.nome", new BrazilianAnalyzer(Version.LUCENE_30));
		Query query = parser.parse(text);
		FullTextQuery textQuery = ftem.createFullTextQuery(query, Movimentacao.class);
		return textQuery.getResultList();
	}
	
	public List<Movimentacao> listarTodasMovimentacoesComCache(Conta conta) {
		String jpql = "SELECT m FROM Movimentacao m WHERE m.conta = :conta ORDER BY m.valor DESC";
		TypedQuery<Movimentacao> query = this.em.createQuery(jpql,
				Movimentacao.class);
		query.setParameter("conta", conta);
		
		query.setHint("org.hibernate.cacheable", true);
		return query.getResultList();
	}
}
