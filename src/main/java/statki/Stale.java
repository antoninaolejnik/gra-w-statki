package statki;

public class Stale {
    public static final int ilosc_rodzajow=4;//4
    public static final int ilosc_statkow=10;//10
    public static final int puste=-2;
    public static final int obok=-1;
    public static final int puste2=-5;

    //tablica??
    public static final int[][] dane ={
            {1,1,4},
            {2,2,3},
            {3,3,2},
            {4,4,1}
            //pierwsza kolumna nr rodzaju, druga dlugosc, ilosc
    };
//    public static final int[][] dane ={
//            {1,1,2},
//            {2,2,1}
//            //pierwsza kolumna nr rodzaju, druga dlugosc, ilosc
//    };
//    public static final int[][] dane ={
//            {1,2,1},
//            {2,4,1}
//            //pierwsza kolumna nr rodzaju, druga dlugosc, ilosc
//    };

//    public static final int[][] dane ={
//            {1,1,1}
//            //pierwsza kolumna nr rodzaju, druga dlugosc, ilosc
//    };
//    static public Pair <Integer, String> [] nazwy;
    public static String [] nazwy = {
        "jednomasztowiec", "dwumasztowiec", "trójmasztowiec", "czteromasztowiec"
    };


}
