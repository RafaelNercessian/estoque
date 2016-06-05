package br.com.cabolider.mb;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.cabolider.modelo.Usuario;

@Named
@SessionScoped
public class UsuarioLogadoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void logar(Usuario usuario){
		this.usuario=usuario;
	}
	
	public String deslogar(){
		this.usuario=null;
		return "login?faces-redirect=true";
	}
	
	public boolean isLogado(){
		if(this.usuario!=null){
			return true;
		}
		else
			return false;
	}
}
