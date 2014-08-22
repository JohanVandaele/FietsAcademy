package be.vdab.entities;

import java.util.Date;

//import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity

@Table(name = "groepscursussen")

// Je tikt bij @DiscriminatorValue de waarde in de discriminator kolom (Soort) als een record hoort bij een
// entity van de huidige entity class (GroepsCursus).
//@DiscriminatorValue("G")

public class GroepsCursus extends Cursus
{
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	private Date van;
	
	@Temporal(TemporalType.DATE)
	private Date tot;
}
