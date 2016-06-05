package br.com.cabolider.mb;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import br.com.cabolider.builder.ProdutoBuilder;
import br.com.cabolider.builder.SaidaBuilder;
import br.com.cabolider.dao.ProdutoDao;
import br.com.cabolider.dao.SaidaDao;
import br.com.cabolider.modelo.Produto;
import br.com.cabolider.modelo.Saida;

public class SaidaBeanTest {

	private SaidaDao saidaDao;
	private ProdutoDao produtoDao;
	private SaidaBeanCopia saidaBeanCopia;

	@Before
	public void setUp() {
		saidaDao = Mockito.mock(SaidaDao.class);
		produtoDao = Mockito.mock(ProdutoDao.class);
		saidaBeanCopia = new SaidaBeanCopia(saidaDao, produtoDao);
	}

	@Test
	public void testandoProdutoASerRetiradoQuantidadeMenorQueOEstoque()
			throws Exception {
		saidaBeanCopia.setValorASerRetirado(10);

		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(15).tamanho("100").tipoDeCobre("Nu").constroi();
		
		saidaBeanCopia.setProduto(produto);

		Mockito.when(produtoDao.retornaProduto(produto)).thenReturn(produto);
		saidaBeanCopia.produtoASerRetirado();

		ArgumentCaptor<Produto> produtoRetornado = ArgumentCaptor
				.forClass(Produto.class);
		Mockito.verify(produtoDao).retornaProduto(produtoRetornado.capture());

		assertEquals("5.14.001.001", produtoRetornado.getValue().getCodigo());
		assertEquals("Branco", produtoRetornado.getValue().getCor());
		assertEquals("Cabo PP", produtoRetornado.getValue().getDescricao());
		assertEquals("1 - Parede", produtoRetornado.getValue().getLocalizacao());
		assertEquals(5, produtoRetornado.getValue().getSaldo(), 0.0001);
		assertEquals("100", produtoRetornado.getValue().getTamanho());
		assertEquals("Nu", produtoRetornado.getValue().getTipoDeCobre());
	}
	
	@Test
	public void testandoProdutoASerRetiradoQuantidadeIgualAoEstoque()
			throws Exception {
		saidaBeanCopia.setValorASerRetirado(15);

		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(15).tamanho("100").tipoDeCobre("Nu").constroi();
		
		saidaBeanCopia.setProduto(produto);
	
		Mockito.when(produtoDao.retornaProduto(produto)).thenReturn(produto);
		saidaBeanCopia.produtoASerRetirado();

		ArgumentCaptor<Produto> produtoRetornado = ArgumentCaptor
				.forClass(Produto.class);
		Mockito.verify(produtoDao).retornaProduto(produtoRetornado.capture());

		assertEquals("5.14.001.001", produtoRetornado.getValue().getCodigo());
		assertEquals("Branco", produtoRetornado.getValue().getCor());
		assertEquals("Cabo PP", produtoRetornado.getValue().getDescricao());
		assertEquals("1 - Parede", produtoRetornado.getValue().getLocalizacao());
		assertEquals(0, produtoRetornado.getValue().getSaldo(), 0.0001);
		assertEquals("100", produtoRetornado.getValue().getTamanho());
		assertEquals("Nu", produtoRetornado.getValue().getTipoDeCobre());
	}
	
	@Test(expected=NullPointerException.class)
	public void testandoProdutoASerRetiradoQuantidadeMaiorQueOEstoque()
			throws Exception {
		saidaBeanCopia.setValorASerRetirado(20);

		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(15).tamanho("100").tipoDeCobre("Nu").constroi();
		
		saidaBeanCopia.setProduto(produto);

		Mockito.when(produtoDao.retornaProduto(produto)).thenReturn(null);
		saidaBeanCopia.produtoASerRetirado();
	}
	
	@Test
	public void testandoRetornoDosProdutosRetirados(){
		Saida saida = new SaidaBuilder().codigo("5.14.001.001").data(Calendar.getInstance())
		.descricao("Cabo PP").nomeDoCliente("Cabolider").qtdeRetirada(5).tamanho("100")
		.constroi();
		
		Saida saida2 = new SaidaBuilder().codigo("5.11.001.001").data(Calendar.getInstance())
				.descricao("Cabo Paralelo").nomeDoCliente("Cabolider").qtdeRetirada(15).tamanho("100")
				.constroi();
		
		List<Saida> listaProdutoRetirados = Arrays.asList(saida,saida2);
		
		Mockito.when(saidaDao.listaItensDeSaida()).thenReturn(listaProdutoRetirados);
		
		saidaBeanCopia.getRetornoProdutoRetirado();
		
		assertEquals(2,saidaBeanCopia.getItensDeSaida().size());
	}
	
	@Test
	public void testandoProdutoRetiradoComTamanhoMenorQueCemESaldoZero() throws Exception{
		saidaBeanCopia.setValorASerRetirado(15);

		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(15).tamanho("70").tipoDeCobre("Nu").constroi();
		
		saidaBeanCopia.setProduto(produto);
	
		Mockito.when(produtoDao.retornaProduto(produto)).thenReturn(produto);
		saidaBeanCopia.produtoASerRetirado();
		
		Mockito.verify(produtoDao,Mockito.times(1)).remove(produto);
	}
}
