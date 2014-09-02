package be.vdab.services;

import java.math.BigDecimal;
//import be.vdab.filters.JPAFilter;
//import javax.persistence.EntityManager;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

import be.vdab.dao.CampusDAO;
import be.vdab.dao.DocentDAO;
import be.vdab.entities.Docent;
import be.vdab.exceptions.DocentBestaatAlException;
import be.vdab.exceptions.DocentNietGevondenException;
import be.vdab.exceptions.RecordAangepastException;
import be.vdab.valueobjects.AantalDocentenPerWedde;
import be.vdab.valueobjects.VoornaamEnId;

// enkele imports ...

public class DocentService
{
	// DocentService gebruikt DocentDAO.
	private final DocentDAO docentDAO = new DocentDAO();
	private final CampusDAO campusDAO = new CampusDAO();
	
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
	
//	public void create(Docent docent)
//	{
////		EntityManager entityManager = JPAFilter.getEntityManager();
////		
////		try
////		{
////			entityManager.getTransaction().begin();
////			docentDAO.create(docent, entityManager);
////			entityManager.getTransaction().commit();
////		}
////		catch (RuntimeException ex)
////		{
////			entityManager.getTransaction().rollback();
////			throw ex;
////		}
////		finally
////		{
////			entityManager.close();
////		}
//		
//		docentDAO.beginTransaction();
//		docentDAO.create(docent);
//		docentDAO.commit();
//	}	
	
	public void create(Docent docent)
	{
		if (docentDAO.findByRijksRegisterNr(docent.getRijksRegisterNr()) != null)
		{
			throw new DocentBestaatAlException();
		}
		
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

//	public void opslag(long id, BigDecimal percentage)
//	{
////		EntityManager entityManager = JPAFilter.getEntityManager();
////		
////		try
////		{
////			entityManager.getTransaction().begin();
////			docentDAO.read(id, entityManager).opslag(percentage);
////			// JPA stuurt bij de commit op de transactie een update statement naar de database en wijzigt hiermee het record dat bij de gewijzigde entity hoort.
////			entityManager.getTransaction().commit();
////			//entityManager.getTransaction().rollback();
////		}
////		catch (RuntimeException ex)
////		{
////			entityManager.getTransaction().rollback();
////			throw ex;
////		}
////		finally
////		{
////			entityManager.close();
////		}
//		
//		docentDAO.beginTransaction();
//		//docentDAO.read(id).opslag(percentage);
//		docentDAO.readWithLock(id).opslag(percentage);
//		docentDAO.commit();
//	}

	public void opslag(long id, BigDecimal percentage)
	{
		docentDAO.beginTransaction();
		// Je gebruikt bij optimistic record locking de method find van EntityManager zonder de derde parameter.
		Docent docent = docentDAO.read(id);
		
		if (docent == null)
		{
			throw new DocentNietGevondenException();
		}
		
		docent.opslag(percentage);
		
		try
		{
			docentDAO.commit();
		}
		// Als een andere gebruiker het record ondertussen wijzigde, werpt JPA een RollbackException bij een commit van de transactie.
		catch (RollbackException ex)
		{
			// Als de method getCause van deze RollbackException een object van het type OptimisticLockException bevat,
			// is de oorzaak van de exception dat een andere gebruiker het record wijzigde. Als de method getCause een ander type object bevat, is de rollback veroorzaakt door een andere fout in de database.
			if (ex.getCause() instanceof OptimisticLockException)
			{
				throw new RecordAangepastException();
			}
		}
	}	
	
//	public Iterable<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot)
//	{
//		return docentDAO.findByWeddeBetween(van, tot);
//	}
	
	// Je geeft de verzameling als List terug: je hebt in de servlet ook het aantal docenten nodig
	public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot, int vanafRij, int aantalRijen)
	{
		return docentDAO.findByWeddeBetween(van,tot, vanafRij,aantalRijen);
	}
	
//	public Iterable<String> findVoornamen()
//	{
//		return docentDAO.findVoornamen();
//	}
	
	public Iterable<VoornaamEnId> findVoornamen()
	{
		return docentDAO.findVoornamen();
	}
	
	public BigDecimal findMaxWedde()
	{
		return docentDAO.findMaxWedde();
	}
	
	public Iterable<AantalDocentenPerWedde> findAantalDocentenPerWedde()
	{
		return docentDAO.findAantalDocentenPerWedde();
	}

	public void algemeneOpslag(BigDecimal percentage)
	{
		BigDecimal factor = BigDecimal.ONE.add(percentage.divide(new BigDecimal(100)));
		
		docentDAO.beginTransaction();
		docentDAO.algemeneOpslag(factor);
		docentDAO.commit();
	}	

	public void bijnaamToevoegen(long id, String bijnaam)
	{
		docentDAO.beginTransaction();
		docentDAO.read(id).addBijnaam(bijnaam);
		docentDAO.commit();
	}

	public void bijnamenVerwijderen(long id, String[] bijnamen)
	{
		docentDAO.beginTransaction();
		Docent docent = docentDAO.read(id);
	
		for (String bijnaam : bijnamen)
		{
			docent.removeBijnaam(bijnaam);
		}
		
		docentDAO.commit();
	}
	
	public Iterable<Docent> findBestBetaaldeVanEenCampus(long id)
	{
		return docentDAO.findBestBetaaldeVanEenCampus(campusDAO.read(id));
	}
}
