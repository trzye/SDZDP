package gko;

import dijkstra.Dijkstra;
import dijkstra.EtapDrogi;
import graf.Wierzcholek;
import przesylki.Przesylka;
import vizualizacja.RuchSamochodow;
import vizualizacja.Zdarzenie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shafe_000 on 2014-12-02.
 */
public class Rozwiazanie {


    private ArrayList<ElementRozwiazania> rozwiazanie;

    public RuchSamochodow getRuchSamochodow() {
        return ruchSamochodow;
    }

    private RuchSamochodow ruchSamochodow;

    public Rozwiazanie(Gko g, int liczbaSamochodow) {
        String baza = g.getKp().getMiasta().get(g.getIdBazy()).getMiasto().toString();
        g.getKp().getMiasta().remove(g.getIdBazy());
        int p = g.getPojemnosc();
        int t[] = new int[liczbaSamochodow];
        int tTemp[] = new int[liczbaSamochodow];
        int lm = g.getKp().getMiasta().size() - 1;    //indeks konca tablicy miast
        int lp = g.getKp().getMiasta().get(lm).getPrzesylki().size() - 1; //indeks konca tablicy przesylek dla ostatniego miasta
        //-------------Ostatni w kolejce ma zawsze pierwsza kolejnosc do wyslania---------------------------------------------
        rozwiazanie = new ArrayList<ElementRozwiazania>();
    ruchSamochodow = new RuchSamochodow(liczbaSamochodow);

        for (int i = 0; i < t.length; i++) {
            t[i] = 0;
            tTemp[i] = 0;
        }
        while (g.getKp().getMiasta().get(lm).getPrzesylki().size() != 0) {
            for (int i = 0; i < liczbaSamochodow; i++) {
                if (g.getKp().getMiasta().get(lm).getMiasto().getW().najmniejszaOdleglosc != Integer.MAX_VALUE) {
                    tTemp[i] = t[i];
                    t[i] += g.getKp().getMiasta().get(lm).getMiasto().getW().najmniejszaOdleglosc;
                }
                //System.out.println("Miasto: " + g.getKp().getMiasta().get(lm).getMiasto().toString() + " odleglosc:" + g.getKp().getMiasta().get(lm).getMiasto().getW().najmniejszaOdleglosc + " prio: " + g.getKp().getMiasta().get(lm).toString());
                //System.out.println("samochód " + i );
                for (int j = 0; j < p; j++) {
                    if (g.getKp().getMiasta().get(lm).getMiasto().getW().najmniejszaOdleglosc == Integer.MAX_VALUE) ;
                    if (lp - j >= 0) {
                        if (g.getKp().getMiasta().get(lm).getPrzesylki().size() > 0) {

                            String iDprzesylki = changeId(g.getKp().getMiasta().get(lm).getPrzesylki().get(lp - j).getId());
                            String miastoCel = g.getKp().getMiasta().get(lm).getMiasto().toString();
                            //-----------------------------Wypisuje szczegółowe działąnie przydzielania paczek i transportu---------------------------------
                            System.out.print(tTemp[i] + " Samochód: " + i + ", pobrano " + iDprzesylki + " z " + baza + "\n");
                            ruchSamochodow.getSamochody().get(i).getZdarzenia().add(new Zdarzenie(baza, tTemp[i]));
                            List<EtapDrogi> etapy = Dijkstra.getShortestPathTo( g.getKp().getMiasta().get(lm).getMiasto().getW());
                            int czas = tTemp[i];
                            for (int jk = 0; jk < etapy.size(); jk++) {
                                int czas1 = etapy.get(jk).getOdleglosc();
                                czas = czas + czas1;
                                System.out.println(czas + " " + etapy.get(jk).getMiasto().toString());
                                ruchSamochodow.getSamochody().get(i).getZdarzenia().add(new Zdarzenie(etapy.get(jk).getMiasto().toString(), czas));
                            }
                            for (int jk = etapy.size()-1; jk >= 1; jk--) {
                                int czas1 = etapy.get(jk).getOdleglosc();
                                czas = czas + czas1 ;
                                System.out.println(czas + " " + etapy.get(jk-1).getMiasto().toString());
                                ruchSamochodow.getSamochody().get(i).getZdarzenia().add(new Zdarzenie(etapy.get(jk-1).getMiasto().toString(), czas));
                            }
                            czas += etapy.get(0).getOdleglosc();
                            ruchSamochodow.getSamochody().get(i).getZdarzenia().add(new Zdarzenie(baza, czas));
                            System.out.println(czas + " " + baza);

                            System.out.print(t[i] + " Samochód: " + i + ", dostarczono " + iDprzesylki + " do " + miastoCel + "\n");
                            ruchSamochodow.getSamochody().get(i).getZdarzenia().add(new Zdarzenie(miastoCel, t[i]));
                            //------------------------------------------------------------------------------------------------------------------------------

                            System.out.println("Samochod: " + i);
                            rozwiazanie.add(new ElementRozwiazania(tTemp[i] + " pobrano przesyłkę " + changeId(g.getKp().getMiasta().get(lm).getPrzesylki().get(lp - j).getId()) + " z miasta "+ baza +"\n"));
                            rozwiazanie.add(new ElementRozwiazania(t[i] + " dostarczono przesyłkę " + changeId(g.getKp().getMiasta().get(lm).getPrzesylki().get(lp - j).getId()) + " do miasta "+ g.getKp().getMiasta().get(lm).getMiasto().toString()+"\n"));
                            g.getKp().getMiasta().get(lm).getPrzesylki().remove(lp - j);
                            g.getKp().getMiasta().get(lm).obliczPriorytet();}
                    } else {
                        break;
                    }
                }
                if (g.getKp().getMiasta().get(lm).getMiasto().getW().najmniejszaOdleglosc != Integer.MAX_VALUE) {
                    tTemp[i] = t[i];
                    t[i] += g.getKp().getMiasta().get(lm).getMiasto().getW().najmniejszaOdleglosc;
                }
                g.getKp().sortKp();
                //System.out.println("-----------------"+g.getKp().toString()+"-----------------");
                lp = g.getKp().getMiasta().get(lm).getPrzesylki().size() - 1;
            }
            g.getKp().sortKp();
        }
    }

    private String changeId(int i) {
        if (i < 10) return "00" + i;
        if (i < 100) return "0" + i;
        return "" + i;
    }

    @Override
    public String toString() {
        Collections.sort(rozwiazanie);
        String s = "";
        for (int j = 0; j < rozwiazanie.size(); j++) {
            s += rozwiazanie.get(j);
        }
        return s;
    }

    private class ElementRozwiazania implements Comparable<ElementRozwiazania> {
        public int czas;
        String elem;

        public ElementRozwiazania(String s) {
            elem = s;
            String x[] = s.split(" ");
            czas = Integer.parseInt(x[0]);
        }

        @Override
        public int compareTo(ElementRozwiazania o) {
            return this.czas - o.czas;
        }

        @Override
        public String toString() {
            return elem;
        }
    }
}

