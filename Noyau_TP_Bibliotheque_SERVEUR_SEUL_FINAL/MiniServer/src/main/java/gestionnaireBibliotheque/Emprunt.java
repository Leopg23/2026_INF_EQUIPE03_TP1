package gestionnaireBibliotheque;
import java.util.Random;

public class Emprunt {
    int id;
    private Livre livre;
    int idUtilisateur;
    int jourEmprunt;
    int jourRetourPrevu;
    int jourRetour;
    private StatutEmprunt statut;

    enum StatutEmprunt {
        EN_COURS,
        EN_RETARD,
        RETOURNE
    }

    ;
    private static int compteurID = 0;

    private static final Random rand = new Random();

    public Emprunt(Livre livre, int idUtilisateur, int jourEmprunt) {
        this.id = prochainID();
        this.livre = livre;
        this.idUtilisateur = idUtilisateur;
        this.jourEmprunt = jourEmprunt;
        this.jourRetourPrevu = jourEmprunt + 40;
        this.statut = StatutEmprunt.EN_COURS;
    }

    public static int prochainID() {
        return ++compteurID;
    }

    public boolean estEnRetard(int jourActuel) {
        if (jourActuel > jourRetourPrevu) {
            return true;
        } else {
            return false;
        }
    }

    public int calculerJoursRetard(int jourActuel) {
        if (jourActuel > jourRetourPrevu) {
            return jourActuel - jourRetourPrevu;
        }
        return 0;
    }
    public void retourner(int jourRetour) {
        this.jourRetour = jourRetour;

        if (jourRetour <= this.jourRetourPrevu) {
            this.statut = StatutEmprunt.RETOURNE;
        } else {
            this.statut = StatutEmprunt.EN_RETARD;
        }
    }



    @Override
    public String toString() {
        return  "=======================================\n" +
                "|        DÉTAILS DE L'EMPRUNT         |\n" +
                "=======================================\n" +
                "| ID             : " + id + "\n" +
                "| Livre ID       : " + livre.getId() + "\n" +
                "| ID Utilisateur : " + idUtilisateur + "\n" +
                "| Jour Emprunt   : " + jourEmprunt + "\n" +
                "| Retour Prévu   : " + jourRetourPrevu + "\n" +
                "| Jour Retour    : " + (statut == StatutEmprunt.RETOURNE ? jourRetour : "Non retourné") + "\n" +
                "| Statut         : " + statut + "\n" +
                "=======================================";
    }
    public int getId() {
        return this.id;
    }
    public int getJourRetourPrevu() {
        return this.jourRetourPrevu;
    }
}
