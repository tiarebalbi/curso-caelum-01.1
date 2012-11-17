package br.com.caelum.financas.testes;

import javax.persistence.EntityManager;

import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Conta;

public class TesteCacheSegundoNivel {

	public static void main(String[] args) {
		EntityManager emOne = new JPAUtil().getEntityManager();
		emOne.getTransaction().begin();
		Conta primeiraConta = emOne.find(Conta.class, 1);
		emOne.getTransaction().commit();
		emOne.close();
		
		EntityManager emDois = new JPAUtil().getEntityManager();
		Conta segundaConta = emDois.find(Conta.class, 1);
		emDois.close();
		
		System.out.println("Titular Primeira Conta: " + primeiraConta.getTitular());
		System.out.println("Titular Segunda Conta: " + segundaConta.getTitular());
		
	}
	
}
