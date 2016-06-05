package br.com.cabolider.mb;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import br.com.cabolider.builder.EntradaBuilder;
import br.com.cabolider.builder.ProdutoBuilder;
import br.com.cabolider.dao.EntradaDao;
import br.com.cabolider.dao.ProdutoDao;
import br.com.cabolider.modelo.Entrada;
import br.com.cabolider.modelo.Produto;

public class EntradaBeanTest {

	private ProdutoDao produtoDaoFalse;
	private EntradaDao entradaDaoFalse;
	private EntradaBeanCopia entradaBeanCopia;

	@Before
	public void setUp() {
		produtoDaoFalse = Mockito.mock(ProdutoDao.class);
		entradaDaoFalse = Mockito.mock(EntradaDao.class);
		entradaBeanCopia = new EntradaBeanCopia(produtoDaoFalse,
				entradaDaoFalse);
	}

	@Test
	public void testandoProdutoASerInseridoQuandoEleExistirNoBDComMesmoTamanho()
			throws IOException {
		entradaBeanCopia.setValorASerInserido(10);

		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();
		
		entradaBeanCopia.setProduto(produto);

		Mockito.when(produtoDaoFalse.retornaProduto(produto)).thenReturn(
				produto);

		entradaBeanCopia.produtoASerInserido();

		ArgumentCaptor<Produto> produtoRetornado = ArgumentCaptor
				.forClass(Produto.class);
		Mockito.verify(produtoDaoFalse).retornaProduto(
				produtoRetornado.capture());

		assertEquals("5.14.001.001", produtoRetornado.getValue().getCodigo());
		assertEquals("Branco", produtoRetornado.getValue().getCor());
		assertEquals("Cabo PP", produtoRetornado.getValue().getDescricao());
		assertEquals("1 - Parede", produtoRetornado.getValue().getLocalizacao());
		assertEquals(15, produtoRetornado.getValue().getSaldo(), 0.0001);
		assertEquals("100", produtoRetornado.getValue().getTamanho());
		assertEquals("Nu", produtoRetornado.getValue().getTipoDeCobre());
	}

	@Test
	public void testandoProdutoASerInseridoQuandoEleExistirNoBDComTamanhoDiferente()
			throws IOException {
		entradaBeanCopia.setValorASerInserido(10);

		Produto produtoPesquisado = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();

		Produto produtoNoBD = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("40").tipoDeCobre("Nu").constroi();

		Produto produtoNoBD2 = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("30").tipoDeCobre("Nu").constroi();

		List<Produto> lista = Arrays.asList(produtoNoBD, produtoNoBD2);

		Mockito.when(produtoDaoFalse.retornaProduto(produtoPesquisado))
				.thenReturn(null);
		Mockito.when(
				produtoDaoFalse.pesquisaProdutoPeloCodigo(produtoPesquisado))
				.thenReturn(lista);

		entradaBeanCopia
				.verificaSeCodigoJaExisteComOutrosTamanhos(produtoPesquisado);

		// Quer dizer que ele invocou o método verificaSeCodigoJaExiste, como
		// esperado!
		assertEquals(2, entradaBeanCopia.getRetornoPesquisaPeloCodigo().size());
		assertEquals("5.14.001.001", entradaBeanCopia
				.getRetornoPesquisaPeloCodigo().get(0).getCodigo());
		assertEquals("Branco", entradaBeanCopia.getRetornoPesquisaPeloCodigo()
				.get(0).getCor());
		assertEquals("Cabo PP", entradaBeanCopia.getRetornoPesquisaPeloCodigo()
				.get(0).getDescricao());
		assertEquals("1 - Parede", entradaBeanCopia
				.getRetornoPesquisaPeloCodigo().get(0).getLocalizacao());
		assertEquals("Nu",
				entradaBeanCopia.getRetornoPesquisaPeloCodigo().get(0)
						.getTipoDeCobre());
	}

	@Test
	public void testandoProdutoASerInseridoQuandoEleNaoExistirNoBDNemComTamanhoDiferente() {
		entradaBeanCopia.setValorASerInserido(10);

		Produto produtoPesquisado = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();

		Mockito.when(produtoDaoFalse.retornaProduto(produtoPesquisado))
				.thenReturn(null);
		Mockito.when(
				produtoDaoFalse.pesquisaProdutoPeloCodigo(produtoPesquisado))
				.thenReturn(null);

		entradaBeanCopia
				.verificaSeCodigoJaExisteComOutrosTamanhos(produtoPesquisado);

		assertNull(entradaBeanCopia.getRetornoPesquisaPeloCodigo());
	}

	@Test
	public void testandoRetornoDosProdutosInseridos() {
		Entrada entrada1 = new EntradaBuilder().codigo("5.14.001.001")
				.data(Calendar.getInstance()).descricao("Cabo PP")
				.ordemDeProducao("21342/3").qtdeDeEntrada(4).tamanho("100")
				.constroi();

		Entrada entrada2 = new EntradaBuilder().codigo("5.11.001.001")
				.data(Calendar.getInstance()).descricao("Cabo Paralelo")
				.ordemDeProducao("11112/3").qtdeDeEntrada(14).tamanho("100")
				.constroi();

		List<Entrada> listaProdutosInseridos = Arrays
				.asList(entrada1, entrada2);
		Mockito.when(entradaDaoFalse.listarProdutosInseridos()).thenReturn(
				listaProdutosInseridos);

		List<Entrada> retornoProdutosInseridos = entradaBeanCopia
				.getRetornoProdutosInseridos();

		assertEquals(2, retornoProdutosInseridos.size());
		assertEquals("5.14.001.001", retornoProdutosInseridos.get(0)
				.getCodigo());
		assertEquals("5.11.001.001", retornoProdutosInseridos.get(1)
				.getCodigo());
	}
}
