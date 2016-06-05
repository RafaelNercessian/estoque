package br.com.cabolider.mb;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.cabolider.builder.UsuarioBuilder;
import br.com.cabolider.dao.UsuarioDao;
import br.com.cabolider.modelo.Usuario;

public class LoginBeanTest {

	private UsuarioDao usuarioDao;
	private LoginBeanCopia loginBeanCopia;
	private UsuarioLogadoBean usuarioLogadoBean;

	@Before
	public void setUp() {
		usuarioDao = Mockito.mock(UsuarioDao.class);
		usuarioLogadoBean = Mockito.mock(UsuarioLogadoBean.class);
		loginBeanCopia = new LoginBeanCopia(usuarioDao, usuarioLogadoBean);
	}

	@Test
	public void testandoAutenticacaoUsuarioExistente() {
		Usuario usuario = new UsuarioBuilder().login("admin").senha("12345")
				.constroi();
		loginBeanCopia.setUsuario(usuario);

		Mockito.when(usuarioDao.verificaSeUsuarioExiste(usuario)).thenReturn(
				true);

		loginBeanCopia.autentica();

		Mockito.verify(usuarioLogadoBean).logar(usuario);
	}
	
	@Test
	public void testandoAutenticacaoUsuarioInexistente() {
		Usuario usuario = new UsuarioBuilder().login("admin").senha("12345")
				.constroi();
		loginBeanCopia.setUsuario(usuario);

		Mockito.when(usuarioDao.verificaSeUsuarioExiste(usuario)).thenReturn(
				false);

		loginBeanCopia.autentica();

		Mockito.verify(usuarioLogadoBean,Mockito.never()).logar(usuario);
	}
}
