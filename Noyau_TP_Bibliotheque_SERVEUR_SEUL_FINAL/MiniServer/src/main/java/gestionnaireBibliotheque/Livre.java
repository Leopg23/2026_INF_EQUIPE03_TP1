package gestionnaireBibliotheque;

import java.util.Objects;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String categorie;
    private StatutLivre statut;



    //enum  StatutLivre {
    //    DISPONIBLE
   //}
    public static final int DUREE_MAX_EMPRUNT = 40;


    private static int compteurId = 0;

    public static int prochainID() {
        return ++ compteurId;
    }

    public Livre(String titre, String auteur, String categorie ) {
        this.id = prochainID();
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.statut = StatutLivre.DISPONIBLE;
    }


    @Override
    public String toString() {
        return "Livre [id= " + id + " ] Titre= " + titre + ",  auteur= " + auteur + ", categorie= " + categorie + ", statut= " + statut ;
    }
    public boolean equals(Object obj) {
        if ( this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Livre)) {
            return false;
        }
        Livre autre =  (Livre) obj;
        return  this.id==autre.id;
    }

    //getter-setters

    public int getId() {

        return id;
    }
    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getCategorie() {
        return categorie;
    }

    public StatutLivre getStatut() {
        return statut;
    }
    public void setStatut(StatutLivre statut) {
        this.statut = statut;
    }

}


