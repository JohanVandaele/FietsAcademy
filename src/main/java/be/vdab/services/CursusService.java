package be.vdab.services;

import be.vdab.dao.CursusDAO;
import be.vdab.entities.Cursus;

public class CursusService
{
	private final CursusDAO cursusDAO = new CursusDAO();
		
	public Iterable<Cursus> findByNaamContains(String woord)
	{
		return cursusDAO.findByNaamContains(woord);
	}
}
