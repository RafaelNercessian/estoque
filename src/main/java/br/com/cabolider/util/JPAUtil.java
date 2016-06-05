package br.com.cabolider.util;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("estoque");

	@RequestScoped
	@Produces
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public void close(@Disposes EntityManager manager){
		manager.close();
	}
}