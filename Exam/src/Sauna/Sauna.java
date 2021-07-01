package Sauna;

public class Sauna {

    private int anzahlMaenner = 0;
    private int anzahlFrauen = 0;

    public synchronized void femaleEnter() {
        while (anzahlMaenner > 0) {
            try {
                this.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            anzahlFrauen++;
        }
    }

    public synchronized void femaleLeave() {
        anzahlFrauen--;
        try {
            this.notifyAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void maleEnter() {
        while (anzahlFrauen > 0) {
            try {
                this.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            anzahlMaenner++;
        }
    }

    public synchronized void maleLeave() {
        anzahlMaenner--;
        try {
            this.notifyAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
