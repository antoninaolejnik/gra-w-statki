public class Plansza {
    private int[][] plansza;
    public Plansza()
    {
        plansza = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                plansza[i][j] = 0;
            }
        }
    }

    public void ustawPole(int x, int y, int wartość) {
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            plansza[x][y] = wartość;
        } else {
            System.out.println("wspolrzedne poza zakresem");
        }
    }

    public void wypiszPlansze()
    {
        System.out.println("- - - - - - - - - - - - - - - - - - - - ");
        for (int i = 0; i < 10; i++) {
            System.out.print("| ");
            for (int j = 0; j < 10; j++) {
                System.out.print(plansza[i][j] + " | ");
            }
            System.out.println("\n- - - - - - - - - - - - - - - - - - - - - ");
        }
        System.out.println("\n\n");
    }

    public static void main(String[] args) {
        Plansza plansza = new Plansza();
        plansza.wypiszPlansze();
//        plansza.ustawPole(1,4,3);
//        plansza.wypiszPlansze();
    }
}
