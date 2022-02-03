import java.util.Random;

public class Bilgisayar extends Oyuncu {

    public Bilgisayar() {
    }

    public Bilgisayar(int oyuncuID, String oyuncuAdi, int skor) {
        super.oyuncuID = oyuncuID;
        super.oyuncuAdi = oyuncuAdi;
        super.skor = skor;
    }

    @Override
    int kartSec(Sporcu[] liste, int index) {
        int i;
        Basketbolcu bkart;
        Futbolcu fkart;

        Random r = new Random();
        if (index < 4) {
            do {
                i = r.nextInt(4);
                bkart = (Basketbolcu) liste[i];
            } while (bkart.kartKullanildiMi);
        } else {
            do {
                i = 4 + r.nextInt(4);
                fkart = (Futbolcu) liste[i];
            } while (fkart.kartKullanildiMi);
        }
        return i;
    }

    boolean kartBittiMi() {
        for (int i = 0; i < kartListesi.length; i++) {
            if (i < 4) {
                if (!((Basketbolcu) kartListesi[i]).kartKullanildiMi) {
                    return false;
                }
            } else {
                if (!((Futbolcu) kartListesi[i]).kartKullanildiMi) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean bkartBittiMi() {
        for (int i = 0; i < 4; i++) {
            if (!((Basketbolcu) kartListesi[i]).kartKullanildiMi) {
                return false;
            }
        }
        return true;
    }

    boolean fkartBittiMi() {
        for (int i = 4; i < 8; i++) {
            if (!((Futbolcu) kartListesi[i]).kartKullanildiMi) {
                return false;
            }
        }
        return true;
    }
}
