package br.com.caelum.financas.testes;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.ValidatorUtil;

public class TesteValidacaoContaSemTitular {

	public static void main(String[] arg) {
		Validator validator = new ValidatorUtil().getValidator();

		Conta conta = new Conta();
		conta.setTitular("");
		conta.setAgencia("adw");
		Set<ConstraintViolation<Conta>> erros = validator.validate(conta);

		for (ConstraintViolation<Conta> erro : erros) {
			System.out.println(erro.getMessage());
			System.out.println(erro.getMessageTemplate());
		}

	}
	
}

