package be.vdab.valueobjects;

import java.io.Serializable;

import javax.persistence.Embeddable;

// enkele imports

@Embeddable
public class TelefoonNr implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String nummer;
	private boolean fax;
	private String opmerking;	
	
	public TelefoonNr(String nummer, boolean fax, String opmerking)
	{
		this.nummer = nummer;
		this.fax = fax;
		this.opmerking = opmerking; // je maakt getters voor nummer,fax,opmerking
	}

	protected TelefoonNr() {} // default constructor voor JPA
	
	@Override
	public String toString()
	{
		return nummer;
	}

	public String getNummer()
	{
		return nummer;
	}

	public void setNummer(String nummer)
	{
		this.nummer = nummer;
	}

	public boolean isFax()
	{
		return fax;
	}

	public void setFax(boolean fax)
	{
		this.fax = fax;
	}

	public String getOpmerking()
	{
		return opmerking;
	}

	public void setOpmerking(String opmerking)
	{
		this.opmerking = opmerking;
	}

	// Je stelt straks in de class Campus de telefoonnummers van een campus voor als een Set<TelefoonNr>.
	// Deze Set laat geen TelefoonNr objecten met hetzelfde nummer toe.
	// Je baseert daarom de equals method op het nummer.	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof TelefoonNr))
		{
			return false;
		}
		
		TelefoonNr telefoonNr = (TelefoonNr) obj;
		return nummer.equalsIgnoreCase(telefoonNr.nummer);
	}
	
	// Je baseert de method hashCode ook op het nummer.
	@Override
	public int hashCode()
	{
		return nummer.toUpperCase().hashCode();
	}
}
