import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Principal {
    private static LlistaPAED<ObjectesMapa> oMapa;
    private static LlistaPAED<ObjectesTenda> oTenda;
    private static LlistaPAED<Jugador> jugadors;
    private static ArbreBST arbre;
    private static ArbresR rtree;
    private static Habitacio[] habitacions;
    private static Connection[] connections;


	public static void llistaFitxers(final String pattern, final File folder, LlistaPAED<String> result) {
		File[] files = folder.listFiles();
		if(files!=null){
			for (int i=0; i<files.length ;i++) {
				if (files[i].isDirectory()) {
					llistaFitxers(pattern, files[i], result);
				}
				if (files[i].isFile()) {
					if (files[i].getName().matches(pattern)) {
						result.add(files[i].getName());
					}
				}
			}
		}

	}

    public static void main(String[] args) throws FileNotFoundException {


        Path path = Paths.get("resources/");
        String datasets = path.toAbsolutePath().toString();

		Node deleted = null;
		String s=null;
		String pos=null;
		int n = 0;
		boolean sortir = false;
		while(!sortir){
			System.out.println("Quina fase vols consultar?");
			System.out.println("\t 1. Fase 1");
			System.out.println("\t 2. Fase 2");
			System.out.println("\t 3. Fase 3");
			System.out.println("\t 4. Sortir");
			System.out.print("Tria: ");
			Scanner sca = new Scanner(System.in);
			int fase = sca.nextInt();
			Path current2 = Paths.get(datasets+"/F" + fase + "/" + "/");
			String camino = current2.toAbsolutePath().toString();

			switch(fase) {
				case 1:
					System.out.println("\n------------------------- F1 -----------------------------");

					System.out.println("\n\nQuins datasets vols consultar? (Lletra/num)");
					System.out.println("\t1. S\n\t2. M\n\t3. L");
					System.out.print("Tria: ");
					Scanner f = new Scanner(System.in);
					char fitxer = f.next().charAt(0);
          System.out.println("\n");

					String lletra;
					switch (fitxer) {
						case '1': case 's': case 'S':
							lletra = "S";
							break;
						case '2': case 'm': case 'M':
							lletra = "M";
							break;
						case '3': case 'l': case 'L':
							lletra = "L";
							break;
						default:
							lletra = "K";
							break;
					}

					BufferedReader readerF1_C = new BufferedReader(new FileReader(camino + "/Connection" + lletra + ".json"));
					BufferedReader readerF1_R = new BufferedReader(new FileReader(camino + "/Room" + lletra + ".json"));
					Gson gsonF1 = new GsonBuilder().create();

					habitacions = gsonF1.fromJson(readerF1_R, Habitacio[].class);
					connections = gsonF1.fromJson(readerF1_C, Connection[].class);

					Graph[][] graph = new Graph[habitacions.length][habitacions.length];

					for(int i  = 0; i < habitacions.length; i++){
						for(int j = 0; j < habitacions.length; j++){
							graph[i][j] = new Graph(false, 0, false);
							graph[i][j].setProb((int)Double.POSITIVE_INFINITY);
						}
					}

					Dijkstra dijkstra = new Dijkstra(graph);
					dijkstra.createGraph(graph, connections);

					System.out.print("Introdueix l'habitació inicial (num): ");
					Scanner scanner = new Scanner(System.in);
					int habitacioInicialEnter = scanner.nextInt();
					System.out.print("Introdueix l'habitació final (num): ");
					Scanner scanner2 = new Scanner(System.in);
					int habitacioFinalEnter = scanner2.nextInt();

					System.out.println("\n-------------------------------------------------------------------");
					Habitacio habitacioInicial = new Habitacio();
					Habitacio habitacioFinal = new Habitacio();

					for(int i = 0; i < habitacions.length; i++){
						if(habitacioInicialEnter == habitacions[i].getId()){
							habitacioInicial.setId(habitacions[i].getId());
							habitacioInicial.setRoomName(habitacions[i].getRoomName());
						}
						if(habitacioFinalEnter == habitacions[i].getId()){
							habitacioFinal.setId(habitacions[i].getId());
							habitacioFinal.setRoomName(habitacions[i].getRoomName());
						}
					}


					Habitacio[] camiOptim = new Habitacio[connections.length];

					for (int i = 0; i < connections.length ; i++) {
						camiOptim[i] = new Habitacio();
					}

					LlistaPAED<Integer> probTotal = new LlistaPAED<>();
					probTotal.add(0);

					long start = System.currentTimeMillis();
					dijkstra.dijkstra(graph, habitacioInicial, habitacioFinal,  habitacions, camiOptim, connections, probTotal);
					long end = System.currentTimeMillis();
					long elapsedTime = end - start;

					System.out.println("-------------------------------------------------------------------");
					System.out.println("L'algorisme ha trigat "+ elapsedTime + " mil·lisegons.");
					System.out.println("La probabilitat total de recorregut es: " + probTotal.get(0));
					System.out.println("-------------------------------------------------------------------\n\n");

					System.out.println("\n------------------------ EXITING F1 ----------------------------\n\n");

					break;

				case 2:
					boolean exitF2 = false;

					while (!exitF2) {
						System.out.println("\n------------------------- F2 -----------------------------");
						System.out.println("\nBenvinguts a la Fase 2. Que voldries buscar? (num)");
						System.out.println("\t 1. Objecte a la tenda");
						System.out.println("\t 2. Objecte al mapa");
						System.out.println("\t 3. Sortir");
						System.out.print("Tria: ");
						sca = new Scanner(System.in);
						int num = sca.nextInt();
						String karpeta = "";
						Gson f2Gson = new GsonBuilder().create();
						BufferedReader f2Reader = null;
						if (num < 3) {
							switch (num) {
								case 1:
									karpeta = "/Object/";
									break;
								case 2:
									karpeta = "/Map/";
									break;
							}
							LlistaPAED<String> fitxersF2 = new LlistaPAED<>();
							int fitxerF2 = demanaDataset(camino + karpeta, fitxersF2);
							Path currentF2 = Paths.get(camino + karpeta + "/" + fitxersF2.get(fitxerF2 - 1));
							String arxiuF2 = currentF2.toAbsolutePath().toString();

							f2Reader = new BufferedReader(new FileReader(arxiuF2));

							switch (num) {
								case 1:
									oTenda = LlistaPAED.listFromArray(f2Gson.fromJson(f2Reader, ObjectesTenda[].class));
									break;
								case 2:
									oMapa = LlistaPAED.listFromArray(f2Gson.fromJson(f2Reader, ObjectesMapa[].class));
									for (int i = 0; i < oMapa.size(); i++) {
									oMapa.get(i).makeCoordinates();
									}
									break;
							}
						}
						boolean sortirTenda = false;

						switch (num) {
							case 1:
								arbre = new ArbreBST(oTenda.size(), new Node(oTenda.get(0)));
								for (int i = 1; i < oTenda.size(); i++) {
									arbre.inserir(arbre.getArrel(), new Node(oTenda.get(i)));
								}
								Node[] r = new Node[oTenda.size()];
								arbre.inordre(arbre.getArrel(), r);
								while (!sortirTenda) {
									System.out.println("Que vols fer? ");
									System.out.println("\t1. Veure arbre ");
									System.out.println("\t2. Eliminar node ");
									System.out.println("\t3. Sortir ");
									System.out.print("Tria: ");
									n = sca.nextInt();
									switch (n) {
										case 1:
											System.out.println("\nCom vols veure l'arbre?");
											System.out.println("\t 1. Inordre");
											System.out.println("\t 2. Representació");
											System.out.print("Tria: ");
											int visArbre = sca.nextInt();
											System.out.println();
											switch(visArbre){
												case 1:
													arbre.setIndexInordre(0);
													r = null;
													r = new Node[arbre.getTotalNodes()];
													arbre.inordre(arbre.getArrel(), r);
													for (int i = 0; i < r.length; i++) {
														System.out.println(r[i].getObjecteTenda().toString());
													}
													break;
												case 2:
													arbre.printarAbreHoritzontal();
													System.out.println("\n");
													break;
											}
											break;
										case 2:
											while (deleted == null) {
												System.out.println("\nPer quin criteri vols buscar l'objecte? (num)");
												System.out.println("\t 1. Per nom");
												System.out.println("\t 2. Per preu");
												System.out.print("Tria: ");
												n = sca.nextInt();
												System.out.println("\n-----------------------------------------------------------");
												switch (n) {
													case 1:
														System.out.println("Quin nom té l'objecte que vols buscar? ");
														Scanner esc = new Scanner(System.in);
														s = esc.nextLine();
														break;
													case 2:
														System.out.println("Quin preu té l'objecte que vols buscar?");
														Scanner sc = new Scanner(System.in);
														s = sc.nextLine();
														break;
													default:
														System.out.println("Siusplau, selecciona una de les opcions del menú, gràcies");
														break;
												}
												if (n == 1) {
													for (int i = 0; i < r.length; i++) {
														if (r[i].getObjecteTenda().getName().equalsIgnoreCase(s)) {
															deleted = r[i];
														}
													}
												} else {
													for (int i = 0; i < r.length; i++) {
														if (r[i].getObjecteTenda().getPrice() == Integer.parseInt(s)) {
															deleted = r[i];
														}
													}
												}
												if (deleted == null) {
													System.out.println("Aquest element no existeix al arbre!\n");
												}
												System.out.println("-----------------------------------------------------------");
											}
											System.out.println("\t\tNode eliminat!");
											System.out.println("-----------------------------------------------------------\n");

											long start3 = System.nanoTime();
											arbre.setIndexInordre(0);
											arbre.deleteKey(deleted);
											long end3 = System.nanoTime();
											long elapsedTime3 = end3 - start3;
											System.out.println("TEMPS: " + elapsedTime3);
											break;
										case 3:
											sortirTenda = true;
											System.out.println("\nSortint...\n");
											break;
										default:
											System.out.println("Opció no vàlida");
											break;
									}
								}
								break;

							case 2:

								rtree = new ArbresR(oMapa);
								boolean sortirMapa = false;
								long start5 = System.nanoTime();
								rtree.makeTreeFromPoints();
								long end5 = System.nanoTime();
								long elapsedTime5 = end5 - start5;
								System.out.println("\n----------------------- R-TREE CREAT ---------------------------");
								System.out.println("\tTemps per creació: " + elapsedTime5);
								System.out.println("----------------------------------------------------------------\n");

								while (!sortirMapa) {
									System.out.println("\nQue vols fer? (num)");
									System.out.println("\t 1. Veure R-Tree");
									System.out.println("\t 3. Eliminar Node");
									System.out.println("\t 4. Sortir");
									System.out.print("Tria: ");
									n = sca.nextInt();

									switch (n) {
										case 1:
											rtree.viewHorizontalRTree();
											break;
										case 2:
											double x1, y1;
											System.out.println("\nEn quina posició es troba el teu objecte? (x1,y1)");
											Scanner sc = new Scanner(System.in);
											pos = sc.nextLine();

											String[] p1 = pos.split(",");

											x1 = Double.parseDouble(p1[0]);
											y1 = Double.parseDouble(p1[1]);
											long start4 = System.nanoTime();
											ObjectesMapa o = rtree.deleteObject(new Point2D.Double(x1, y1));
											long end4 = System.nanoTime();
											long elapsedTime4 = end4 - start4;
											System.out.println("\nTEMPS ELIMINACIÓ: " + elapsedTime4);
											System.out.println("S'ha eliminat l'objecte: " + o.getId());
											break;
										case 4:
											sortirMapa = true;
											System.out.println("\nSortint...\n");
											break;
										default:
											System.out.println("Opció no vàlida");
											break;
									}
								}

								break;

							case 3:
								exitF2 = true;
								System.out.println("\nTornant al menú principal...\n");
								System.out.println("\n------------------------ EXITING F2 ----------------------------\n\n");
								break;
							default:
								System.out.println("Opció no vàlida");
								break;
						}
					}
					break;

				case 3:
					System.out.println("\n------------------------- F3 -----------------------------");

					LlistaPAED<String> fitxersF3 = new LlistaPAED<>();
					int fitxerF3 = demanaDataset(camino, fitxersF3);
					Path currentF3 = Paths.get(camino + "/" + fitxersF3.get(fitxerF3-1));
					String arxiuF3 = currentF3.toAbsolutePath().toString();

					System.out.println("\n---------------------- Cerca Jugador ----------------------");


					BufferedReader reader = new BufferedReader(new FileReader(arxiuF3));
					Gson gsonJugadors = new GsonBuilder().create();
					jugadors = LlistaPAED.listFromArray(gsonJugadors.fromJson(reader, Jugador[].class));
					System.out.println("Benvinguts a la Fase 3. Quin usuari vols buscar? (Player X)");
					sca = new Scanner(System.in);
					String usuari = sca.nextLine();

					long iniciHash = System.nanoTime();
					PAEDHash<String, Jugador> mapah = new PAEDHash<>();
					for(Jugador j : jugadors){
						mapah.put(j.getName(), j);
					}
					long endingHash = System.nanoTime();
					long tempsTotalHash = endingHash - iniciHash;
					System.out.println("\n\n-------------------------------------------------------------------");
					System.out.println("\tTemps en crear el hash: " + tempsTotalHash);
					System.out.println("-------------------------------------------------------------------\n\n");


					long inici = System.nanoTime();
					Jugador result = mapah.get(usuari);
					long ending = System.nanoTime();
					long tempsTotal = ending - inici;
					if(result!=null){
						System.out.println("\n------------------------- RESULTAT -----------------------------");
						System.out.println("\tInformació del jugador: ");
            			System.out.println("\tName: " + usuari);
						System.out.println("\tKDA: " + result.getKDA());
						System.out.println("\tGames: " + result.getGames());
						System.out.println("-------------------------------------------------------------------");
						System.out.println("\tTemps en fer la cerca: " + tempsTotal);
						System.out.println("-------------------------------------------------------------------\n\n");
					}else{
						System.out.println("Aquest jugador no existeix al dataset");
					}

						System.out.println("\n------------------------ EXITING F3 ----------------------------\n\n");
					break;
				case 4:
					System.out.println("Gràcies per fer servir el programa!");
					sortir = true;
					break;
				default:
					System.out.println("Aquesta opció no està disponible");
					break;
			}

		}
    }

	private static int demanaDataset(String camino, LlistaPAED<String> fitxerss) {
		System.out.println("\n\nQuin dataset vols consultar? (num) \n");
		final File folder = new File(camino);
		llistaFitxers(".*\\.json", folder, fitxerss);
		int numeroh = 1;
		for (String string : fitxerss) {
			System.out.print(numeroh + ":" + string + "   ");
			numeroh++;
		}
		System.out.print("\n\nTria: ");

		Scanner in = new Scanner(System.in);
		int fitxer = in.nextInt();
		return fitxer;
	}
}
