package it.darkfagio.metaexalted.utils;

public class Utils {

	public static String[] attribute = {
		"Forza", "Destrezza", "Costituzione",
		"Carisma", "Manipolazione", "Aspetto",
		"Intelligenza", "Percezione", "Prontezza"};
	
	public static String[] ability = {
		"Tiro con l'Arco", "Arti Marziali", "Mischia", "Lancio", "Guerra",
		"Coerenza", "Espressione", "Presenza", "Resistenza", "Sopravvivenza",
		"Mestiere", "Investigazione", "Conoscenze", "Medicina", "Occulto",
		"Atletica", "Consapevolezza", "Schivata", "Crimine", "Elusione",
		"Burocrazia", "Linguistica", "Cavalcare", "Navigare", "Socializzare"};
	
	public static String[] weaponStat = {
		"Peso", "Velocità", "Bonus Precisione Fisico", "Bonus Parata Fisico",
		"Bonus Precisione Mentale", "Bonus Parata Mentale", "Perforanza", "Bonus Perforanza",
		"Commozione", "Cadenza", "Durabilità", "Stazza"	};
	
	public static final String[] armorStat = {
		"Peso", "Impaccio", "Stazza", "Vestizione", "Durabilità"};
	

	public static int rollDie(int n, int soglia) {
		int r = 0;
		for (int i = 0; i < n; ++i) {
			int x = (int) Math.ceil(Math.random() * 10);
			if (x >= soglia) ++r;
			if (x == 10) ++r;
		}
		
		return r;
	}
	
	public static int rollDie(int n) {
		return rollDie(n, 7);
	}
	
	public static double dotProduct(double[] a, double[] b, int n) {
		double x = 0;
		
		for (int i = 0; i < n; ++i)
			x += a[i]*b[i];
		
		return x;
	}
	
	public static void log(String string) {
		System.out.println(string);
	}
	
}
