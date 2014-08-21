package be.vdab.dao;

//import javax.persistence.EntityManager;

import java.math.BigDecimal;

import be.vdab.entities.Docent;
//import be.vdab.filters.JPAFilter;

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
	
	public Iterable<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot)
	{
		// je gebruikt van en tot later ...
		//return getEntityManager().createQuery("select d from Docent d", Docent.class).getResultList();
		//return getEntityManager().createQuery("select d from Docent d order by d.wedde desc, d.voornaam, d.familienaam", Docent.class).getResultList();
		//return getEntityManager().createQuery("select d from Docent d where d.wedde between 2000 and 2200 order by d.wedde desc, d.voornaam, d.familienaam", Docent.class).getResultList();
		
//		return getEntityManager().createQuery("select d from Docent d where d.wedde between ? and ? order by d.wedde,d.id", Docent.class)
//				.setParameter(1, van)
//				.setParameter(2, tot)
//				.getResultList();
		
		return getEntityManager().createQuery("select d from Docent d where d.wedde between :van and :tot order by d.wedde,d.id", Docent.class)
				.setParameter("van", van)
				.setParameter("tot", tot)
				.getResultList();
	}
}
