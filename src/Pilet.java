public class Pilet implements Comparable<Pilet> {
    private int koha_nr;
    private boolean myydud = false;

    public int getKoha_nr() {
        return koha_nr;
    }

    public void setKoha_nr(int koha_nr) {
        this.koha_nr = koha_nr;
    }

    public boolean isMyydud() {
        return myydud;
    }

    public void setMyydud(boolean myydud) {
        this.myydud = myydud;
    }

    public Pilet(int koht) {
        this.koha_nr = koht;
    }

    @Override
    public String toString() {
        return "Pilet{" +
                "koha_nr=" + koha_nr +
                ", myydud=" + myydud +
                '}';
    }

    @Override
    public int compareTo(Pilet o) {
        if(koha_nr < o.getKoha_nr()) {
            return -1;
        }
        if(koha_nr > o.getKoha_nr()) {
            return 1;
        }
        return 0;
    }
}
