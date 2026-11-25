/*
CICLO DE JUEGO:
primero se genera el tablero vacio y se enseña
se pone la primera coordenada
luego generan las minas evitando la primera posicion
ahi entra el bucle de juego
se pone una x y una y (de 0 hasta TAMAÑO-1)
si esa posicion es mina, se pierde
si no quedan posiciones sin descubrir que no sean minas, se gana
*/

import java.util.Scanner;
import java.util.Random;

public class minesweeper {
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		Random rd = new Random();
		int TAMAÑO = 8;
		int NUMMINAS = 10;
		int [][] minas = new int[TAMAÑO][TAMAÑO];
		char [][] tablero = new char[TAMAÑO][TAMAÑO];
		//inicio
		tablero = iniciarTablero(TAMAÑO);
		mostrarTablero(tablero);
		int [] coordenadas = coords(sc);
		minas = iniciarMinas(minas, rd, NUMMINAS, coordenadas);
		tablero = sacarMinas(tablero, minas, coordenadas);
		boolean terminado = haTerminado(tablero, minas, coordenadas);
		while(!terminado){
			mostrarTablero(tablero);
			coordenadas=coords(sc);
			tablero=sacarMinas(tablero, minas, coordenadas);
			terminado = haTerminado(tablero, minas, coordenadas);
		}
		mostrarTablero(tablero);
	}

	public static boolean haTerminado(char [][] tablero, int [][] minas, int [] coordenadas){
		int x = coordenadas[0];
		int y = coordenadas[1];
		if(x>=0 && x<tablero[0].length && y>=0 && y<tablero.length && minas[y][x]==1){
			System.out.println("Has pisado una mina.");
			return true;
		}
		for(int i=0; i<tablero.length; i++){
			for(int j=0; j<tablero[0].length; j++){
				if(tablero[i][j]=='#' && minas[i][j]==0){
					return false;
				}
			}
		}
		System.out.println("Has ganado.");
		return true;
	}

	public static char[][] sacarMinas(char [][]tablero, int [][] minas, int [] coordenadas){
		int x = coordenadas[0];
		int y = coordenadas[1];
		if(x<0 || x>=tablero[0].length || y<0 || y>=tablero.length)
			return tablero;
		if(tablero[y][x]!='#' || minas[y][x]==1)
			return tablero;
		int minasady = adyacentes(minas, x, y);
		if(minasady!=0){
			tablero[y][x]=(char) (minasady+'0');
			return tablero;
		}
		else{
			tablero[y][x]=' ';
			for(int i=-1; i<=1; i++)
				for(int j=-1; j<=1; j++){
					if(i!=0 || j!=0){
						int posx=x+i;
						int posy=y+j;
						if(posx>=0 && posx<tablero[0].length && posy>=0 && posy<tablero.length){
							int [] adevolvercoords = {posx, posy};
							sacarMinas(tablero, minas, adevolvercoords);
						}
					}
				}
		}
		return tablero;
	}

	public static int adyacentes(int [][] tabla, int x, int y){
		int cont = 0, posx=0, posy=0;
		for(int i=-1; i<=1; i++)
			for(int j=-1; j<=1; j++){
				if(i!=0 || j!=0){
					posx=x+i;
					posy=y+j;
					if(posx>=0 && posx<tabla[0].length && posy>=0 && posy<tabla.length){
						if(tabla[posy][posx]==1)
							cont++;
					}
				}
			}
		return cont;
	}
	public static int[][] iniciarMinas(int [][] tabla, Random rd, int minass, int [] coords){
		int posx, posy;
		for(int i=0; i<minass; i++){
			posx=rd.nextInt(tabla[0].length);
			posy=rd.nextInt(tabla.length);
			int difx = posx - coords[0];
			int dify = posy - coords[1];
			boolean cerca = (difx >= -1 && difx <= 1) && (dify >= -1 && dify <= 1);
			if(tabla[posy][posx]==0 && !cerca)
				tabla[posy][posx]=1;
			else
				i--;
		}
	        return tabla;
	}
	public static char[][] iniciarTablero(int size){
		char [][] adevolver = new char [size][size];
		for(int i=0; i<size; i++)
			for(int j=0; j<size; j++)
				adevolver[i][j]='#';
		return adevolver;
	}

	public static int[] coords(Scanner sc){
		System.out.print("Posicion x -> ");
		int posx = sc.nextInt();
		System.out.print("Posicion y -> ");
		int posy = sc.nextInt();
		int [] paradevolver = {posx, posy};
		return paradevolver;
	}

	public static void mostrarTablero(char [][] tabla){
		//┌, ─, ┐, │, └, ┘
		System.out.print("┌");
		for(int i=0;i<tabla.length;i++)
			System.out.print("──");
		System.out.print("─┐");
		System.out.println();
		for(int i=0; i<tabla.length; i++){
			System.out.print("│ ");
			for(int j=0; j<tabla.length; j++)
				System.out.print(tabla[i][j]+" ");
			System.out.print("│");
			System.out.println();
		}
		System.out.print("└");
		for(int i=0;i<tabla.length;i++)
			System.out.print("──");
		System.out.print("─┘");
		System.out.println();
   	}
}
