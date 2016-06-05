package br.com.cabolider.dao;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.cabolider.builder.EntradaBuilder;
import br.com.cabolider.modelo.Entrada;
import br.com.cabolider.util.JPAUtil;

public class EntradaDaoTest {

private EntityManager manager;
private EntradaDaoCopia entradaDaoCopia;
	
	@Before
	public void setUp() {
		manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		entradaDaoCopia = new EntradaDaoCopia(manager);
	}

	@After
	public void finaliza() {
		manager.getTransaction().rollback();
		manager.close();
	}
	
	@Test
	public void testandoProdutosInseridos(){
		Entrada entrada = new EntradaBuilder().codigo("5.14.001.001")
		.descricao("Cabo PP").ordemDeProducao("33312/3")
		.qtdeDeEntrada(5).tamanho("100").data(Calendar.getInstance()).constroi();
		
		Entrada entrada2 = new EntradaBuilder().codigo("5.10.001.001")
				.descricao("Cabo Flexivel").ordemDeProducao("33312/3")
				.qtdeDeEntrada(5).tamanho("100").data(Calendar.getInstance()).constroi();
		
		entradaDaoCopia.gravarEntrada(entrada);
		entradaDaoCopia.gravarEntrada(entrada2);
		
		List<Entrada> listarProdutosInseridos = entradaDaoCopia.listarProdutosInseridos();
		
		assertEquals(2, listarProdutosInseridos.size());
		assertEquals("5.14.001.001", listarProdutosInseridos.get(0).getCodigo());
		assertEquals("5.10.001.001", listarProdutosInseridos.get(1).getCodigo());
	}
		
}
