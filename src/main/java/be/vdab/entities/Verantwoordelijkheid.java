package be.vdab.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

// enkele imports ...

@Entity
@Table(name = "verantwoordelijkheden")
public class Verantwoordelijkheid implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id; // je maakt zelf een getter
	
	private String naam; // je maakt zelf een getter Docent<<entity>>Verantwoordelijkheid<<entity>>**
	
	public long getId()
	{
		return id;
	}

//	public void setId(long id)
//	{
//		this.id = id;
//	}

	public String getNaam()
	{
		return naam;
	}

//	public void setNaam(String naam)
//	{
//		this.naam = naam;
//	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Verantwoordelijkheid))
		{
			return false;
		}
		return ((Verantwoordelijkheid)obj).naam.equalsIgnoreCase(this.naam);
	}

	@Override
	public int hashCode()
	{
		return naam.toUpperCase().hashCode();
	}
	
	// ----------------
	// -- ManyToMany --
	// ----------------
	// Je tikt @ManyToMany bij een variabele die een many-to-many associatie voorstelt.
	@ManyToMany
	// Je definieert met @JoinTable de tussentable die hoort bij associatie.
	@JoinTable
			// Je tikt bij name de naam van de tussentable.
		(	 name = "docentenverantwoordelijkheden"
			// Je tikt bij joinColumns de naam van de kolom in de tussentable die de foreign key is naar de primary key van de
			// table (verantwoordelijkheden) die hoort bij de huidige entity (Verantwoordelijkheid).
			// Je vult joinColumns met een @JoinColumn.
			,joinColumns = @JoinColumn(name = "verantwoordelijkheidid")
			// Je tikt bij inverseJoinColumns de kolomnaam in de tussentable die de foreign key is naar de primary key van
			// de table (docenten) die hoort bij de entity aan de andere associatie kant (Docent). Je vult inverseJoinColumns met een @JoinColumn.
			,inverseJoinColumns = @JoinColumn(name = "docentid")
		)
	private Set<Docent> docenten;
	
	// --------
	// Docenten
	// --------
	public void addDocent(Docent docent)
	{
		docenten.add(docent);
		
		if (! docent.getVerantwoordelijkheden().contains(this))
		{
			docent.addVerantwoordelijkheid(this);
		}
	}

	public void removeDocent(Docent docent)
	{
		docenten.remove(docent);
		
		if (docent.getVerantwoordelijkheden().contains(this))
		{
			docent.removeVerantwoordelijkheid(this);
		}
	}
	
	public Set<Docent> getDocenten()
	{
		return Collections.unmodifiableSet(docenten);
	}
}

		