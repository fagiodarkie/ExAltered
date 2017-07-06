package it.darkfagio.metaexalted.model;

public class Background {

	private String name;
	private String description;
	private short degree;
	
	public Background() {
		setName("");
		setDescription("");
		setDegree((short)0);
	}
	
	public static Background factoryFromString(String s) {
		Background b = new Background();
		b.fromString(s);
		return b;
	}

	public void fromString(String s) {
		String[] res = s.split("/");
		setName(res[0]);
		setDescription(res[1]);
		setDegree(Short.parseShort(res[2]));
	}
	
	public String toString() {
		return name + "/" + description + "/" + degree;
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

	public short getDegree() {
		return degree;
	}

	public void setDegree(short degree) {
		this.degree = degree;
	}
	
}
