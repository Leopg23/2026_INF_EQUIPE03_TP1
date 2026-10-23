package gestionnaireBibliotheque;

import java.util.ArrayList;
import java.util.HashMap;
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


    // AJOUT : constructeur qui initialise les attributs (évite les NullPointerException)
    public GestionnaireBibliotheque() {
        utilisateurs = new HashMap<>();
        utilisateursAuthentifies = new HashMap<>();
        livres = new ListesLivres(new ArrayList<>());
    }


    // Point d'entrée : redirige chaque commande vers la bonne méthode
    public String traiterCommande(int idConnexion, String typeCommande, String argument) {
        this.idConnection = idConnexion;
        this.typeCommande = typeCommande;
        this.argument = argument;

        if (typeCommande == null) {
            return "BAD_COMMAND_ERROR"; // à adapter selon l'énoncé
        }

        if (typeCommande.equalsIgnoreCase("ID")) {
            return traiterID(idConnexion, argument);
        } else if (typeCommande.equalsIgnoreCase("BOOKS")) {
            return traiterBOOKS();
        } else if (typeCommande.equalsIgnoreCase("BORROW")) {
            return traiterBORROW(idConnexion, argument);
        }else if (typeCommande.equalsIgnoreCase("RETURN")) {
            return traiterRETURN(idConnexion, argument);
        }else if (typeCommande.equalsIgnoreCase("RESERVE")) {
            return traiterRETURN(idConnexion, argument);
        }

        return "BAD_COMMAND_ERROR"; // à adapter selon l'énoncé
    }


    // Exercice 1 : commande ID <id utilisateur>
    public String traiterID(int idConnexion, String argument) {
        // 1. L'identifiant est valide ?
        int idUtilisateur;
        try {
            idUtilisateur = Integer.parseInt(argument.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return "BAD_ARGUMENT_ERROR";
        }

        // 2. L'utilisateur existe ?
        Utilisateur utilisateur = utilisateurs.get(idUtilisateur);
        if (utilisateur == null) {
            return "BAD_ARGUMENT_ERROR";
        }

        // 3. La connexion courante est authentifiée ?
        if (utilisateursAuthentifies.containsKey(idConnexion)) {
            return "ALREADY_AUTHENTIFIED_ERROR";
        }

        // 4. L'utilisateur est connecté depuis un autre client ?
        if (utilisateursAuthentifies.containsValue(utilisateur)) {
            return "TOO_MANY_CONNECTIONS_ERROR";
        }

        // 5. Tout est good
        utilisateursAuthentifies.put(idConnexion, utilisateur);
        return "AUTHORIZED " + utilisateur.getId() + " " + utilisateur.getNom();
    }


    // Exercice 2 : commande BOOKS
    public String traiterBOOKS() {
        StringBuilder reponse = new StringBuilder("BOOKS ");
        reponse.append(livres.taille());

        for (Livre livre : livres) {
            reponse.append(" {")
                    .append(livre.getId()).append(" ")
                    .append(livre.getTitre()).append(" ")
                    .append(livre.getAuteur()).append(" ")
                    .append(livre.getCategorie()).append(" ")
                    .append(livre.getStatut())
                    .append("}");
        }
        return reponse.toString();
    }


    // Exercice 3 : commande BORROW <id livre> <jour>
    public String traiterBORROW(int idConnexion, String argument) {
        // 1. L'utilisateur est authentifié sur cette connexion ?
        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if (utilisateur == null) {
            return "AUTHENTICATION_ERROR";
        }

        // 2. Les arguments sont valides ? (2 nombres)
        if (argument == null) {
            return "BAD_ARGUMENT_ERROR";
        }
        String[] parties = argument.trim().split("\\s+");
        if (parties.length != 2) {
            return "BAD_ARGUMENT_ERROR";
        }
        int idLivre;
        int jour;
        try {
            idLivre = Integer.parseInt(parties[0]);
            jour = Integer.parseInt(parties[1]);
        } catch (NumberFormatException e) {
            return "BAD_ARGUMENT_ERROR";
        }
        if (jour < 0) {
            return "BAD_ARGUMENT_ERROR";
        }

        // 3. Le livre existe ?
        Livre livre = livres.rechercher(idLivre);
        if (livre == null) {
            return "BOOK_NOT_FOUND_ERROR";
        }

        // 4. Le livre est disponible ?
        if (livre.getStatut() != StatutLivre.DISPONIBLE) {
            return "BOOK_NOT_AVAILABLE_ERROR";
        }

        // 5. L'utilisateur est à sa limite ?
        if (!utilisateur.peutEmprunter()) {
            return "BORROW_LIMIT_ERROR";
        }

        // 6. Tout est good
        Emprunt emprunt = new Emprunt(livre, utilisateur.getId(), jour);
        utilisateur.ajouterEmpruntEnCours(emprunt);
        livre.setStatut(StatutLivre.EMPRUNTE);

        return "BORROW_OK " + emprunt.getId() + " " + livre.getId() + " " + emprunt.getJourRetourPrevu();
    }

    public String traiterRETURN(int idConnexion, String argument){
        // 1. L'utilisateur est authentifié sur cette connexion ?
        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if (utilisateur == null) {
            return "AUTHENTICATION_ERROR";
        }

        // 2. Les arguments sont valides ? (2 nombres)
        if (argument == null) {
            return "BAD_ARGUMENT_ERROR";
        }
        String[] parties = argument.trim().split("\\s+");
        if (parties.length != 2) {
            return "BAD_ARGUMENT_ERROR";
        }
        int idLivre;
        int jour;
        try {
            idLivre = Integer.parseInt(parties[0]);
            jour = Integer.parseInt(parties[1]);
        } catch (NumberFormatException e) {
            return "BAD_ARGUMENT_ERROR";
        }

        if (jour < 0) {
            return "BAD_ARGUMENT_ERROR";
        }

        // 3. Verifier que l'emprunt est dans les emprunts en cours de l'utilisateur
        Emprunt emprunt = utilisateur.rechercherEmpruntEnCours(idLivre);
        if (emprunt == null) {
            return "BAD_BORROW_ERROR";
        }


        // 4. Tout est good
        Livre livre = livres.rechercher(idLivre);
        utilisateur.supprimerEmpruntEnCours(idLivre);
        utilisateur.ajouterEmpruntTermine(emprunt);
        livre.setStatut(StatutLivre.DISPONIBLE);


        return "BORROW_OK " + emprunt.getId() + " " + livre.getId() + " " + emprunt.getJourRetourPrevu();
    }
    public String traiterRESERVE(int idConnexion, String argument){
        // 1. L'utilisateur est authentifié sur cette connexion ?
        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if (utilisateur == null) {
            return "AUTHENTICATION_ERROR";
        }

        // 2. L'identifiant est valide ?
        int idLivre;
        try {
            idLivre = Integer.parseInt(argument.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return "BAD_ARGUMENT_ERROR";
        }

        // 3. Le livre existe ?
        Livre livre = livres.rechercher(idLivre);
        if (livre == null) {
            return "BOOK_NOT_FOUND_ERROR";
        }

        //4. Le livre est reserve
        if(livre.getStatut().equals(StatutLivre.EMPRUNTE)){
            return "BOOK_ALREADY_AVAILABLE_ERROR";
        }

        //5. Tout est good
        Reservation reservation = new Reservation(livre,idConnexion,utilisateur.getTypeUtilisateur());
        reservationsEnAttente.ajouter(reservation);
        livre.setStatut(StatutLivre.RESERVE);
        return "RESERVATION_OK";
    }
    public String traiterINFO(int idConnexion, String argument){
        // 1. L'utilisateur est authentifié sur cette connexion ?
        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if (utilisateur == null) {
            return "AUTHENTICATION_ERROR";
        }
        // 2. Verifier si l'utilisateur a entree un argument
        if(argument!=null){
            int idEmprunt;
            try {
                idEmprunt = Integer.parseInt(argument.trim());
            } catch (NumberFormatException | NullPointerException e) {
                return "BAD_ARGUMENT_ERROR";
            }
            if(!utilisateur.getEmpruntsEnCours().contient(idEmprunt)){
                return "BAD_BORROW_ERROR";
            }
            else {
                return utilisateur.getEmpruntsEnCours().rechercher(idEmprunt).toString();
            }
        }

        return utilisateur.getEmpruntsEnCours().toString();
    }
}