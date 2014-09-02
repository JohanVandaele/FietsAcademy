package be.vdab.dao;

//import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.NoResultException;

import be.vdab.entities.Docent;
import be.vdab.valueobjects.AantalDocentenPerWedde;
//import be.vdab.filters.JPAFilter;
import be.vdab.valueobjects.VoornaamEnId;

// enkele imports ...

public class DocentDAO extends AbstractDAO
{
//	public Docent read(long id)
//	{
//		// Je vraagt een EntityManager aan de servlet filter JPAFilter.
//		EntityManager entityManager = JPAFilter.getEntityManager();
//
//		try
//		{
//			// Je zoekt een Docent entity op de primary key met de find method.
//			// De 1° parameter is het type van de op te zoeken entity
//			// De 2° parameter is de primary key waarde van de op te zoeken entity
//			return entityManager.find(Docent.class, id);
//		}
//		finally
//		{
//			entityManager.close();
//		}
//	}

//	public Docent read(long id, EntityManager entityManager)
	public Docent read(long id)
	{
		//return entityManager.find(Docent.class, id);
		return getEntityManager().find(Docent.class, id);
	}
	
//	public void create(Docent docent, EntityManager entityManager)
	public void create(Docent docent)
	{
		//entityManager.persist(docent);
		getEntityManager().persist(docent);
	}	

//	public void delete(long id, EntityManager entityManager)
	public void delete(long id)
	{
		//Docent docent = entityManager.find(Docent.class, id);
		Docent docent = getEntityManager().find(Docent.class, id);
		
		if (docent != null)
		{
			//entityManager.remove(docent);
			getEntityManager().remove(docent);
		}
	}
	
//	public Iterable<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot,int vanafRij, int aantalRijen)
	// Je geeft de verzameling als List terug: je hebt in de servlet ook het aantal docenten nodig
	public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot,int vanafRij, int aantalRijen)
	{
		// je gebruikt van en tot later ...
		//return getEntityManager().createQuery("select d from Docent d", Docent.class).getResultList();
		//return getEntityManager().createQuery("select d from Docent d order by d.wedde desc, d.voornaam, d.familienaam", Docent.class).getResultList();
		//return getEntityManager().createQuery("select d from Docent d where d.wedde between 2000 and 2200 order by d.wedde desc, d.voornaam, d.familienaam", Docent.class).getResultList();
		
//		return getEntityManager().createQuery("select d from Docent d where d.wedde between ? and ? order by d.wedde,d.id", Docent.class)
//				.setParameter(1, van)
//				.setParameter(2, tot)
//				.getResultList();
		
//		return getEntityManager().createQuery("select d from Docent d where d.wedde between :van and :tot order by d.wedde,d.id", Docent.class)
//				.setParameter("van", van)
//				.setParameter("tot", tot)
//				.getResultList();
		
//		return getEntityManager().createQuery("select d from Docent d where d.wedde between :van and :tot order by d.wedde,d.id", Docent.class)
//				.setParameter("van", van)
//				.setParameter("tot", tot)
//				.setFirstResult(vanafRij)
//				.setMaxResults(aantalRijen).getResultList();

		return getEntityManager().createNamedQuery("Docent.findByWeddeBetween", Docent.class)
				.setParameter("van", van)
				.setParameter("tot", tot)
				.setFirstResult(vanafRij)
				.setMaxResults(aantalRijen).getResultList();
	}
	
//	public Iterable<String> findVoornamen()
//	{
//		return getEntityManager().createQuery("select d.voornaam from Docent d",String.class).getResultList();
//	}	
	
	public Iterable<VoornaamEnId> findVoornamen()
	{
		return getEntityManager().createQuery("select new be.vdab.valueobjects.VoornaamEnId(d.id, d.voornaam) from Docent d",VoornaamEnId.class).getResultList();
	}
	
	public BigDecimal findMaxWedde()
	{
		return getEntityManager().createQuery("select max(d.wedde) from Docent d", BigDecimal.class).getSingleResult();
	}

	public Iterable<AantalDocentenPerWedde> findAantalDocentenPerWedde()
	{
		return getEntityManager().createQuery(
			"select new be.vdab.valueobjects.AantalDocentenPerWedde(d.wedde,count(d)) from Docent d group by d.wedde",
			AantalDocentenPerWedde.class).getResultList();
		}	

	public void algemeneOpslag(BigDecimal factor)
	{
		getEntityManager().createNamedQuery("Docent.algemeneOpslag")
			.setParameter("factor", factor)
			.executeUpdate();
	}
	
	public Docent findByRijksRegisterNr(long rijksRegisterNr)
	{
		try
		{
			return getEntityManager()
				.createNamedQuery("Docent.findByRijksRegisterNr", Docent.class)
				.setParameter("rijksRegisterNr", rijksRegisterNr)
				.getSingleResult();
		}
		catch (NoResultException ex)
		{
			// Als de method getSingleResult geen record vindt, werpt ze een NoResultException. Je vangt deze fout op en je geeft null terug.
			return null;
		}
	}
}
