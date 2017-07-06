package it.darkfagio.metaexalted.model;

public class Charm {
	
	private String name;
	private String description;
	public static final int
		ability = 0,
		minAbility = 1,
		minEssence = 2,
		essenceCost = 3,
		willpowerCost = 4,
		healthCost = 5,
		healthLevel = 6,
		limitCost = 7;
	
	private short[] feature;
	
	//TODO implementare i prodigi e come si interfacciano con il PG
	
	public Charm () {
		feature = new short[8];
	}

	public void fromString(String s) {
	}
	
	public static Charm factoryFromString(String s) {
		Charm c = new Charm();
		c.fromString(s);
		return c;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short get(int position) {
		return feature[position];
	}
	
	public void set(int index, short val) {
		feature[index] = val;
	}

}
