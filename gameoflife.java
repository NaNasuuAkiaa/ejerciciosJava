/* 
Para una viva -> Si tiene 0 o 1 -> muere
                 Si tiene 2 o 3 -> vive
                 Si tiene +3 -> muere
Para una muerta -> Si tiene 3 -> nace
                   Si tiene !=3 -> sigue muerta
*/

public class gameoflife {
    public static void main(String args[]){
        int SIZE = 20;
        int PASOS = 100;
        char [][] tablero = new char[SIZE][SIZE];
        int [][] posicionesiniciales = {
            {0,1},
            {1,2},
            {2,0},
            {2,1},
            {2,2}
        };
        tablero = posicionesIni(tablero, posicionesiniciales);
        imprimirTablero(tablero);
        for(int i=0; i<PASOS; i++){
            tablero = step(tablero);
            imprimirTablero(tablero);
            dormir();
        }
    }

    public static void dormir(){
            try {
                Thread.sleep(150); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Sleep was interrupted");
            }
    }

    public static char[][] posicionesIni(char[][] tabla, int [][] posiciones){
        for(int i=0; i<tabla.length; i++)
            for(int j=0; j<tabla[0].length; j++)
                tabla[i][j]=' ';

        for(int i=0; i<posiciones.length; i++)
            tabla[posiciones[i][0]][posiciones[i][1]]='#';

        return tabla;
    }

    public static int adyacentes(char [][] tabla, int x, int y){
        int cont = 0, posx=0, posy=0;
        for(int i=-1; i<=1; i++)
            for(int j=-1; j<=1; j++){
                if(i!=0 || j!=0){
                    posx=i+x;
                    posy=j+y;
                    if(posx>=0 && posx<tabla.length && posy>=0 && posy<tabla[0].length){
                        if(tabla[posx][posy]=='#')
                            cont++;
                    } 
                }
            }
        return cont;
    }

    public static void imprimirTablero(char [][] tabla){
        for(int i=0; i<tabla.length; i++){
            for(int j=0; j<tabla[0].length; j++){
                System.out.print(tabla[i][j]);
            }
            System.out.println("");
        }
        System.out.println("=========================");
    }

    public static char[][] step(char [][] tabla){
        char [][] tablaaux= new char [tabla.length][tabla[0].length];
        int nums=0;
        for(int i=0; i<tabla.length; i++)
            for(int j=0; j<tabla[0].length; j++){
                nums=adyacentes(tabla, i, j);
                if(tabla[i][j]==' ')
                    if(nums==3)
                        tablaaux[i][j]='#';
                    else
                        tablaaux[i][j]=' ';
                else {
                    if(nums<=1)
                        tablaaux[i][j]=' ';
                    else
                        if(nums>=4)
                            tablaaux[i][j]=' ';
                        else
                            tablaaux[i][j]='#';
                }
            }
        return tablaaux;
    }
}
