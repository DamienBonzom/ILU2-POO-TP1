package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		
		Etal etal = new Etal();
		Gaulois damien = new Gaulois("damien", 100);
		etal.acheterProduit(2, null);
		System.out.println("Fin du test");

	}

}