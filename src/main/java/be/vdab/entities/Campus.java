package be.vdab.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

	// ----------------
	// -- OneToMany ---
	// ----------------
//	// Je tikt @OneToMany bij een variabele die een one-to-many associatie voorstelt.
//	@OneToMany
//	// Bij de variabele docenten hoort de table docenten.
//	// Je definieert met @JoinColumn de foreign key kolom (campusid) in die table die verwijst naar de primary key
//	// van de table (campussen), die hoort bij de huidige class (Campus).
//	@JoinColumn(name = "campusid")
//	// Je definieert met de optionele @OrderBy in welke volgorde JPA de Docent entities aan de many kant moet lezen uit de database.
//	// Je vermeldt de naam van één of meerdere private variabelen die horen bij de kolom waarop je wil sorteren.
//	@OrderBy("voornaam, familienaam")
//	private Set<Docent> docenten;

	// Je voegt aan @OneToMany de parameter mappedBy toe.
	// Je tikt daarbij de naam van de variabele (campus), die in de entity class aan de many kant van de associatie (Docent) de associatie voorstelt.
	@OneToMany(mappedBy = "campus")
	@OrderBy("voornaam, familienaam")
	private Set<Docent> docenten;	

	// --------------
	// -- OneToOne --
	// --------------
	// Je tikt @OneToOne bij een variabele die een one-to-one associatie voorstelt.
	// Een one-to-one associatie is standaard eager: als je een Campus entity leestuit de database, leest JPA tegelijk de bijbehorende Manager entity.
	// Je kan overschakelen naar lazy loading door de parameter fetch op FetchType.LAZY te plaatsen.
	// Als je nu een Campus entity leest uit de database, leest JPA de bijbehorende Manager pas wanneer je de manager van de campus voor de eerste keer aanspreekt.
	@OneToOne(fetch = FetchType.LAZY)
	// De table campussen hoort bij de huidige entity (Campus). Je definieert met @JoinColumn de naam van de kolom in deze table
	// die de foreign key bevat die verwijst naar de table (managers) die hoort bij de geassocieerde entity (Manager).
	@JoinColumn(name = "managerid")
	private Manager manager;
	
	public Manager getManager()
	{
		return manager;
	}	
	
	//
	public Campus(String naam, Adres adres)
	{
		setNaam(naam);
		setAdres(adres);
		telefoonNrs = new LinkedHashSet<>();
		docenten = new LinkedHashSet<>();
	}
	
	protected Campus() {}

	//je maakt getters en setters voor de niet-static private variabelen public Campus(String naam, Adres adres) {
	public Set<Docent> getDocenten()
	{
		return docenten;
	}

	public void setDocenten(Set<Docent> docenten)
	{
		this.docenten = docenten;
	}

//	public void addDocent(Docent docent)
//	{
//		docenten.add(docent);
//	}
//
//	public void removeDocent(Docent docent)
//	{
//		docenten.remove(docent);
//	}

	public void addDocent(Docent docent)
	{
		docenten.add(docent);
		
		if (docent.getCampus() != this)
		{ // als de andere kant nog niet bijgewerkt is
			docent.setCampus(this); // werk je de andere kant bij
		}
	}	

	public void removeDocent(Docent docent)
	{
		docenten.remove(docent);
		
		if (docent.getCampus() == this)
		{ // als de andere kant nog niet bijgewerkt is
			docent.setCampus(null); // werk je de andere kant bij
		}
	}	
	
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
