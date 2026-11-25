//hecho como trabajo de clase entre cuatro personas siguiendo las reglas del profesor del juego

/*
hay tres jugadores, dos oficiales y la banca.
cada jugador empieza con 100 euros, la banca tiene dinero indefinido
el as vale -1, el resto de cartas vale lo que vale, hast a el 10
j,q,k vale 11
*/

import java.util.Random;
import java.util.Scanner;

public class blackjack {

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        Random rd = new Random();

        int saldo_jugador_uno = 100;
        int saldo_jugador_dos = 100;

        while(saldo_jugador_uno>0 && saldo_jugador_dos>0){
            System.out.println("Saldo jugador 1: "+saldo_jugador_uno+" euros");
            System.out.println("Saldo jugador 2: "+saldo_jugador_dos+" euros");
            System.out.println("Comienza la ronda.");

            int [] saldos = ronda(saldo_jugador_uno, saldo_jugador_dos, sc, rd);

            saldo_jugador_uno = saldos[0];
            saldo_jugador_dos = saldos[1];
        }

        //el ganador es:
        System.out.println("Fin del juego.");

        if(saldo_jugador_uno>0)
            System.out.println("El ganador es el jugador 1, con un total de "+saldo_jugador_uno+" euros.");
        else
            System.out.println("El ganador es el jugador 2, con un total de "+saldo_jugador_dos+" euros.");
    }

    //cuando se use esta funcion, apu es la apuesta de la ronda; cartas_repartidas se va a pasar siempre; 
    public static int[] ronda(int saldo1, int saldo2, Scanner sc, Random rd){
        int APUESTA_MAXIMA = 50;
        String [] PALOS = {"diamantes","treboles","picas","corazones"};
        String [] CARTAS = {"as","dos","tres","cuatro","cinco","seis","siete","ocho","nueve","diez","J","Q","K"};
        int [] VALORES = {-1,2,3,4,5,6,7,8,9,10,11,11,11};

        int [][] baraja = new int[13][4]; //1 -> repartida , 0 -> en la baraja
        int apuesta=0, total1=0, total2=0, bote=0;
        boolean jug1plantarse=false, jug2plantarse=false;
        boolean jug1perder=false, jug2perder=false;

        System.out.print("Por favor, introduzca la apuesta (máximo: "+APUESTA_MAXIMA+"): ");
        apuesta = sc.nextInt();
        sc.nextLine();

        if(apuesta>APUESTA_MAXIMA){
            System.out.println("No se puede apostar más de "+APUESTA_MAXIMA+" euros.\n==========\n");
            int [] devolver = {saldo1, saldo2};
            return devolver;
        }

        if(apuesta>saldo1 || apuesta>saldo2) {
            System.out.println("No se puede apostar más del saldo de alguno de los dos jugadores.\n==========\n");
            int [] devolver = {saldo1, saldo2};
            return devolver;
        }
        else {
            saldo1-=apuesta;
            saldo2-=apuesta;
            bote=apuesta*2;
            System.out.println("");

            int [] carta = sacarCarta(baraja, rd);
            baraja [carta[0]] [carta[1]]=1; //establecer que una carta ya ha sido jugada
            System.out.println("Jugador 1 ha sacado: "+CARTAS[carta[0]]+" de "+PALOS[carta[1]]); //carta[0] -> x generada en sacarCarta, carta[1] -> y generada en sacarCarta
            total1+=VALORES[carta[0]];

            System.out.println("Total del Jugador 1: " + total1);

            carta = sacarCarta(baraja, rd);
            baraja [carta[0]] [carta[1]]=1;
            System.out.println("Jugador 2 ha sacado: "+CARTAS[carta[0]]+" de "+PALOS[carta[1]]);
            total2+=VALORES[carta[0]];

            System.out.println("Total del Jugador 2: " + total2);
            System.out.println("");

            while ((!jug1plantarse && !jug1perder) || (!jug2plantarse && !jug2perder)) {
                if(!jug1plantarse && !jug1perder){ //jugador 1
                    System.out.print("Jugador 1, ¿quieres plantarte? (s/n): ");
                    String respuesta = sc.nextLine();

                    if(respuesta.equals("s")){
                        jug1plantarse = true;
                    }
                    else {
                        carta = sacarCarta(baraja, rd);
                        baraja [carta[0]] [carta[1]]=1;
                        System.out.println("Jugador 1 ha sacado: "+CARTAS[carta[0]]+" de "+PALOS[carta[1]]);
                        total1+=VALORES[carta[0]];
                        System.out.println("Total del Jugador 1: " + total1);

                        if(total1>21){
                            System.out.println("Jugador 1 ha perdido. Su apuesta la gana la banca.");
                            jug1perder=true;
                            bote-=apuesta;
                        }
                    }
                }

                if(!jug2plantarse && !jug2perder){ //jugador 2
                    System.out.print("Jugador 2, ¿quieres plantarte? (s/n): ");
                    String respuesta = sc.nextLine();

                    if(respuesta.equals("s")){
                        jug2plantarse = true;
                    }
                    else {
                        carta = sacarCarta(baraja, rd);
                        baraja [carta[0]] [carta[1]]=1;
                        System.out.println("Jugador 2 ha sacado: "+CARTAS[carta[0]]+" de "+PALOS[carta[1]]);
                        total2+=VALORES[carta[0]];
                        System.out.println("Total del Jugador 2: " + total2);

                        if(total2>21){
                            System.out.println("Jugador 2 ha perdido. Su apuesta la gana la banca.");
                            jug2perder=true;
                            bote-=apuesta;
                        }
                    }
                }

                System.out.println("");
            }

            //no hay banca: el jugador que se planta con más puntos gana el bote. Si ambos se plantan con el mismo número de puntos, se reparte a iguales. La apuesta del que ha perdido se la lleva la banca, y si ha terminado, se lleva el bote el ganador.
            int [] devolver = {saldo1, saldo2};

            if(jug1perder && jug2perder){
                System.out.println("Ambos jugadores han perdido. La banca se queda con el bote.");
            }

            if(jug1perder && !jug2perder){
                System.out.println("Jugador 2 gana el bote de "+bote+" euros.");
                devolver[1]+=bote;
            }

            if(!jug1perder && jug2perder){
                System.out.println("Jugador 1 gana el bote de "+bote+" euros.");
                devolver[0]+=bote;
            }

            if(!jug1perder && !jug2perder){
                if(total1>total2){
                    System.out.println("Jugador 1 gana el bote de "+bote+" euros.");
                    devolver[0]+=bote;
                }
                else if(total2>total1){
                    System.out.println("Jugador 2 gana el bote de "+bote+" euros.");
                    devolver[1]+=bote;
                }
                else{
                    System.out.println("Empate. Se reparte el bote a partes iguales.");
                    devolver[0]+=bote/2;
                    devolver[1]+=bote/2;
                }
            }

            System.out.println("");
            return devolver;
        }
    }

    public static int [] sacarCarta(int [][] cartas_repartidas, Random rd){
        int x=rd.nextInt(13);
        int y=rd.nextInt(4);

        if(cartas_repartidas[x][y] == 0){
            int [] carta = {x,y};
            return carta;
        }
        else
            return sacarCarta(cartas_repartidas, rd);
    }
}
