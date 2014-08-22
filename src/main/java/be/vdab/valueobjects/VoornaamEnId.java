package be.vdab.valueobjects;

public class VoornaamEnId
{
	private final long id;
	private final String voornaam;
	// je maakt getters voor de private variabelen
	
	public VoornaamEnId(long id, String voornaam)
	{
		this.id = id;
		this.voornaam = voornaam;
	}

	public long getId()
	{
		return id;
	}

	public String getVoornaam()
	{
		return voornaam;
	}
	
//	public void setId(long id)
//	{
//		this.id=id;
//	}
//	
//	public void setId(String voornaam)
//	{
//		this.voornaam=voornaam;
//	}
}
