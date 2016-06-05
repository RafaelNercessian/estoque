package br.com.cabolider.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("br.com.cabolider.util.TamanhoValidator")
public class TamanhoValidator implements Validator {

	public void validate(FacesContext fc, UIComponent component, Object value)
			throws ValidatorException {
		String valor = value.toString();
		if (!valor.matches("^[0-9]*$")) {
			FacesMessage mensagem = new FacesMessage(
					"O campo tamanho só aceita números!");
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(mensagem);

		}
	}
}
