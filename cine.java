//primer trabajo de clase en grupo

//un cine necesita un programa para contorlar la venta de entradas de una sesion
//comienza preguntando cuantas localidades tiene disponibles la sala
//cada cliente indicara su nombre, su edad y el numero de entradas
//el precio normal es de 8, los menores pagan 6
//el programa debe atender clientes mientras queden localidades disponibles
//no debera permitir vender mas entradas de las que queden libres
//cuando no queden localidades, o cuando se indique que no hay mas clientes, el programa termina
//al terminar, mostrara:
// - numero total de entradas vendidas
// - numero de clientes atendidos
// - la recaudacion total
// si se ha conseguido llenar completamente la sala



import java.util.Scanner;

public class Cine {

	public static void main(String args[]){

		int capacidad=0,edad=0, numero_menores=0, numero_clientes=0, leer_entradas=0, entradas_vendidas=0, recaudacion=0, total_a_pagar=0;
		String esCliente="", nombre="";

		Scanner sc = new Scanner(System.in);

		System.out.println("Cines B R A M");
		System.out.println("Capacidad de la sala: ");
		capacidad=sc.nextInt();
		sc.nextLine(); //limpiar buffer

		System.out.println("Desea comprar entradas? (si/no)");
		esCliente = sc.nextLine();

		while(esCliente.equals("si") && capacidad>0){
			//pedir el nombre
			System.out.println("Como te llamas?");
			nombre = sc.nextLine();

			//pedir entradas
			System.out.println("Hola, "+nombre+", cuantas entradas quieres? (Quedan "+capacidad+" entradas)");
			leer_entradas = sc.nextInt();
			sc.nextLine(); //limpiar el buffer

			//Comprobar si el cliente esta comprando mas entradas que quedan
			if(leer_entradas>capacidad)
				System.out.println("No quedan suficientes entradas.");
			else { //si no:
				if(leer_entradas==1){ //si solo se compra una

					System.out.println("Cuantos años tienes, "+nombre+"?");
					edad = sc.nextInt();
					sc.nextLine();
					if(edad<18){
						total_a_pagar=6;
						recaudacion+=6;
					}
					else {
						recaudacion+=8;
						total_a_pagar=8;
					}

				} else { //se compra mas de una
					System.out.println("Cuantos menores hay? (si no hay menores, poner 0)");
					numero_menores = sc.nextInt();
					sc.nextLine();
					if(numero_menores>leer_entradas)
						System.out.println("No puede haber mas menores que entradas");
					else {
						total_a_pagar=(numero_menores*6)+((leer_entradas - numero_menores)*8);
						recaudacion+=total_a_pagar;
					}
				}
				entradas_vendidas+=leer_entradas;
				capacidad-=leer_entradas;
				numero_clientes++;
				System.out.println("Total a pagar: "+total_a_pagar);
				System.out.println("Gracias por su compra!\n");
				System.out.println("==============");
			}
			if(capacidad>0){
				System.out.println("Desea comprar entradas? (si/no)");
				esCliente = sc.nextLine();
			} else
				capacidad=0;
		}

		System.out.println("Numero total de entradas vendidas: "+entradas_vendidas);
		System.out.println("Numero total de clientes atendidos: "+numero_clientes);
  		System.out.println("la recaudacion total han sido: "+recaudacion+" euros");
		if(capacidad==0)
			System.out.println("Se ha llenado la sala");
		else
			System.out.println("No se ha llenado la sala");

	}

}
