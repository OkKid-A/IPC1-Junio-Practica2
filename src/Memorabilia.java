package src;

import java.util.*;
import java.lang.*;

public class Memorabilia{

	String[] CATEGORIAS = new String[]{"Acción","Ciencia Ficción","Comedia","Romance","Documental","Animación"};
	String[][] clientes;
	String[][] peliculas;
	String[][] prestamos;
	int contClientes;
	int contPeliculas;
	int contPrestamos;
	Scanner scanner;

	public Memorabilia(){
		clientes = new String[30][4];
		peliculas = new String[30][5];
		prestamos = new String[30][3];
		contPrestamos = 0;
		contPeliculas = 0;
		contClientes = 0;
		scanner = new Scanner(System.in);
		irAlMenu();			
	}

	public void dibujarMenu(){
		System.out.println("\nMENÚ PRINCIPAL:");
		System.out.println("\n1.Prestar una Película");
		System.out.println("2.Devolver una Película");
		System.out.println("3.Listar a todas las Películas");
		System.out.println("4.Ingresar nueva Película");
		System.out.println("5.Ordenar las Películas Por Nombre");
		System.out.println("6.Inscribir a un Cliente nuevo");
		System.out.println("7.Listar a todos los Clientes");
		System.out.println("8.Reportes");
		System.out.println("9.Salir");
	}

	public void irAlMenu(){
		dibujarMenu();
		try{
			int opcion = Integer.valueOf(scanner.nextLine());
			switch (opcion){
				case 1:
					prestarPelicula();
					break;
				case 2:
					devolverPelicula();
					break;
				case 3:
					listarPeliculas(false);
					break;
				case 4:
					agregarPelicula();
					break;
				case 5:
					ordenarAlfabeticamente(1,peliculas,contPeliculas);
					System.out.println("\nSe han ordenado las películas en orden ascendente");
					irAlMenu();
					break;
				case 6:
					agregarCliente();
					break;
				case 7:
					listarClientes(false);
					break;
				case 8:
					irAlSubMenuReportes();
					break;
				case 9:
					System.out.println("\nSee you next time");
					System.exit(0);
					break;
				default:
				 	System.out.println("\nEsa opción no está en el menú");
					irAlMenu();
					break;
			}
		} catch (NumberFormatException e){
			System.out.println("\nIngresa el número que se encuentra al lado de la opción que deseas realizar");
			irAlMenu();
		}
	}

	public void irAlSubMenuReportes(){
		mostarSubMenuReportes();	
		try{
			int opcion = Integer.valueOf(scanner.nextLine());
			switch(opcion){
				case 1:
					listarPorCategoria();
					break;
				case 2:
					listarPorCategoriaUnica();
					break;
				case 3:
					listarPrestamosPorPelicula();
					break;
				case 4:
					listarPeliculaMasMenosPrestada();
					break;
				case 5:
					irAlMenu();
					break;
				default:
					System.out.println("Esa opción no esta en el menú");
					irAlSubMenuReportes();
					break;
			}
		} catch (NumberFormatException e) {
			System.out.println("\nEl valor ingresado no es válido");
		}
	}

	public void listarPrestamosPorPelicula(){
		int limite = 0;
		System.out.println("\nPrestamos por Película:");
		for (int k = 0;k < contPeliculas;k++) {
			int cont = 0;
			if (limite == contPrestamos) {
				break;
			} else{
				for (int i = 0;i < contPrestamos;i++) {
					if (peliculas[k][0].equals(prestamos[i][0])) {
						cont++;
						limite++;
					}		
				}
			}
			System.out.println(peliculas[k][1]+": "+cont);
		}
	}

	public void listarPeliculaMasMenosPrestada(){
		int mayor = 0;
		String mayorID = "";
		int menor = 1000;
		String menorID = "";
		for (int k = 0;k < contPeliculas;k++) {
			int cont = 0;
			for (int i = 0;i < contPrestamos;i++) {
				if (peliculas[k][0].equals(prestamos[i][0])) {
					cont++;
				}
			}
			if (cont > mayor) {
				mayor = cont;
				mayorID = peliculas[k][0];
			} else if (cont < menor) {
				menor = cont;
				menorID = peliculas[k][0];
			}
		}
		System.out.println("\nLa película más prestada es: " + peliculas[buscarValor(mayorID,peliculas,0)][1]+". Fue prestada "+mayor+" veces.");
		System.out.println("\nLa película menos prestada es: " + peliculas[buscarValor(menorID,peliculas,0)][1]+". Fue prestada "+menor+" veces.");
		irAlSubMenuReportes();
	}

