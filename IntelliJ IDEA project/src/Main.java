

/**
 * Created by shafe_000 on 2014-11-30.
 */
public class Main {
    public static void main(String args[]) {
        String plikMiast = "";
        String plikZleceń = "";
        Integer liczbaSamochodów = 10;
        Integer pojemnośćSamochodu = 1;
        String instrukcja =
                "OPCJE WYWOŁANIA PROGRAMU\n" +
                        "-m [plik] - wskazuje ścieżkę do pliku z informacją o miastach i połączeniach\n" +
                        "-z [plik] - wskazuje ścieżkę do pliku z informacją o zleceniach\n" +
                        "-n [liczba] - liczba dostępnych samochodów\n" +
                        "-l [liczba] - limit paczek w jednym samochodzie";

        boolean czyWprowadzonoArgument[] = {false, false, false, false};

        for (int j = 0; j < args.length - 1; j++) {
            if (args[j].contains("-m")) {
                plikMiast = args[j + 1];
                czyWprowadzonoArgument[0] = true;
            } else if (args[j].contains("-z")) {
                czyWprowadzonoArgument[1] = true;
                plikZleceń = args[j + 1];
            } else if (args[j].contains("-n")) {
                czyWprowadzonoArgument[2] = true;
                liczbaSamochodów = Integer.parseInt(args[j + 1]);
            } else if (args[j].contains("-l")) {
                czyWprowadzonoArgument[3] = true;
                pojemnośćSamochodu = Integer.parseInt(args[j + 1]);
            }
        }

        for (int j = 0; j < 4; j++) {
            if (!czyWprowadzonoArgument[j]) {
                System.out.print(instrukcja);
                return;
            }
        }

        Gko gko = new Gko(plikMiast, plikZleceń, pojemnośćSamochodu);
        System.out.println(gko.toString());

    }
}