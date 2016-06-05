package br.com.cabolider.mb;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.cabolider.dao.UsuarioDao;
import br.com.cabolider.modelo.Usuario;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	@Inject
	private UsuarioDao usuarioDao;

	@Inject
	private UsuarioLogadoBean usuarioLogadoBean;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String autentica() {
		if (usuarioDao.verificaSeUsuarioExiste(usuario)) {
			usuarioLogadoBean.logar(usuario);
			return "produto?faces-redirect=true";
		} else {
			this.usuario = new Usuario();
			return "login?faces-redirect=true";
		}
	}
}