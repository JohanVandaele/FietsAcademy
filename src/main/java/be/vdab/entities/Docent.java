package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import org.hibernate.annotations.NamedQuery;
import be.vdab.enums.Geslacht;

//@NamedQuery
//	(	 name = "Docent.findByWeddeBetween"
//		,query = "select d from Docent d where d.wedde between :van and :tot order by d.wedde,d.id"
//	)

// enkele imports (vooral uit javax.persistence) ...

// Je tikt @Entity juist voor de entity class.
@Entity
// Je tikt @Table voor de entity class, met de table naam. Je mag @Table weglaten als de table naam gelijk is aan de class naam.
@Table(name = "docenten")
// JPA raadt aan dat de class Serializable implementeert.
// Dit is niet noodzakelijk voor de samenwerking met de database. Het is wel noodzakelijk als je objecten via serialization naar een binar bestand zou wegschrijven.
// We volgen in de cursus deze aanbeveling.
public class Docent implements Serializable
{
	private static final long serialVersionUID = 1L;

	// Je tikt @Id voor de private variabele die hoort bij de primary key kolom.
	@Id
	// Je tikt @GeneratedValue als de database de kolom zelf invult.
	// Dit is zo in de table docenten: id is een autonumber kolom.
	@GeneratedValue
	// JPA associeert een private variabele met een table kolom met dezelfde naam.
	// Je tikt @Column voor een private variabele als de naam van de bijbehorende table kolom verschilt van de naam van die private variabele.
	// Je tikt bij de parameter name de naam van de kolom: @Column(name = "kolomnaam")
	// @Column(name = "kolomnaam")
	private long id;

	private String voornaam;
	private String familienaam;
	private BigDecimal wedde;
	private long rijksRegisterNr;

	@Enumerated(EnumType.STRING)	// Je geeft zo aan dat bij de variabele een varchar kolom hoort
	private Geslacht geslacht;

	// Je tikt @Transient voor een private variabele als die private variabele geen bijbehorende kolom heeft in de database table.
	
	// Je tikt @Temporal voor een private Date variabele. Je geeft met een parameter het type aan van de bijbehorende table kolom.
	// @Temporal(TemporalType.DATE) Het kolomtype is Date (de kolom bevat enkel een datum)
	// @Temporal(TemporalType.TIME) Het kolomtype is Time (de kolom bevat enkel een tijd)
	// @Temporal(TemporalType.TIMESTAMP) Het kolomtype is DateTime (de kolom bevat een datum en een tijd)	

	// Je tikt @ElementCollection bij een variabele die een verzameling value objecten voorstelt.
	@ElementCollection
	// Je geeft met @CollectionTable de naam van de table (docentenbijnamen) aan die de value objecten bevat.
	@CollectionTable	(	 name = "docentenbijnamen" 
							// Je geeft met @JoinColumn een kolom in deze table aan.
							// Je kiest de foreign key kolom die verwijst naar primary kolom
							// in de table (docenten) die hoort bij de huidige entity class (Docent).
							// Je vult met deze @JoinColumn de parameter joinColumns van @CollectionTable.
							,joinColumns = @JoinColumn(name = "docentid"))
	@Column(name = "Bijnaam")
	private Set<String> bijnamen;

	// ------------
	// ManyToOne
	// ------------
	// Je tikt @ManyToOne bij een variabele die een many-to-one associatie voorstelt.
	// De foreign key kolom campusId, die bij deze associatie hoort, is in de database
	// gedefinieerd als verplicht in te vullen. Je plaatst dan de parameter optional op false.
	// JPA controleert dan voor het toevoegen of wijzigen van een record dat deze kolom wel
	// degelijk ingevuld wordt en werpt een exception als dit niet het geval is.
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	// De table docenten hoort bij de huidige class Docent.
	// Je duidt met @JoinColumn de kolom campusid in deze table aan.
	// Je kiest de foreign key kolom die verwijst naar de table campussen die hoort bij de geassocieerde entity (Campus).	
	@JoinColumn(name = "campusid")
	private Campus campus;	

	//
	public void opslag(BigDecimal percentage)
	{
		BigDecimal factor =	BigDecimal.ONE.add(percentage.divide(new BigDecimal(100)));
		wedde = wedde.multiply(factor, new MathContext(2, RoundingMode.HALF_UP));
	}

	// Properties
	public Campus getCampus()
	{
		return campus;
	}

//	public void setCampus(Campus campus)
//	{
//		this.campus = campus;
//	}

