package br.com.caelum.financas.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

import br.com.caelum.financas.validator.PossuiNumeroEAgencia;

@PossuiNumeroEAgencia
@Entity
public class Conta {

	@Id
	@GeneratedValue
	private Integer id;
	@NotBlank
	private String titular;
	private String agencia;
	private String numero;
	private String banco;
	@OneToMany(mappedBy="conta")
	private List<Movimentacao> movimentacao;

	/**
	 * @return the movimentacao
	 */
	public List<Movimentacao> getMovimentacao() {
		return movimentacao;
	}

	/**
	 * @param movimentacao the movimentacao to set
	 */
	public void setMovimentacao(List<Movimentacao> movimentacao) {
		this.movimentacao = movimentacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

}
