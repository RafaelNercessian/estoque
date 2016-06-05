package br.com.cabolider.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.cabolider.builder.ProdutoBuilder;
import br.com.cabolider.modelo.Produto;
import br.com.cabolider.util.JPAUtil;

public class ProdutoDaoTest {

	private EntityManager manager;
	private ProdutoDaoCopia dao;
	
	@Before
	public void setUp() {
		manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		dao = new ProdutoDaoCopia(manager);
	}

	@After
	public void finaliza() {
		manager.getTransaction().rollback();
		manager.close();
	}

	@Test
	public void testandoMetodoListaProdutoComDoisCabosComDescricaoParecidaECodigosDiferentes() {
		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();
		Produto produto2 = new ProdutoBuilder().codigo("5.10.001.001")
				.cor("Branco").descricao("Cabo PP Flexivel").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();
		
		dao.gravar(produto);
		dao.gravar(produto2);
		List<Produto> listaPorProduto = dao.listaPorProduto(produto);
		
		assertEquals(2, listaPorProduto.size());
		assertEquals("5.10.001.001",listaPorProduto.get(0).getCodigo());
		assertEquals("5.14.001.001",listaPorProduto.get(1).getCodigo());
	}
	
	@Test
	public void testandoMetodoListaProdutoDoisCabosComDescricaoECodigosDiferentes() {
		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();
		Produto produto2 = new ProdutoBuilder().codigo("5.20.001.001")
				.cor("Branco").descricao("Cabo Flexivel").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();
		
		dao.gravar(produto);
		dao.gravar(produto2);
		List<Produto> listaPorProduto = dao.listaPorProduto(produto);
		
		assertEquals(1, listaPorProduto.size());
		assertEquals("5.14.001.001",listaPorProduto.get(0).getCodigo());
	}
	
	@Test
	public void testandoMetodoListaProdutoDoisCabosComDescricaoECodigosIguais() {
		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();
		Produto produto2 = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("40").tipoDeCobre("Nu").constroi();
		
		dao.gravar(produto);
		dao.gravar(produto2);
		List<Produto> listaPorProduto = dao.listaPorProduto(produto);
		
		assertEquals(2, listaPorProduto.size());
		assertEquals("5.14.001.001",listaPorProduto.get(0).getCodigo());
		assertEquals("5.14.001.001",listaPorProduto.get(1).getCodigo());
	}

	@Test
	public void testandoMetodoRetornaProduto(){
		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();
		Produto produto2 = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP Flexivel").localizacao("1 - Parede")
				.saldo(11).tamanho("40").tipoDeCobre("Nu").constroi();
		
		dao.gravar(produto);
		dao.gravar(produto2);
		
		Produto produtoRetornado = dao.retornaProduto(produto);
		assertEquals("5.14.001.001", produtoRetornado.getCodigo());
		assertEquals("Branco", produtoRetornado.getCor());
		assertEquals("Cabo PP", produtoRetornado.getDescricao());
		assertEquals("1 - Parede", produtoRetornado.getLocalizacao());
		assertEquals(5, produtoRetornado.getSaldo(),0.0001);
		assertEquals("100", produtoRetornado.getTamanho());
		assertEquals("Nu", produtoRetornado.getTipoDeCobre());
	}
	
	@Test
	public void testandoPesquisaPeloCodigoComCodigosIguais(){
		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();
		Produto produto2 = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(15).tamanho("30").tipoDeCobre("Nu").constroi();
		
		dao.gravar(produto);
		dao.gravar(produto2);
		
		List<Produto> pesquisaProdutoPeloCodigo = dao.pesquisaProdutoPeloCodigo(produto);
		assertEquals(2, pesquisaProdutoPeloCodigo.size());
		assertEquals("100", pesquisaProdutoPeloCodigo.get(0).getTamanho());
		assertEquals("30", pesquisaProdutoPeloCodigo.get(1).getTamanho());
	}
	
	@Test
	public void testandoPesquisaPeloCodigoComCodigosDiferentes(){
		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();
		Produto produto2 = new ProdutoBuilder().codigo("5.10.002.003")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(15).tamanho("80").tipoDeCobre("Sn").constroi();
		
		dao.gravar(produto);
		dao.gravar(produto2);
		
		List<Produto> pesquisaProdutoPeloCodigo = dao.pesquisaProdutoPeloCodigo(produto);
		assertEquals(1, pesquisaProdutoPeloCodigo.size());
		assertEquals("5.14.001.001", pesquisaProdutoPeloCodigo.get(0).getCodigo());
	}
	
	@Test
	public void testandoMetodoAltera(){
		Produto produto = new ProdutoBuilder().codigo("5.14.001.001")
				.cor("Branco").descricao("Cabo PP").localizacao("1 - Parede")
				.saldo(5).tamanho("100").tipoDeCobre("Nu").constroi();
		
		dao.gravar(produto);
		
		Produto retornaProduto = dao.retornaProduto(produto);
		assertEquals("5.14.001.001", retornaProduto.getCodigo());
		assertEquals("Cabo PP", retornaProduto.getDescricao());
		
		retornaProduto.setCodigo("5.11.001.010");
		retornaProduto.setDescricao("Cabo Paralelo");
		
		dao.altera(retornaProduto);
		manager.flush();
		
		
		Produto retornaProdutoAlterado = dao.retornaProduto(retornaProduto);
		assertEquals("5.11.001.010",retornaProdutoAlterado.getCodigo());
		assertEquals("Cabo Paralelo",retornaProdutoAlterado.getDescricao());
	}
}
