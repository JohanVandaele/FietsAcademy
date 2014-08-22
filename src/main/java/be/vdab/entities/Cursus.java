package be.vdab.entities;

import java.io.Serializable;

//import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity

// Je tikt @Inheritance bij de hoogste class in de inheritance hiërarchie.
// Je tikt bij strategy op welke manier je inheritance nabootst in de database.
// Je gebruikt bij “table per class hiërarchy” de waarde SINGLE_TABLE
//@Inheritance  (strategy=InheritanceType.SINGLE_TABLE)
//@Inheritance  (strategy=InheritanceType.JOINED)
@Inheritance  (strategy=InheritanceType.TABLE_PER_CLASS)

// Je tikt bij de hoogste class uit de inheritance hiërarchy de naam van de table die hoort bij de objecten uit de inheritance hiërarchie.
//@Table(name = "cursussen")

// Je tikt bij @DiscriminatorColumn de discriminator kolom.
//@DiscriminatorColumn(name = "Soort")

public abstract class Cursus implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	//@GeneratedValue
	private long id;
	private String naam;
	
	@Override
	public String toString()
	{
		return naam;
	}
}
