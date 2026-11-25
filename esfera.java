public class esfera {
	public static void main(String args[]) {
		int RADIO = 25;
		int[][][] esfera = new int[RADIO * 2 + 1][RADIO * 2 + 1][RADIO * 2 + 1];
		esfera = generarEsfera(esfera, RADIO);
		// debugporcapas(esfera, 10);
		char[][] pantalla = new char[RADIO * 2 + 1][RADIO * 2 + 1];
		pantalla = aplanarPantalla(esfera, RADIO);
		debugimprimirpantalla(pantalla);
	}

	public static int[][][] generarEsfera(int[][][] e, int r) {
		int rados = powerados(r);
		for (int i = -r; i <= r; i++) {
			for (int j = -r; j <= r; j++) {
				for (int k = -r; k <= r; k++) {
					if ((powerados(i) + powerados(j) + powerados(k)) <= rados) {
						e[i + r][j + r][k + r] = 1;
					} else {
						e[i + r][j + r][k + r] = 0;
					}
				}
			}
		}
		return e;
	}

	public static char[][] aplanarPantalla(int[][][] e, int r) {
		int total = e.length;
		char[][] pantalla = new char[total][total];

		for (int i = 0; i < total; i++) {
			for (int j = 0; j < total; j++) {
				pantalla[i][j] = ' ';
			}
		}

		double ASPECT_RATIO = 0.47393364928;
		// este numero viene del ratio de la fuente del cmd de windows, que es 1:2.11
		// para que se vea esférico, el eje y se multiplica por este factor

		//(-1 , 1)
		double luzX = 1.0;
		double luzY = -1.0;
		double luzZ = 1.0;

		// Normalizar el vector de luz para que su magnitud sea 1
		double longitudLuz = sqrt(luzX * luzX + luzY * luzY + luzZ * luzZ);
		luzX /= longitudLuz;
		luzY /= longitudLuz;
		luzZ /= longitudLuz;

		char[] gradienteSombra = { '.', ',', '-', '~', ':', ';', '=', '!', '*', '#', '$', '@' };

		for (int i = 0; i < total; i++) {
			// Mapeamos la fila actual de la pantalla comprimiéndola verticalmente mediante
			// el ratio
			int original1 = r + round((i - r) / ASPECT_RATIO);

			// Si la fila calculada sale de los límites de la esfera, la ignoramos en lugar
			// de saturarla
			if (original1 < 0 || original1 >= total) {
				continue;
			}

			for (int k = 0; k < total; k++) {
				for (int j = 0; j < total; j++) {
					if (e[original1][j][k] == 1) {
						double nx = original1 - r;
						double ny = j - r;
						double nz = k - r; // relativas

						// normalizado
						double len = sqrt(nx * nx + ny * ny + nz * nz);
						if (len > 0) {
							nx /= len;
							ny /= len;
							nz /= len;
						}

						// producto escalar junto a la luz
						double dot = nx * luzX + ny * luzY + nz * luzZ;

						// para las sompras mas oscuras, pq es menor a cero
						if (dot < 0)
							dot = 0;

						// actualizar el array
						int indiceSombra = round(dot * (gradienteSombra.length - 1));
						char c = gradienteSombra[indiceSombra];

						if (pantalla[i][k] == ' ') {
							pantalla[i][k] = c;
						}
					}
				}
			}
		}
		return pantalla;
	}

	public static int powerados(int n) {
		return n * n;
	}

	public static double sqrt(double n) {
		if (n < 0) {
			throw new IllegalArgumentException("Numero negativo, no hay raiz");
		}
		if (n == 1 || n == 0) {
			return n;
		}
		double ini = n / 2, ant, precision = 0.0000001;
		do {
			ant = ini;
			ini = (ant + (n / ant)) / 2;
		} while ((ant - ini) > precision);

		return ini;
	}

	public static int round(double n) {
		boolean esNegativo = n < 0;
		if (esNegativo) {
			n = -n;
		}

		int parteEntera = 0;
		double resto = n;

		while (resto >= 1.0) {
			resto = resto - 1.0;
			parteEntera++;
		}

		if (resto >= 0.5) {
			parteEntera++;
		}

		if (esNegativo) {
			if (resto == 0.5) {
				parteEntera--;
			}
			return -parteEntera;
		}

		return parteEntera;
	}

	public static void debugporcapas(int[][][] e, int altura) {
		int total = e.length;
		int count = 0;
		for (int i = 0; i < total; i++) {
			for (int k = 0; k < total; k++) {
				System.out.print(e[i][altura][k] + ",");
				count++;
				if (count == total) {
					System.out.println();
					count = 0;
				}
			}
		}
	}

	public static void debugimprimirpantalla(char[][] p) {
		int total = p.length;
		for (int i = 0; i < total; i++) {
			for (int j = 0; j < total; j++) {
				System.out.print(p[i][j]);
			}
			System.out.println();
		}
	}
}
