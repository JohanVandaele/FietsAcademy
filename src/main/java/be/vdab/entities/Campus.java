package be.vdab.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import be.vdab.valueobjects.Adres;
import be.vdab.valueobjects.TelefoonNr;

@Entity
@Table(name="campussen")
public class Campus implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private String naam;
	
	// Je tikt @ElementCollection bij een variabele met een verzameling value objecten.
	@ElementCollection
	// Je geeft met @CollectionTable de naam van de table (campussentelefoonnrs) aan die de value objecten bevat.
	@CollectionTable
		(	 name = "campussentelefoonnrs"
			// Je geeft met @JoinColumn een kolom in deze table aan. Je kiest de foreign key kolom die verwijst naar primary kolom in de table (campussen) die hoort bij de huidige entity class (Campus). Je vult met deze @JoinColumn de parameter joinColumns van @CollectionTable.
			,joinColumns = @JoinColumn(name = "campusid")
		)
	// Je definieert met de optionele @OrderBy in welke volgorde JPA de value objecten moet lezen uit de database. Je vermeldt de naam van één of meerdere private variabelen (gescheiden door komma) die horen bij de kolom waarop je wil sorteren. Je kan omgekeerd sorteren met desc na een private variabele.
	@OrderBy("fax")
	private Set<TelefoonNr> telefoonNrs;
	
	// Je tikt @Embedded voor een variabele met als type een value object class.
	@Embedded
	private Adres adres;
	
	public Campus(String naam, Adres adres)
	{
		setNaam(naam);
		setAdres(adres);
		telefoonNrs = new LinkedHashSet<>();
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

	public Set<TelefoonNr> getTelefoonNrs()
	{
		return Collections.unmodifiableSet(telefoonNrs);
	}
	
	public void addTelefoonNr(TelefoonNr telefoonNr)
	{
		telefoonNrs.add(telefoonNr);
	}
	
	public void removeTelefoonNr(TelefoonNr telefoonNr)
	{
		telefoonNrs.remove(telefoonNr);
	}
}
