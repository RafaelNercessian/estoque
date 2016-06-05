package br.com.cabolider.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cabolider.modelo.Usuario;

//Foram retirados os: manager.getTransaction.begin(), manager.getTransaction.commit() 
//e manager.close() de todos os métodos para que fosse possível realizar o teste

public class UsuarioDaoCopia implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	
	
	public UsuarioDaoCopia(EntityManager manager){
		this.manager=manager;
	}

	public boolean verificaSeUsuarioExiste(Usuario usuario) {
		Query query = manager
				.createQuery("select u from Usuario u where u.login=:Login and u.senha=:Senha")
		.setParameter("Login", usuario.getLogin())
		.setParameter("Senha", usuario.getSenha());
		boolean usuarioEncontrado = !query.getResultList().isEmpty();
		return usuarioEncontrado;

	}

	public void grava(Usuario usuario) {
		manager.persist(usuario);
	}
}
