    package gestionnaireBibliotheque;

    import java.util.ArrayList;
    import java.util.Iterator;

    public class ListeEmprunts implements IListeEmprunts, Iterable<Emprunt> {

        private ArrayList<Emprunt> emprunts = new ArrayList<>();

        public ListeEmprunts() {
            this.emprunts = new ArrayList<>();
        }

        public boolean  ajouter(Emprunt emprunt) {
            emprunts.add(emprunt);
            return true;
        }

        public Emprunt supprimer (int idEmprunt) {
            for (Emprunt emprunt : emprunts) {
                if (idEmprunt == emprunt.getId()) {
                    emprunts.remove(emprunt);
                    return emprunt;
                }
            }
            return null;

        }

        public Emprunt rechercher(int idEmprunt){
            for (Emprunt emprunt : emprunts) {
                if (idEmprunt == emprunt.getId()) {
                    return emprunt;
                }
            }
            return null;
        }

        public boolean contient(int idEmprunt){
            for (Emprunt emprunt : emprunts) {
                if (idEmprunt == emprunt.getId()) {
                    return true;
                }
            }
            return false;
        }

        public int taille(){
            return emprunts.size();
        }
        public boolean estVide(){
            return emprunts.size() > 0;
        }

        public Iterator<Emprunt> iterator() {
            return emprunts.iterator();
        }

        @Override
        public String toString() {
            String res = "INFO " + emprunts.size();
            for (Emprunt emprunt : emprunts) {
                res += " " + emprunt.toString();
            }
            return res;
        }
    }