	public void setCampus(Campus campus)
	{
		if (this.campus != null && this.campus.getDocenten().contains(this))
		{
			// als de andere kant nog niet bijgewerkt is
			this.campus.removeDocent(this); // werk je de andere kant bij
		}

		this.campus = campus;
		
		if (campus != null && ! campus.getDocenten().contains(this))
		{
			// als de andere kant nog niet bijgewerkt is
			campus.addDocent(this); // werk je de andere kant bij
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

	public String getVoornaam()
	{
		return voornaam;
	}

	public String getFamilienaam()
	{
		return familienaam;
	}

	public BigDecimal getWedde()
	{
		return wedde;
	}

	public long getRijksRegisterNr()
	{
		return rijksRegisterNr;
	}

	public String getNaam()
	{
		return voornaam + ' ' + familienaam;
	}

	public String getGeslacht()
	{
		return geslacht.toString();
	}

	public Docent(String voornaam, String familienaam, BigDecimal wedde,Geslacht geslacht, long rijksRegisterNr)
	{
		setVoornaam(voornaam);
		setFamilienaam(familienaam);
		setWedde(wedde);
		setGeslacht(geslacht);
		setRijksRegisterNr(rijksRegisterNr);
	
		bijnamen = new HashSet<>();
		verantwoordelijkheden = new LinkedHashSet<>();
	}
	
	// default constructor is vereiste voor JPA
	protected Docent()
	{
	}

	// Bijnamen
	public void addBijnaam(String bijnaam)
	{
		bijnamen.add(bijnaam);
	}	
	
	public void removeBijnaam(String bijnaam)
	{
		bijnamen.remove(bijnaam);
	}

	//
	public Set<String> getBijnamen()
	{
		// Je kan per ongeluk de bijnamen toevoegen of verwijderen
		//return bijnamen;
		// Verhinderd per ongeluk de bijnamen toe tevoegen of te verwijderen : UnsupportedOperationException
		return Collections.unmodifiableSet(bijnamen);
	}	
	
	public static boolean isVoornaamValid(String voornaam)
	{
		return voornaam != null && ! voornaam.isEmpty();
	}
	
	public static boolean isFamilienaamValid(String familienaam)
	{
		return familienaam != null && ! familienaam.isEmpty();
	}
	
	public static boolean isWeddeValid(BigDecimal wedde)
	{
		return wedde != null && wedde.compareTo(BigDecimal.ZERO) >= 0;
	}
	
	public static boolean isRijksRegisterNrValid(long rijksRegisterNr)
	{
		long getal = rijksRegisterNr / 100;
		
		if (rijksRegisterNr / 1_000_000_000 < 50)
		{
			getal += 2_000_000_000;
		}

		//return rijksRegisterNr % 100 == 97 - getal % 97;
		return true;
	}
	
	public void setVoornaam(String voornaam)
	{
		if (! isVoornaamValid(voornaam))
		{
			throw new IllegalArgumentException();
		}
		
		this.voornaam = voornaam;
	}
	
	public void setFamilienaam(String familienaam)
	{
		if (! isFamilienaamValid(familienaam))
		{
			throw new IllegalArgumentException();
		}
		
		this.familienaam = familienaam;
	}
	
	public void setWedde(BigDecimal wedde)
	{
		if (! isWeddeValid(wedde))
		{
			throw new IllegalArgumentException();
		}
		
		this.wedde = wedde;
	}
	
	public void setGeslacht(Geslacht geslacht)
	{
		this.geslacht = geslacht;
	}
	
	public void setRijksRegisterNr(long rijksRegisterNr)
	{
		if (! isRijksRegisterNrValid(rijksRegisterNr))
		{
			throw new IllegalArgumentException();
		}
		
		this.rijksRegisterNr = rijksRegisterNr;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Docent))
		{
			return false;
		}
		
		return ((Docent)obj).rijksRegisterNr == rijksRegisterNr;
	}
	
	@Override
	public int hashCode()
	{
		return Long.valueOf(rijksRegisterNr).hashCode();
	}

	// ----------------
	// -- ManyToMany --
	// ----------------
	// Je tikt @ManyToMany bij een variabele die een many-to-many associatie voorstelt.
	// Je tikt bij de parameter mappedBy de variabele naam (docenten), die aan de andere associatie kant (Verantwoordelijkheid), de associatie voorstelt.
	@ManyToMany(mappedBy = "docenten")
	private Set<Verantwoordelijkheid> verantwoordelijkheden;

	public void addVerantwoordelijkheid(Verantwoordelijkheid verantwoordelijkheid)
	{
		verantwoordelijkheden.add(verantwoordelijkheid);
		
		if (!verantwoordelijkheid.getDocenten().contains(this))
		{
			verantwoordelijkheid.addDocent(this);
		}
	}

	public void removeVerantwoordelijkheid(Verantwoordelijkheid verantwoordelijkheid)
	{
		verantwoordelijkheden.remove(verantwoordelijkheid);
		
		if (verantwoordelijkheid.getDocenten().contains(this))
		{
			verantwoordelijkheid.removeDocent(this);
		}
	}

	public Set<Verantwoordelijkheid> getVerantwoordelijkheden()
	{
		return Collections.unmodifiableSet(verantwoordelijkheden);
	}
}
