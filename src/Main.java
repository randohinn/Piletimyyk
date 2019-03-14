import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    private static List<Pilet> piletid = new ArrayList<Pilet>();

    public static void main(String[] args){
        for (int i = 0; i < 30; i++) {
            // nr 0 kohaga bussi pole veel nähtud
            piletid.add(new Pilet(i+1));
        }
        while(true) {
            int vaja = 0;
            try{
                vaja = Integer.parseInt(JOptionPane.showInputDialog(null, "Mitut piletit soovid osta?", "Mitut piletit soovid osta?", JOptionPane.INFORMATION_MESSAGE));
                if(vaja == 0) {
                    continue;
                }
                int myydud_arv = 0;
                for (Pilet pilet : piletid) {
                    if(pilet.isMyydud()) {
                        myydud_arv++;
                    }
                }
                if(piletid.size() - myydud_arv < vaja) {
                    JOptionPane.showMessageDialog(null, "Nii palju pileteid ei ole saada!");
                    System.exit(0);
                }
            } catch (Exception e) {
                System.exit(0);
            }
            List<Pilet> myydavad = pakuMyygiks(vaja);
            // Kinnitamiseks võime kuvada vaid kohanumbrid
            String kohanumbrid = "";
            for (Pilet pilet : myydavad) {
                kohanumbrid += ", "+pilet.getKoha_nr();
                kohanumbrid = kohanumbrid.replaceFirst("^, ", "");
            }
            int on_ok = JOptionPane.showConfirmDialog(null, "Müügiks on pakkuda piletid kohanumbritega: "+kohanumbrid +". Kas olete nõus?","Tehingu kinnitamine", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(on_ok == 0) { // Miks yes == 0 ?!?
                for (Pilet pilet : piletid) {
                    if(myydavad.contains(pilet)) {
                        pilet.setMyydud(true);
                    }
                }
            }
        }
    }

    private static List<Pilet> pakuMyygiks(int arv) {
        Random rand = new Random();
        List<Pilet> result = new ArrayList<Pilet>();
        while(result.size() < arv) {
            int indeks = rand.nextInt(piletid.size());
            Pilet pilet = piletid.get(indeks);
            if(!result.contains(pilet) && !pilet.isMyydud()) {
                result.add(pilet);
            }
        }
        Collections.sort(result);
        return result;
    }
}
