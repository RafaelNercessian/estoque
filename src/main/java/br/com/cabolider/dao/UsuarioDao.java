package br.com.cabolider.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cabolider.modelo.Usuario;
import br.com.cabolider.util.JPAUtil;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	public boolean verificaSeUsuarioExiste(Usuario usuario) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		Query query = manager
				.createQuery("select u from Usuario u where u.login=:Login and u.senha=:Senha")
		.setParameter("Login", usuario.getLogin())
		.setParameter("Senha", usuario.getSenha());
		boolean usuarioEncontrado = !query.getResultList().isEmpty();
		manager.close();
		return usuarioEncontrado;

	}

	public void grava(Usuario usuario) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.persist(usuario);
		manager.getTransaction().commit();
		manager.close();
	}
}
