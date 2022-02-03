public class Kullanici extends Oyuncu {

    public Kullanici() {
    }

    public Kullanici(int oyuncuID, String oyuncuAdi, int skor) {
        super.oyuncuID = oyuncuID;
        super.oyuncuAdi = oyuncuAdi;
        super.skor = skor;
    }

    @Override
    int kartSec(Sporcu[] liste, int index) {
        super.kartSec(liste, index);
        return 0;
    }
}
