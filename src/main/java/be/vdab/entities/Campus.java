package be.vdab.entities;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import be.vdab.valueobjects.Adres;

@Entity
@Table(name="campussen")
public class Campus implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private String naam;
	// Je tikt @Embedded voor een variabele met als type een value object class.
	@Embedded
	private Adres adres;
	
	public Campus(String naam, Adres adres)
	{
		setNaam(naam);
		setAdres(adres);
	}
	
	protected Campus() {}

	//je maakt getters en setters voor de niet-static private variabelen public Campus(String naam, Adres adres) {
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getNaam()
	{
		return naam;
	}

	public void setNaam(String naam)
	{
		this.naam = naam;
	}

	public Adres getAdres()
	{
		return adres;
	}

	public void setAdres(Adres adres)
	{
		this.adres = adres;
	}
}