	public void listarPorCategoriaUnica(){
		try{
			int categoria = elegirCategoria();
			for (int i = 0;i < contPeliculas;i++) {
				if (peliculas[i][3].equals(CATEGORIAS[categoria])) {
					motrarInfoPelicula(i);
				}
			}
		} catch (NumberFormatException e) {
			irAlSubMenuReportes();
		}
		irAlSubMenuReportes();
	}

	public void listarPorCategoria(){
		System.out.print("\nCategorías:\n");
			for (int i = 0;i < CATEGORIAS.length;i++) {
				System.out.println(CATEGORIAS[i]+": "+String.valueOf(contarValor(peliculas,CATEGORIAS[i],3)));
			}
		
		irAlSubMenuReportes();
	}

	public int contarValor(String[][] array, String valor,int posicion){
		int cont = 0;
		for (int i = 0;i < contPeliculas;i++) {
			if (array[i][posicion].equals(valor)) {
				cont++;
			}
		}
		return cont;
	}

	public void mostarSubMenuReportes(){
		System.out.println("\nREPORTES");
		System.out.println("1.Peliculas por Categoría");
		System.out.println("2.Peliculas de una Categoría");
		System.out.println("3.Prestamos por Película");
		System.out.println("4.Película más y menos Prestada");
		System.out.println("5.Regresar");
	}

	public void prestarPelicula(){
		System.out.println("\nLas peliculas disponibles son:");
		listarPeliculas(true);
		System.out.println("0.Regresar");
		try{
			int opcion = Integer.parseInt(scanner.nextLine());
			if (opcion == 0) {
				irAlMenu();	
			} else {
				confirmarPrestamo(opcion);
			}
		} catch (NumberFormatException e) {
			System.out.println("\nIngresa el número a la par de la película que desees");
			prestarPelicula();
		}
	}

	public void confirmarPrestamo(int pelicula){
		System.out.println("\nQué cliente prestará la película?");
		listarClientes(true);
		System.out.println("0.Regresar");	
		try{
			int opcion = Integer.valueOf(scanner.nextLine());
			if (opcion == 0) {
				irAlMenu();	
			} else {
				System.out.println("\nPor cuantos días se rentará la película?");		
				int dias = Integer.valueOf(scanner.nextLine());
				System.out.println("\nSe le ha prestado a "+clientes[opcion-1][0]+" La pelicula " + peliculas[pelicula-1][1] + " por " + dias +" dias.");
				prestamos[contPrestamos] = new String[]{peliculas[pelicula-1][0],clientes[opcion][1],dias+""};
				contPrestamos++;
				peliculas[pelicula-1][4] = "false";
				clientes[opcion-1][3] = "true";
			}
		} catch (NumberFormatException e) {
			System.out.println("\nIngresa el número a la par del nombre del cliente");
			confirmarPrestamo(pelicula);
		}
		irAlMenu();
	}

	public void listarClientes(boolean ocupados){
		int cont = 0;
		if (clientes[0][0] != null) {
			if (!ocupados) {
				while((clientes[cont][0] != (null))){
					System.out.println((cont+1)+".Nombre: "+clientes[cont][0]);
					System.out.println("  ID: "+clientes[cont][1]);
					System.out.println("  Teléfono: "+clientes[cont][2]);
					if (Boolean.parseBoolean(clientes[cont][3])) {
						System.out.println("  Tiene un prestamo activo");
					} else {
						System.out.println("  No tiene prestamos activos");
					}
					cont++;
					irAlMenu();
				}
			} else {
				while (clientes[cont][0] != null) {
					if (!(Boolean.parseBoolean(clientes[cont][3]))) {
						System.out.println((cont+1)+"."+clientes[cont][0]);	
					}
					cont++;
				}
			}
		} else {
			System.out.println("\nAún no hay nada aquí\n");
			irAlMenu();
		}
	}

	public void listarPeliculas(boolean disponibles){
		int cont = 0;
		if (peliculas[0][0] != null) {
			if (!disponibles) {
				while((peliculas[cont][0] != (null))){
					motrarInfoPelicula(cont);
					cont++;
				}
				irAlMenu();
			} else {
				while((peliculas[cont][0]!=(null))&&cont < 30){
					if ((Boolean.parseBoolean(peliculas[cont][4]))) {
						System.out.println((cont+1) + "." + peliculas[cont][1]);	
					}
					cont++;
				}
			}
		} else {
			System.out.println("\nAún no hay nada aquí\n");
			irAlMenu();
		}
	}

