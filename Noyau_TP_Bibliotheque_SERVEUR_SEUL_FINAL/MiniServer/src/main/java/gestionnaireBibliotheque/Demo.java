package gestionnaireBibliotheque;

import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {

        GestionnaireBibliotheque gestionnaire = new GestionnaireBibliotheque();
        Scanner scanner = new Scanner(System.in);

        // 1. Pré-chargement
        gestionnaire.traiterCommande(0, "REGISTER", "101 ETUDIANT Amine");
        gestionnaire.traiterCommande(0, "REGISTER", "102 PROFESSEUR Sophie");
        gestionnaire.traiterCommande(0, "REGISTER", "103 PERSONNEL Marc");

        Livre livre1 = new Livre("Java: Comment programmer", "Deitel", "Informatique");
        Livre livre2 = new Livre("Algorithmes et structures de donnees", "Cormen", "Informatique");
        Livre livre3 = new Livre("Le Petit Prince", "Saint-Exupery", "Litterature");

        gestionnaire.getLivres().ajouter(livre1);
        gestionnaire.getLivres().ajouter(livre2);
        gestionnaire.getLivres().ajouter(livre3);


        int idConnexionCourante = 1;

        System.out.println("=== Console Bibliotheque prête ===");
        System.out.println("Exemples de commandes :");
        System.out.println("  REGISTER 12 ETUDIANT Leo");
        System.out.println("  ID 101");
        System.out.println("  BOOKS");
        System.out.println("  BORROW 1 5");
        System.out.println("  EXIT");
        System.out.println("-----------------------------------");

        boolean enExecution = true;

        while (enExecution) {
            System.out.print("> ");
            String ligne = scanner.nextLine().trim();


            if (ligne.isEmpty()) {
                continue;
            }

            String[] tokens = ligne.split("\\s+", 2);
            String commande = tokens[0];
            String argument = (tokens.length > 1) ? tokens[1] : "";


            String reponse = gestionnaire.traiterCommande(idConnexionCourante, commande, argument);
            System.out.println(reponse);


            if (commande.equalsIgnoreCase("EXIT") && "END".equals(reponse)) {
                enExecution = false;
            }
        }

        scanner.close();
        System.out.println("Session terminée.");
    }
}