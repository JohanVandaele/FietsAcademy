package be.vdab.services;

import javax.persistence.EntityManager;

import be.vdab.dao.DocentDAO;
import be.vdab.entities.Docent;
import be.vdab.filters.JPAFilter;

// enkele imports ...

public class DocentService
{
	// DocentService gebruikt DocentDAO.
	private final DocentDAO docentDAO = new DocentDAO();

	public Docent read(long id)
	{
		// Je vraagt een EntityManager aan de servlet filter JPAFilter
		EntityManager entityManager = JPAFilter.getEntityManager();
		
		try
		{
			// Je roept de DAO layer op en geeft de EntityManager mee.
			return docentDAO.read(id, entityManager);
		}
		finally
		{
			// Je sluit de EntityManager.
			entityManager.close();
		}
	}
	
	public void create(Docent docent)
	{
		EntityManager entityManager = JPAFilter.getEntityManager();
		
		try
		{
			entityManager.getTransaction().begin();
			docentDAO.create(docent, entityManager);
			entityManager.getTransaction().commit();
		}
		catch (RuntimeException ex)
		{
			entityManager.getTransaction().rollback();
			throw ex;
		}
		finally
		{
			entityManager.close();
		}
	}	
}
