package it.darkfagio.metaexalted.model;

public class ExperienceLogEntry {

	private short amount;
	private String description;
	
	public ExperienceLogEntry(short a, String d) {
		amount = a;
		description = d;
	}

	public ExperienceLogEntry() {	}

	public String toString() {
		return "" + amount + ":-:" + description;
	}
	
	public void fromString(String s) {
		if (s.equalsIgnoreCase("")) return;
		String[] r = s.trim().split(":-:");
		description = r[1];
		amount = Short.parseShort(r[0]);
	}
	
	public boolean isExpense() {
		return amount < 0;
	}
	
	public short getAmount() {
		return amount;
	}
	public void setAmount(short amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
