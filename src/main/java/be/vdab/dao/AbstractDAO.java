package be.vdab.dao;

import javax.persistence.EntityManager;

import be.vdab.filters.JPAFilter;

// enkele imports ...

abstract class AbstractDAO
{
	// Je kan deze protected method aanspreken vanuit DAO classes.
	protected EntityManager getEntityManager()
	{
		// Je leest de EntityManager van de huidige thread uit de ThreadLocal variabele van JPAFilter.
		return JPAFilter.getEntityManager();
	}

	public void beginTransaction()
	{
		getEntityManager().getTransaction().begin();
	}

	public void commit()
	{
		getEntityManager().getTransaction().commit();
	}

	public void rollback()
	{
		getEntityManager().getTransaction().rollback();
	}
}
