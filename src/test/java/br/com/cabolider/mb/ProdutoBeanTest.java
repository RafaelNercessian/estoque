package br.com.cabolider.mb;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.cabolider.builder.ProdutoBuilder;
import br.com.cabolider.dao.ProdutoDao;
import br.com.cabolider.modelo.Produto;

public class ProdutoBeanTest {

	private ProdutoBeanCopia produtoBean;
	private ProdutoDao daoFalso;

	@Before
	public void setUp() {
		daoFalso = Mockito.mock(ProdutoDao.class);
		produtoBean = new ProdutoBeanCopia(daoFalso);
	}

	@Test
	public void testandoPesquisa() {
		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();

		Produto produto2 = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("40").tipoDeCobre("Nu").constroi();

		produtoBean.setProduto(produto);

		List<Produto> listaProdutos = Arrays.asList(produto, produto2);

		Mockito.when(daoFalso.listaPorProduto(produto)).thenReturn(
				listaProdutos);

		List<Produto> lista = produtoBean.getLista();

		assertEquals(2, lista.size());
		assertEquals("100", lista.get(0).getTamanho());
		assertEquals("40", lista.get(1).getTamanho());
	}
}
