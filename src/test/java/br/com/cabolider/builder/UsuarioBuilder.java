package br.com.cabolider.builder;

import br.com.cabolider.modelo.Usuario;

public class UsuarioBuilder {

	private Usuario usuario=new Usuario();

	public UsuarioBuilder login(String login) {
		usuario.setLogin(login);
		return this;
	}

	public UsuarioBuilder senha(String senha) {
		usuario.setSenha(senha);
		return this;
	}
	
	public Usuario constroi(){
		return usuario;
	}

}
