package gestionnaireBibliotheque;

import java.util.Map;

public class GestionnaireBibliotheque {
    private int idConnection;
    private String typeCommande;
    private String argument;
    //-------Attribut privé-------
    private Map<Integer, Utilisateur> utilisateurs;
    /* contient l’ensemble des utilisateurs enregistrés dans le système. La clé
représente l’identifiant de l’utilisateur, tandis que la valeur correspond à l’objet Utilisateur
associé.*/


    private Map<Integer, Utilisateur> utilisateursAuthentifies;
    /* contient les utilisateurs actuellement connectés au
système. La clé représente l’identifiant de connexion du client, tandis que la valeur correspond
à l’utilisateur authentifié sur cette connexion*/


    private ListesLivres livres;
    /*contient la liste des livres présents dans la bibliothèque. Cette structure
permettra notamment de rechercher, afficher, emprunter et réserver les livres.*/


    private FilePrioriteReservations reservationsEnAttente;
    /*contient l’ensemble des réservations actuellement en
attente dans la bibliothèque. Les réservations sont stockées dans une file de priorité afin de
respecter automatiquement l’ordre de priorité défini dans le système : professeur, personnel,
étudiant, puis ordre d’arrivée des réservations.*/


    public String traiterCommande(int idConnexion, String typeCommande, String argument){
        this.idConnection = idConnexion;
        this.typeCommande = typeCommande;
        this.argument = argument;

        if(typeCommande.equals("id")){
            traiterID(idConnexion, argument);
        } else if(typeCommande.equals("books")){

        }


        return "";
    };

    public String traiterID(int idConnexion, String argument){

        return null;
    };
}
