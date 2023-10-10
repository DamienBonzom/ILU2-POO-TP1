package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nb_etals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nb_etals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		StringBuilder chaine = new StringBuilder();
		if(chef == null) {throw new VillageSansChefException("Le village n'a pas de chef");}
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();

	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int indice_etal_libre = marche.trouverEtalLibre();
		chaine.append(String.format("%s cherche un endroit pour vendre %d %s.\n", vendeur.getNom(), nbProduit, produit));
		marche.utiliserEtal(indice_etal_libre, vendeur, produit, nbProduit);
		chaine.append(String.format("Le vendeur %s vend des %s � l'�tal n�%d.\n", vendeur.getNom(), produit, indice_etal_libre+1));
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etals_produit = marche.trouverEtals(produit);
		
		chaine.append(String.format("Les vendeurs qui proposent des %s sont :\n", produit));
		for(int i = 0; i < etals_produit.length; i++) {
			chaine.append(String.format("- %s\n", etals_produit[i].getVendeur().getNom()));
		}
		
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	};
	
	public String partirVendeur(Gaulois vendeur) {
		return rechercherEtal(vendeur).libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append(String.format("Le march� du village \"%s\" poss�de plusieurs �tals :\n", nom));
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
	
	private static class Marche{
		private Etal[] etals;
		private int nbEtals = 0;
		
		private Marche(int nb_etals) {
			this.etals = new Etal[nb_etals];
			this.nbEtals = nb_etals;
			for(int i = 0; i<nb_etals; i++) {
				this.etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			int estLibre = -1;
			int indice = 0;
			while(estLibre == -1 && indice < nbEtals) {
				if(!(etals[indice].isEtalOccupe())) {
					estLibre = indice;
				}else{
					indice += 1;
				}
			}
			
			return estLibre;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nb_etals_produit = 0;
			for(int i = 0; i < nbEtals ; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nb_etals_produit += 1;
				}
			}
			
			Etal[] etal_produit = new Etal[nb_etals_produit];
			int indice = 0;
			for(int i = 0; i < nbEtals ; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etal_produit[indice] = etals[i];
					indice += 1;
				}
			}
			
			return etal_produit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i < nbEtals; i++) {
				if(etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder affichage = new StringBuilder();
			int nb_etal_vide = 0;
			for(int i = 0; i < nbEtals; i++) {
				if(etals[i].isEtalOccupe()) {
					affichage.append(etals[i].afficherEtal());
				}else {
					nb_etal_vide += 1;
				}
			}
			if(nb_etal_vide < 2) {
				affichage.append(String.format("Il reste %d �tal non utilis� dans le march�.\n", nb_etal_vide));
			}else {
				affichage.append(String.format("Il reste %d �tals non utilis�s dans le march�.\n", nb_etal_vide));
			}
			
			return affichage.toString();
		}
		
	}
	
	
}