package gestionnaireBibliotheque;
import java.util.PriorityQueue;

public class FilePrioriteReservations {
    private PriorityQueue<Reservation> file;

    public FilePrioriteReservations(PriorityQueue<Reservation> file) {
        this.file = new PriorityQueue<>();
    }





    public boolean ajouter(Reservation reservation) {
        return this.file.add(reservation);
    }
    public Reservation retirer() {
        return this.file.poll();
    }
    public Reservation consulter(){
        return this.file.peek();
    }
    public boolean estVide(){
        return this.file.isEmpty();
    }
    public int taille(){
        return this.file.size();
    }
    public Reservation retirerPourLivre(int idLivre){
        for(Reservation reservation : this.file){
            if(reservation.getLivre().getId() == idLivre){
                this.file.remove(reservation);
                return reservation;
            }
        }
        return null;
    }

    //Overrides
    @Override
    public String toString() {
        return file.toString();
    }


    //getter, setters

    public PriorityQueue<Reservation> getFile() {
        return file;
    }
}
