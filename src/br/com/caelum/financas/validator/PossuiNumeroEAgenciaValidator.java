package br.com.caelum.financas.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.caelum.financas.modelo.Conta;

public class PossuiNumeroEAgenciaValidator implements ConstraintValidator<PossuiNumeroEAgencia, Conta> {

	@Override
	public void initialize(PossuiNumeroEAgencia init) {
		
	}

	@Override
	public boolean isValid(Conta entidade, ConstraintValidatorContext ctx) {
		if(entidade == null) {
			return true;
		}
		return ! (entidade.getAgencia() == null ^ entidade.getNumero() == null);
	}

}
