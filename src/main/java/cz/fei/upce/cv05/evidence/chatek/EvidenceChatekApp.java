package cz.fei.upce.cv05.evidence.chatek;

import java.util.Scanner;

public class EvidenceChatekApp {

    // Konstanty pro definovani jednotlivych operaci (pouze pro cisty kod)
    static final int KONEC_PROGRAMU = 0;
    static final int VYPIS_CHATEK = 1;
    static final int VYPIS_KONKRETNI_CHATKU = 2;
    static final int PRIDANI_NAVSTEVNIKU = 3;
    static final int ODEBRANI_NAVSTEVNIKU = 4;
    static final int CELKOVA_OBSAZENOST = 5;
    static final int VYPIS_PRAZDNE_CHATKY = 6;

    static final int VELIKOST_KEMPU = 10;
    static final int MAX_VELIKOST_CHATKY = 4;

    static Scanner scanner = new Scanner(System.in);

    // Definovani pole podle velikosti kempu (poctu chatek)
    static int[] chatky = new int[VELIKOST_KEMPU];

    static String menu = """
                    MENU:
                                        
                    1 - vypsani vsech chatek
                    2 - vypsani konkretni chatky
                    3 - Pridani navstevniku
                    4 - Odebrani navstevniku
                    5 - Celkova obsazenost kempu
                    6 - Vypis prazdne chatky
                    0 - Konec programu
                    """;

    public static void main(String[] args) {
        int operace;

        do {
            System.out.println(menu);

            // Ziskani operace od uzivatele
            System.out.print("Zadej volbu: ");
            operace = scanner.nextInt();

            switch (operace) {
                case VYPIS_CHATEK -> {

                    vypisChatek();
                }

                case VYPIS_KONKRETNI_CHATKU -> {

                    vypisKonkretniChatku();
                }

                case PRIDANI_NAVSTEVNIKU -> {

                    pridejNavstevnikaDoChatky();
                }

                case ODEBRANI_NAVSTEVNIKU -> {
                    odeberNastevnikaZChatky();
                }

                case CELKOVA_OBSAZENOST -> {
                    vypisObsazenostChatky();
                }

                case VYPIS_PRAZDNE_CHATKY -> {
                    vypisPrazdneChatky();
                }

                case KONEC_PROGRAMU -> {
                    System.out.println("Konec programu");
                }

                default -> {
                    System.err.println("Neplatna volba");
                }
            }
        } while (operace != 0);
    }

    private static void pridejNavstevnikaDoChatky() {
        int cisloChatky = najdiCisloChatky();
        // Ziskani poctu navstevniku, kteri se chteji v chatce ubytovat
        int pocetNavstevniku;

        System.out.print("Zadej pocet navstevniku: ");
        pocetNavstevniku = scanner.nextInt();
        // Pokud je pocet uz ubytovanych plus ty co se chteji ubytovat vetsi nez kapacita chatky je to nevalidni vstup
        if (kontrolaPoctuNavstevnikuNaVstupu(pocetNavstevniku)) {
            if ((chatky[cisloChatky] + pocetNavstevniku) > MAX_VELIKOST_CHATKY) {
                System.err.println("Prekrocen maximalni pocet navstevniku chatky");

            }
        }
        // Pridej nove ubytovane do chatky k tem co uz tam jsou
        chatky[cisloChatky] = pocetNavstevniku + chatky[cisloChatky];
    }

    private static boolean kontrolaPoctuNavstevnikuNaVstupu(int pocetNavstevniku) {
        // Zaporne cislo nebo prilis velky nevalidni vstup
        if (pocetNavstevniku <= 0 || pocetNavstevniku > MAX_VELIKOST_CHATKY) {
            System.err.println("Neplatna hodnota pro pocet navstevniku"); // Zacni novou iteraci cyklu
            return false;
        }
        return true;
    }

    private static int najdiCisloChatky() {
        // Ziskani cisla chatky od uzivatele
        int cisloChatky;
        do {
            System.out.print("Zadej cislo chatky: ");
            // Odecteni 1 protoze uzivatel cisluje chatky o 1, ale program od 0
            cisloChatky = scanner.nextInt() - 1;
            // Zaporne nebo cislo vetsi nez je pocet chatek je nevalidni vstup
            if (cisloChatky < 0 || cisloChatky >= chatky.length) {
                System.err.println("Tato chatka neexistuje");
                // Zacni novou iteraci cyklu
            }
        } while (cisloChatky < 0 || cisloChatky >= chatky.length);
        return cisloChatky;
    }

    private static boolean vypisKonkretniChatku() {
        // Ziskani cisla chatky od uzivatele
        System.out.print("Zadej cislo chatky: ");
        int cisloChatky = scanner.nextInt();
        // Zaporne nebo cislo vetsi nez je pocet chatek je nevalidni vstup
        if (cisloChatky < 0 || cisloChatky >= chatky.length) {
            System.err.println("Tato chatka neexistuje");
            return false; // Zacni novou iteraci cyklu
        }
        System.out.println("Chatka [" + cisloChatky + "] = " + chatky[cisloChatky]);
        return true;
    }

    private static void vypisChatek() {
        // Projdi cele pole od <0, VELIKOST) a vypis kazdy index
        for (int i = 0; i < chatky.length; i++) {
            System.out.println("Chatka [" + (i + 1) + "] = " + chatky[i]);
        }
    }

    private static void odeberNastevnikaZChatky() {
        int cisloChatky = najdiCisloChatky();
        // Ziskani poctu navstevniku, kteri se chteji v chatce ubytovat
        int pocetNavstevniku;
        System.out.print("Zadej pocet navstevniku: ");
        pocetNavstevniku = scanner.nextInt();
        if (kontrolaPoctuNavstevnikuNaVstupu(pocetNavstevniku)) {
            if ((chatky[cisloChatky] - pocetNavstevniku) < 0) {
                System.err.println("Odebrano az moc navstevniku z chatky");

            }
        }

        chatky[cisloChatky] -= pocetNavstevniku;
    }

    private static void vypisObsazenostChatky() {
        int pocet_navstevniku = 0;
        for (int chatka : chatky) {
            pocet_navstevniku += chatka;
        }
        System.out.printf("V arealu je celkem %d navstevniku\n", pocet_navstevniku);
    }

    private static void vypisPrazdneChatky() {
        for (int i = 0; i < chatky.length; i++) {
            if (chatky[i] == 0) {
                System.out.printf("Chatka %d je prazdna\n", i);
            }

        }
    }

}
