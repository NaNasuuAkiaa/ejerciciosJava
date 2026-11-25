//simulacro de examen del dia 25/11/2026. Hecho a papel y transcrito, tiene mejores soluciones.

public class sudoku {
	public static void main(String args[]){
		int[][] tablero = {
			{5, 3, 4, 6, 7, 8, 9, 1, 2},
			{6, 7, 2, 1, 9, 5, 3, 4, 8},
			{1, 9, 8, 3, 4, 2, 5, 6, 7},
			{8, 5, 9, 7, 6, 1, 4, 2, 3},
			{4, 2, 6, 8, 5, 3, 7, 9, 1},
			{7, 1, 3, 9, 2, 4, 8, 5, 6},
			{9, 6, 1, 5, 3, 7, 2, 8, 4},
			{2, 8, 7, 4, 1, 9, 6, 3, 5},
			{3, 4, 5, 2, 8, 6, 1, 7, 9}
		};
		System.out.println("Es el sudoku valido? "+validarSudoku(tablero));
	}
	public static boolean esGrupoValido(int [] grupo){
		for(int i=0; i<grupo.length; i++){
			if(grupo[i]>=10 || grupo[i]<0)
				return false;
			for(int j=0; j<grupo.length; j++)
				if(i!=j)
					if(grupo[i]==grupo[j])
						return false;
		}
		return true;
	}
	public static boolean validarFila(int [][] tablero, int fila){
		return esGrupoValido(tablero[fila]);
	}
	public static boolean validarColumna(int [][] tablero, int columna){
		int [] columnaArr = new int [tablero.length];
		for(int i=0; i<tablero.length; i++)
			columnaArr[i]=tablero[i][columna];
		return esGrupoValido(columnaArr);
	}
	public static boolean validarBloque(int [][] tablero, int filaInicio, int columnaInicio){
		int [] grupo = new int [9];
		int cont = 0;
		for(int i=filaInicio; i<=filaInicio+2; i++)
			for(int j=columnaInicio; j<=columnaInicio+2; j++){
				grupo[cont]=tablero[i][j];
				cont++;
			}
		return esGrupoValido(grupo);
	}
	public static boolean validarSudoku(int [][] tablero){
		for(int i=0; i<tablero.length; i++)
			if(!validarFila(tablero, i))
				return false;
		for(int i=0; i<tablero.length; i++)
			if(!validarColumna(tablero, i))
				return false;
		for(int i=0; i<tablero.length; i+=3)
			for(int j=0; j<tablero.length; j+=3)
				if(!validarBloque(tablero, i, j))
					return false;
		return true;
	}
}
