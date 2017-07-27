/*
Author: Jacopo Freddi
Project: ExAltered
Description: Interactive Character Sheet for a heavily-altered version of
  the  role-playing game Exalted, 2nd Edition, by White Wolf Publishing.

Copyright (C) 2014 - 2017 Jacopo Freddi

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

© 2017 White Wolf Entertainment AB. All rights reserved.
Exalted® and Storytelling System™ are trademarks and/or
registered trademarks of White Wolf Entertainment AB.

All rights reserved. www.white-wolf.com
*/

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
