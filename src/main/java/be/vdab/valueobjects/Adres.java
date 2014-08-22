package be.vdab.valueobjects;

import java.io.Serializable;

import javax.persistence.Embeddable;

// Je tikt @Embeddable voor een value object class.
@Embeddable
public class Adres implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	// Als je geen JPA gebruikt, kan je bij private variabelen final tikken.
	// De compiler controleert dan dat je de variabele enkel bij zijn declaratie of in de constructor initialiseert.
	// JPA werkt echter niet samen met final variabelen.
	private String straat;
	private String huisNr;
	private String postcode;
	private String gemeente;
	
	// De constructor bevat per private variabele een parameter.
	public Adres(String straat, String huisNr, String postcode, String gemeente)
	{
		this.straat = straat;
		this.huisNr = huisNr;
		this.postcode = postcode;
		this.gemeente = gemeente;
	}

	// Een value object class moet bij JPA een default constructor hebben.
	protected Adres(){}

	// je maakt voor alle niet-static private variabelen getters, geen setters
	public String getStraat()
	{
		return straat;
	}

	public void setStraat(String straat)
	{
		this.straat = straat;
	}

	public String getHuisNr()
	{
		return huisNr;
	}

	public void setHuisNr(String huisNr)
	{
		this.huisNr = huisNr;
	}

	public String getPostcode()
	{
		return postcode;
	}

	public void setPostcode(String postcode)
	{
		this.postcode = postcode;
	}

	public String getGemeente()
	{
		return gemeente;
	}

	public void setGemeente(String gemeente)
	{
		this.gemeente = gemeente;
	}
}
