package be.vdab.dao;

import be.vdab.entities.Cursus;


public class CursusDAO extends AbstractDAO
{
	public Iterable<Cursus> findByNaamContains(String woord)
	{
		return getEntityManager().createNamedQuery("Cursus.findByNaamContains",	Cursus.class)
			.setParameter("zoals", '%' + woord + '%')
			.getResultList();
	}
}
