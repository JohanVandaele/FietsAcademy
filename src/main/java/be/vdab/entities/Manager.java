package be.vdab.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// enkele imports

@Entity
@Table(name = "managers")

public class Manager implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	
	private String voornaam;
	private String familienaam;
	
	// --------------
	// -- OneToOne --
	// --------------
	// Je tikt @OneToOne bij een variabele die een one-to-one associatie voorstelt.
	@OneToOne
		(
				// Ook deze one-to-one associatie is standaard eager: als je een Manager entity leest uit de database, leest JPA tegelijk de bijbehorende Campus entity.
				// Je kan overschakelen naar lazy loading door de parameter fetch op FetchType.LAZY te plaatsen.
				// Als je nu een Manager entity leest uit de database, leest JPA de bijbehorende Campus entity pas wanneer je de campus van de manager voor de eerste keer aanspreekt.
				fetch = FetchType.LAZY
				// Je voegt aan @OneToOne de parameter mappedBy toe. Je tikt hierbij de variabele naam (manager),
				// die aan de andere associatie kant (Campus) de associatie voorstelt.
				,mappedBy = "manager"
		)
	private Campus campus;

	// 
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getVoornaam()
	{
		return voornaam;
	}

	public void setVoornaam(String voornaam)
	{
		this.voornaam = voornaam;
	}

	public String getFamilienaam()
	{
		return familienaam;
	}

	public void setFamilienaam(String familienaam)
	{
		this.familienaam = familienaam;
	}

	public Campus getCampus()
	{
		return campus;
	}

	public void setCampus(Campus campus)
	{
		this.campus = campus;
	}
	
	// Je maakt getters voor id, voornaam, familienaam en campus
}
