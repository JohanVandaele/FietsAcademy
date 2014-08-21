package be.vdab.services;

import java.math.BigDecimal;

//import javax.persistence.EntityManager;

import be.vdab.dao.DocentDAO;
import be.vdab.entities.Docent;
//import be.vdab.filters.JPAFilter;

// enkele imports ...

public class DocentService
{
	// DocentService gebruikt DocentDAO.
	private final DocentDAO docentDAO = new DocentDAO();

	public Docent read(long id)
	{
//		// Je vraagt een EntityManager aan de servlet filter JPAFilter
//		EntityManager entityManager = JPAFilter.getEntityManager();
//		
//		try
//		{
//			// Je roept de DAO layer op en geeft de EntityManager mee.
//			return docentDAO.read(id, entityManager);
//		}
//		finally
//		{
//			// Je sluit de EntityManager.
//			entityManager.close();
//		}

		return docentDAO.read(id);
	}
	
	public void create(Docent docent)
	{
//		EntityManager entityManager = JPAFilter.getEntityManager();
//		
//		try
//		{
//			entityManager.getTransaction().begin();
//			docentDAO.create(docent, entityManager);
//			entityManager.getTransaction().commit();
//		}
//		catch (RuntimeException ex)
//		{
//			entityManager.getTransaction().rollback();
//			throw ex;
//		}
//		finally
//		{
//			entityManager.close();
//		}
		
		docentDAO.beginTransaction();
		docentDAO.create(docent);
		docentDAO.commit();
	}	

	public void delete(long id)
	{
//		EntityManager entityManager = JPAFilter.getEntityManager();
//		
//		try
//		{
//			entityManager.getTransaction().begin();
//			docentDAO.delete(id, entityManager);
////			entityManager.getTransaction().commit();
//			entityManager.getTransaction().rollback();
//		}
//		catch (RuntimeException ex)
//		{
//			entityManager.getTransaction().rollback();
//			throw ex;
//		}
//		finally
//		{
//			entityManager.close();
//		}
		
		docentDAO.beginTransaction();
		docentDAO.delete(id);
		docentDAO.commit();
	}

	public void opslag(long id, BigDecimal percentage)
	{
//		EntityManager entityManager = JPAFilter.getEntityManager();
//		
//		try
//		{
//			entityManager.getTransaction().begin();
//			docentDAO.read(id, entityManager).opslag(percentage);
//			// JPA stuurt bij de commit op de transactie een update statement naar de database en wijzigt hiermee het record dat bij de gewijzigde entity hoort.
//			entityManager.getTransaction().commit();
//			//entityManager.getTransaction().rollback();
//		}
//		catch (RuntimeException ex)
//		{
//			entityManager.getTransaction().rollback();
//			throw ex;
//		}
//		finally
//		{
//			entityManager.close();
//		}
		
		docentDAO.beginTransaction();
		docentDAO.read(id).opslag(percentage);
		docentDAO.commit();
	}
}
