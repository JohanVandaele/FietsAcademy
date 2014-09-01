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

// -- Table per class hierarchy --
// Er is één table voor de volledige class inheritance hierarchy.
// Deze table bevat kolommen voor alle attributen van alle classes van de hierarchy.
// cursussen: id, naam, van, tot, duurtijd, soort('G"/'I')
//@Inheritance  (strategy=InheritanceType.SINGLE_TABLE)

//-- Table per subclass --
//De database bevat een table per class uit de inheritance hierarchy.
//Elke table bevat enkel kolommen voor de attributen in de bijbehorende class.
// cursussen: id, naam - groepscursussen: id, van, tot - individuelecursussen: id, duurtijd
//@Inheritance  (strategy=InheritanceType.JOINED)

// -- Table per concrete class --
// De database bevat enkel tables voor de laagste classes uit de inheritance hiërarchy
// (classes waarvan geen andere classes erven).
// groepscursussen: id, naam, van tot - individuelecursussen: id, naam, duurtijd
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
