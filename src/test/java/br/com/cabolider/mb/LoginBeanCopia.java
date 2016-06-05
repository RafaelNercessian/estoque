package br.com.cabolider.mb;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.cabolider.dao.UsuarioDao;
import br.com.cabolider.modelo.Usuario;

@Named
@SessionScoped
public class LoginBeanCopia implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();
	private UsuarioDao dao;
	private UsuarioLogadoBean usuarioLogadoBean;

	public LoginBeanCopia(UsuarioDao dao, UsuarioLogadoBean usuarioLogadoBean) {
		this.dao = dao;
		this.usuarioLogadoBean=usuarioLogadoBean;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String autentica() {
		if (dao.verificaSeUsuarioExiste(usuario)) {
			usuarioLogadoBean.logar(usuario);
			return "produto?faces-redirect=true";
		} else {
			this.usuario = new Usuario();
			return "login?faces-redirect=true";
		}
	}
}