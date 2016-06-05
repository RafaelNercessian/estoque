package br.com.cabolider.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.cabolider.modelo.Usuario;
import br.com.cabolider.util.JPAUtil;

public class UsuarioDaoTest {

	private EntityManager manager;
	private UsuarioDaoCopia dao;

	@Before
	public void setUp(){
		manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		dao = new UsuarioDaoCopia(manager);
	}
	
	@After
	public void finaliza(){
		manager.getTransaction().rollback();
		manager.close();
	}
	
	@Test
	public void procurandoUsuarioExistente(){
		Usuario usuario = new Usuario();
		usuario.setLogin("Cabolider");
		usuario.setSenha("1234");
		
		dao.grava(usuario);
		
		boolean verificaSeUsuarioExiste = dao.verificaSeUsuarioExiste(usuario);
		
		assertTrue(verificaSeUsuarioExiste);
	}
	
	@Test
	public void procurandoUsuarioInexistente(){
		Usuario usuario = new Usuario();
		usuario.setLogin("Diretoria");
		usuario.setSenha("4567");
		
		boolean verificaSeUsuarioExiste = dao.verificaSeUsuarioExiste(usuario);
		
		assertFalse(verificaSeUsuarioExiste);
		
	}
}
