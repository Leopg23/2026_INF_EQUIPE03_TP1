package gestionnaireBibliotheque;

import java.security.PublicKey;
import java.util.Objects;
import java.util.PriorityQueue;

public abstract class  Utilisateur{
    private int id;
    private String nom;
    private ListeEmprunts empruntsEnCours =  new ListeEmprunts();
    private ListeEmprunts empruntsTermine = new ListeEmprunts();
    private FilePrioriteReservations reservations;
    public static final int MAX_RESERVATIONS = 10;

    public Utilisateur(int id,  String nom) {
        this.id = id;
        this.nom = nom;
        this.empruntsEnCours = new ListeEmprunts();
        this.reservations = new FilePrioriteReservations(new PriorityQueue<>());
        this.empruntsTermine = new ListeEmprunts();
    }

    public void ajouterEmpruntEnCours(Emprunt emprunt) {
    //add pour ajouter a l array list
        this.empruntsEnCours.ajouter(emprunt);

    }

    public void ajouterEmpruntTermine(Emprunt emprunt) {
        this.empruntsTermine.ajouter(emprunt);
    }

    public Emprunt supprimerEmpruntEnCours( int idEmprunt){
        return this.empruntsEnCours.supprimer(idEmprunt);
    }

    public Emprunt rechercherEmpruntEnCours(int idEmprunt){
         return this.empruntsEnCours.rechercher(idEmprunt);
    }

    public Emprunt rechercherEmpruntTermine(int idEmprunt){
        return this.empruntsTermine.rechercher(idEmprunt);
    }


    public boolean aDesEmpruntsEnCours(){
        if (this.empruntsEnCours.taille() >= 1){
            return true;
        }
        return false;
    }

    public abstract int nombreMaxEmprunts();

    public boolean peutEmprunter() {
        return this.empruntsEnCours.taille() < nombreMaxEmprunts();
    }
//6.12
    public boolean possedeReservationEnAttentePourLivre (int idLivre){
      for (Reservation res : this.reservations.getFile()){
          if (res.getLivre().getId() == idLivre){
              return true;
          }

      }
      return false;
    }

    public boolean ajouterReservation(Reservation reservation){
        if (this.reservations.taille()<MAX_RESERVATIONS){

            this.reservations.ajouter(reservation);
            return  true;
        }
        return false;
    }

    public abstract double calculerPenalite(int joursRetard);

    public abstract TypeUtilisateur getTypeUtilisateur();

    @Override
    public String toString() {
       return  "Utilisateur { "+
               "Type d'utilisateur : " + this.getTypeUtilisateur() +
               " Id d'utilisateur : " + id +
               " Nom : " + nom +
               " Nombre d'emprunt en cours : " + empruntsEnCours +
               " Nombre d'emprunts terminé : " + empruntsTermine +
               " Nombre de reservations en attente : " + reservations;
    }

    public int getId() {
        return this.id;
    }
    public String getNom() {
        return nom;
    }
}
