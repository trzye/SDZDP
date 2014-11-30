import kmip.Kmip;
import przesylki.Kp;

import java.io.*;
import java.util.Scanner;

/**
 * gko - główny kontener operacyjny
 */
public class Gko {
    private Kmip kmip;
    private Kp kp;

    public Gko(String plikMapy, String plikZlecen, int pojemnoscSamochodu) {
        File fplikZlecen = new File(plikZlecen);
        File fplikMapy = new File(plikMapy);
        int idBazy = this.getIdBazy(fplikZlecen);
        kmip = new Kmip(fplikMapy, idBazy);
        kp = new Kp(kmip, pojemnoscSamochodu, fplikZlecen);
    }

    public Kmip getKmip() {
        return kmip;
    }

    public Kp getKp() {
        return kp;
    }

    @Override
    public String toString() {
        return kmip.toString() + "\n" + kp.toString();
    }

    public int getIdBazy(File plik) {
        return 0;
    }
}
