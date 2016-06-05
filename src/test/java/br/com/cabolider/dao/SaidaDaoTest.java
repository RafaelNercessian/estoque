package br.com.cabolider.dao;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.cabolider.builder.SaidaBuilder;
import br.com.cabolider.modelo.Saida;
import br.com.cabolider.util.JPAUtil;

public class SaidaDaoTest {

	private EntityManager manager;
	private SaidaDaoCopia dao;

	@Before
	public void setUp() {
		manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		dao = new SaidaDaoCopia(manager);
	}

	@After
	public void finaliza() {
		manager.getTransaction().rollback();
		manager.close();
	}

	@Test
	public void testandoMetodoListaSaida() {

		Saida saida = new SaidaBuilder().codigo("5.14.001.001")
				.descricao("Cabo PP").tamanho("100").nomeDoCliente("Cabolider")
				.qtdeRetirada(1).data(Calendar.getInstance()).constroi();
		Saida saida2 = new SaidaBuilder().codigo("5.11.001.010")
				.descricao("Cabo Paralelo").tamanho("100").nomeDoCliente("Cabolider")
				.qtdeRetirada(1).data(Calendar.getInstance()).constroi();
		
		dao.gravarSaida(saida);
		dao.gravarSaida(saida2);
		
		List<Saida> itensDeSaida = dao.listaItensDeSaida();
		assertEquals(2,itensDeSaida.size());
		assertEquals("5.14.001.001", itensDeSaida.get(0).getCodigo());
		assertEquals("5.11.001.010", itensDeSaida.get(1).getCodigo());
	}
}