	public void motrarInfoPelicula(int cont){
		System.out.println("\n"+(cont+1) + ".Nombre: " + peliculas[cont][1]);
		System.out.println("  ID: "+peliculas[cont][0]);
		System.out.println("  Año de Estreno: "+peliculas[cont][2]);
		System.out.println("  Categoria: "+peliculas[cont][3]);
		if (Boolean.parseBoolean(peliculas[cont][4])) {
			System.out.println("  Esta disponible");
		} else {
			System.out.println("  Esta rentada");
		}
	}

	public void agregarPelicula(){
		if (contPeliculas<30) {
			try{
			int id = 1000;
			System.out.println("\nIngresa el Nombre de la Película");
			String nombre = scanner.nextLine();
			System.out.println("\nIngresa el Año de su Estreno");
			int fecha = Integer.valueOf(scanner.nextLine());
			int categoria = elegirCategoria();
			peliculas[contPeliculas++] = new String[]{(contPeliculas+id)+"",nombre,fecha+"",CATEGORIAS[categoria],"true"};
			System.out.println("\nLa pelicula se agregó con éxito");
			irAlMenu();
			} catch (NumberFormatException | InputMismatchException x){
				System.out.println("\nIngresaste un valor inválido");
				agregarPelicula();
			}
		} else {
			System.out.println("Se ha alcanzado el máximo de películas");
			irAlMenu();
		}
	}

	public int elegirCategoria() throws NumberFormatException{
		listarCategorias();
		int opcion = Integer.parseInt(scanner.nextLine());
		while(opcion < 0 && opcion > 6){
			System.out.println("\nNo tenemos esa categoría");
			opcion = scanner.nextInt();
		}
		return (opcion-1);
	}

	public void listarCategorias(){
		System.out.println("\nElige su categoría:");
		for (int i = 0;i < CATEGORIAS.length;i++) {
			System.out.println(""+(i+1)+"."+CATEGORIAS[i]);
		}
	}

	public void agregarCliente(){
		try{
		int id = 100000;
		System.out.println("\nIngresa el Nombre del Cliente");
		String nombre = scanner.nextLine();
		System.out.println("\nIngresa el número de Teléfono del Cliente");
		int telefono = Integer.valueOf(scanner.nextLine());
		clientes[contClientes] = new String[]{nombre,(contClientes+id)+"",telefono+"","false"};
		contClientes++;
		System.out.println("\nEl Cliente se agregó con éxito");
		irAlMenu();
		} catch (NumberFormatException | InputMismatchException x) {
			System.out.println("\nIngresa un número de teléfono apropiado");
			agregarCliente();
		}
	}

	public void ordenarAlfabeticamente(int indiceMuestra,String[][] array,int length){
		for (int i = 0;i < length;i++) {
			for (int j = i + 1;j < length;j++) {
					if (array[i][indiceMuestra].compareTo(array[j][indiceMuestra]) > 0) {
						String[] temp = array[i];
						array[i] = array[j];
						array[j] = temp;
					}
				}	
		}
	}

	public void devolverPelicula(){
		System.out.println("\nIngresa el ID de la pélicula prestada");
		String idPeli = scanner.nextLine();
		int indicePeli = buscarValor(idPeli,peliculas,0);
		if (indicePeli==-1) {
			System.out.println("\nEl ID ingresado no existe");
			irAlMenu();
		} else if (peliculas[indicePeli][4].equals("true")){
			System.out.println("Esta película no está rentada actualmente");
			irAlMenu();
		} else{
			System.out.println("\nIngresa el ID del cliente que regresó la película");	
			String idCliente = scanner.nextLine();
			int indiceCliente = buscarValor(idCliente,clientes,1);
			if (indiceCliente==-1) {
				System.out.println("\nEl ID ingresado no existe");
				irAlMenu();
			} else if (clientes[indiceCliente][3].equals("false")) {
				System.out.println("Este cliente no tiene ninguna película rentada actualmente");
			} else {
				peliculas[indicePeli][4] = "true";
				clientes[indiceCliente][3] = "false";
				System.out.println("Se ha devuelto la pelicula y esta disponible ");
			}
			irAlMenu();
		}
	}           

	public int buscarValor(String referencia,String[][] array,int indice){
		int ubicacion = -1;
		for (int i = 0;i < array.length;i++) {
			if (referencia.equals(array[i][indice])) {
				ubicacion = i;
				break;
			}
		}
		return ubicacion;
	}


	public static void main(String[] args) {
		Memorabilia ex = new Memorabilia();
	}
}
