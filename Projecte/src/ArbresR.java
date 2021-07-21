import java.awt.geom.*;

public class ArbresR {

	private RectanglePare root;
	private LlistaPAED<ObjectesMapa> allObjects;

	private int cont = 1;

	public ArbresR( LlistaPAED<ObjectesMapa> allObjects) {
		this.allObjects = allObjects;
	}

	public RectanglePare getRoot() {
		return root;
	}

	public void setRoot(RectanglePare root) {
		this.root = root;
	}

	public LlistaPAED<ObjectesMapa> getAllObjects() {
		return allObjects;
	}

	public void setAllObjects(LlistaPAED<ObjectesMapa> allObjects) {
		this.allObjects = allObjects;
	}

	public boolean hitOrMiss(Point2D coordenada, LlistaPAED<Point2D> r){
		boolean found = false;
			if(coordenada.getX() >= r.get(0).getX() && coordenada.getX() <= r.get(1).getX()){
					if(coordenada.getY() <= r.get(0).getY() && coordenada.getY() >= r.get(1).getY()){
							found = true;
					}
			}
			return found;
	}

	public ObjectesMapa buscaObjecte(Point2D coordenada){
		boolean v = false;
		RectanglePare brancaRoot;
		ObjectesMapa objectoh = null;


		for(RectanglePare r : this.root.getPares()){
			if(hitOrMiss(coordenada, r.getVertexs()) && objectoh == null){
				RectanglePare explorador = r;
				RectanglePare next = null;

				while(explorador.hasPares() && explorador != null){
					for(RectanglePare h : explorador.getPares()){
						if(hitOrMiss(coordenada, h.getVertexs()) && objectoh == null){
							next = h;
						}
					}
					explorador = next;
				}
				if(explorador!=null){
					for(ObjectesMapa g : explorador.getNodes()){
						if(hitOrMiss(coordenada, g.getCoordinates()) && objectoh == null){
							objectoh = g;
						}
					}
				}
			}
		}

		return objectoh;
	}

	public ObjectesMapa buscaObjecte(int id){
		ObjectesMapa k=null;
		for (ObjectesMapa o : this.allObjects){
			if(o.getId()==id){
				k=o;
			}
		}
		return k;
	}

	public ObjectesMapa deleteObject(int id){
		ObjectesMapa o = buscaObjecte(id);
		if(o!=null){
			this.allObjects.remove(o);
			System.out.println("HIT!");
			makeTreeFromPoints();
			System.out.println("Object Deleted :D");
		}else{
			System.out.println("Aquest objecte no existeix al arbre");
			System.out.println("MISS");
		}
		cont = 1;

		return o;

	}

	public ObjectesMapa deleteObject(Point2D coordenada){
		ObjectesMapa o = buscaObjecte(coordenada);
		if(o!=null){
			this.allObjects.remove(o);
			System.out.println("\nHIT!");
			makeTreeFromPoints();
		}else{
			System.out.println("\nMISS");
		}
		cont = 1;
		return o;
	}

	public double getDistance (RectanglePare pare, RectanglePare nou){

		double distance;
		double min = Double.MAX_VALUE;

		LlistaPAED<Point2D> coordenades1 = new LlistaPAED<>();

		coordenades1.addAll(pare.getVertexs());
		coordenades1.add(new Point2D.Double(pare.getVertexs().get(1).getX(),pare.getVertexs().get(0).getY()));
		coordenades1.add(new Point2D.Double(pare.getVertexs().get(0).getX(),pare.getVertexs().get(1).getY()));

		LlistaPAED<Point2D> coordenades2 = new LlistaPAED<>();

		coordenades2.addAll(nou.getVertexs());
		coordenades2.add(new Point2D.Double(nou.getVertexs().get(1).getX(),nou.getVertexs().get(0).getY()));
		coordenades2.add(new Point2D.Double(nou.getVertexs().get(0).getX(),nou.getVertexs().get(1).getY()));


			for(Point2D p: coordenades1){
				for(Point2D p2: coordenades2){

					distance =  Math.sqrt((p2.getX()-p.getX())*(p2.getX()-p.getX()) + (p2.getY()-p.getY())*(p2.getY()-p.getY()));

					if(distance < min){
						min = distance;
					}
				}
		}
		return min;
	}

	public void clearVisited(LlistaPAED<RectanglePare> rectangles){
		for(RectanglePare r : rectangles){
			r.setVisited(false);
		}
	}

	public boolean isNotMaxMin(RectanglePare min){
		boolean yes = true;
		if(min.getVertexs().get(0).getX()==Integer.MAX_VALUE && min.getVertexs().get(0).getY()==Integer.MAX_VALUE && min.getVertexs().get(1).getX()==Integer.MAX_VALUE && min.getVertexs().get(1).getY()==Integer.MAX_VALUE){
			yes = false;
		}
		return yes;
	}


	public void makeTreeFromPoints(){
		LlistaPAED<RectanglePare> rectangles = new LlistaPAED<>();
		LlistaPAED<RectanglePare> nova = new LlistaPAED<>();
		LlistaPAED<Point2D> t = new LlistaPAED<>();
		t.add(new Point2D.Double(Integer.MAX_VALUE, Integer.MAX_VALUE));
		t.add(new Point2D.Double(Integer.MAX_VALUE, Integer.MAX_VALUE));
		RectanglePare min = new RectanglePare(t);
		double minDistance = Double.MAX_VALUE;
		double actualDistance;


		for (ObjectesMapa o : this.allObjects){

				RectanglePare p = new RectanglePare();
				p.addNode(o);
				o.makeCoordinates();
				p.setVertexs(o.getCoordinates());
				rectangles.add(p);
				o.setPare(p);

		}

		while (!optimumReached(rectangles)){
			for (RectanglePare o : rectangles){
					for (RectanglePare o2 : rectangles){
							if (!o2.equals(o) && !o2.ifFullNodes() && o2.notVisited() ){
								actualDistance = getDistance(o, o2);
								if (actualDistance < minDistance){
									if (o.area(o2) < o.area(min)){

										minDistance = actualDistance;
										min = o2;

									}
								}
							}
					}

					if(isNotMaxMin(min)){
						o.getNodes().addAll(min.getNodes());
						o.setupGermans();
						rectangles.remove(min);
						o = updateCoordenates(o);
						o.setVisited(true);
						min.setVertexs(t);
						minDistance = Double.MAX_VALUE;
					}
			}
			clearVisited(rectangles);
		}

			//Busquem si s'ha quedat algun sol i l'agrupem
			int index = 0;
			for(RectanglePare o: rectangles){
				if(!o.ifFullNodes()){

					//buscar a rectangles el més pròxim al que queda suelto
					for (int i= 0; i<rectangles.size() ;i++){
						if (!rectangles.get(i).equals(o) && !rectangles.get(i).ifFullNodes() && rectangles.get(i).notVisited()) {
							actualDistance = getDistance(o, rectangles.get(i));
							if (actualDistance < minDistance){
								if (o.area(rectangles.get(i)) < o.area(min)){

									minDistance = actualDistance;
									index = i;

								}
							}
						}
					}//for o2


					rectangles.get(index).getNodes().addAll(o.getNodes());
					rectangles.get(index).setupGermans();
					rectangles.add(index, updateCoordenates(rectangles.get(index)));
					rectangles.remove(o);
				}
			}

			clearVisited(rectangles);

			min.setVertexs(t);
			minDistance = Double.MAX_VALUE;

			LlistaPAED<RectanglePare> papiChulos = new LlistaPAED<>();
			papiChulos.addAll(rectangles);
			LlistaPAED<RectanglePare> TMP = new LlistaPAED<>();

			while(!optimumFathersReached(papiChulos)){
				TMP.removeAll();
				TMP.addAll(papiChulos);
				papiChulos.removeAll();
				for(RectanglePare p : TMP){

					RectanglePare k = new RectanglePare(p.getVertexs());
					k.setPares(new LlistaPAED<>()); //Inicialitza la llista de pares
					p.setPare(k); //Fa que el pare de p sigui k, crea la relació pare-fill
					k.addPare(p); //Afegeix el pare p(que té els dos nodes) com el seu unic fill
					papiChulos.add(k); //Afegeix k (que ara te com a fill p) a la llista de papichulos

				}


//---------------------------------------------AQUI ESTA EL FALLO-------------------------------------------------------------------
				for (RectanglePare o : papiChulos){
					for (RectanglePare o2 : papiChulos){
						if (!o2.equals(o) && !o2.ifFullPares() && !o.ifFullPares() && o2.notVisited()){ //get pare... pilotes -- no hauriem de necessitar agafar el pare
							actualDistance = getDistance(o, o2); //Retorna valor numèric de la distància
								if (actualDistance < minDistance){
										if (o.area(o2) < o.area(min)){
											minDistance = actualDistance;
											min = o2;
										}
								}
						}
					}

					if(isNotMaxMin(min)){
						LlistaPAED<RectanglePare> papitos = new LlistaPAED<>();
						papitos.addAll(o.getPares());
						papitos.addAll(min.getPares());
						o.setPares(papitos);
						papiChulos.remove(min);
						o.setVisited(true);
						min.setVertexs(t);
						minDistance = Double.MAX_VALUE;
						o = updateCoordenates(o);
					}

			}//for o : papiChulos
//------------------------------------------------------------------------------------------------------------------------------
 			clearVisited(rectangles);
		}//while



		setUpRoot(papiChulos);
	}//funció


	public RectanglePare updateCoordenates(RectanglePare papi){
		double minX= Double.MAX_VALUE, maxX=0, minY= Double.MAX_VALUE, maxY=0;
		RectanglePare aux2 = null;
		RectanglePare aux = null;

		if(papi.hasPares()){

			aux2 = new RectanglePare(papi.getPares().get(0));

			try {
				aux = (RectanglePare) aux2.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}


		if(papi.getNodes()!=null && papi.getNodes().size()>0){
			for (ObjectesMapa o : papi.getNodes()){
				if(o.getX1()<minX){
					minX = (double) o.getX1();
				}
				if(o.getY1()>maxY){
					maxY = (double) o.getY1();
				}
				if(o.getX2()>maxX){
					maxX = (double) o.getX2();
				}
				if(o.getY2()<minY){
					minY = (double) o.getY2();
				}
			}
		}else if(papi.getPares()!=null && papi.getPares().size()>0){
			for (RectanglePare o : papi.getPares()){
				if(o.getVertexs().get(0).getX()<minX){
					minX = o.getVertexs().get(0).getX();
				}
				if(o.getVertexs().get(0).getY()>maxY){
					maxY = o.getVertexs().get(0).getY();
				}
				if(o.getVertexs().get(1).getX()>maxX){
					maxX = o.getVertexs().get(1).getX();
				}
				if(o.getVertexs().get(1).getY()<minY){
					minY = o.getVertexs().get(1).getY();
				}
			}
		}

		LlistaPAED<Point2D> dale = new LlistaPAED<>();
		dale.add(new Point2D.Double(minX, maxY));
		dale.add(new Point2D.Double(maxX, minY));
		papi.setVertexs(dale);


		if(aux!=null){
			papi.getPares().add(0, aux);
		}

		return papi;
	}

	private void setUpRoot(LlistaPAED<RectanglePare> end){
		this.root = new RectanglePare();

		for(RectanglePare r : end){
			r.setPare(this.root);
			this.root.addPare(r);
		}
	}

	private boolean optimumReached(LlistaPAED<RectanglePare> rectangles){
		boolean ok=true;
		int suma2 = 0;
		int suma1 = 0;
		if (this.allObjects.size()%2 == 0){
			for (int i = 0; i < rectangles.size(); i++ ) {
				if (rectangles.get(i).getNodes().size() < 2) {
					ok=false;
				}
			}
		} else {
			for (int i = 0; i < rectangles.size(); i++ ) {
				if (rectangles.get(i).ifFullNodes()) {
					suma2++;
				} else{
					suma1++;
				}
			}
			if(suma2 != rectangles.size() - 1 || suma1 != 1){
				ok = false;
			}

		}

		return ok;

	}



	private boolean optimumFathersReached(LlistaPAED<RectanglePare> all){
		return all.size()<=3;
	}

	public void viewTree(boolean deleted){
		ViewRTree r = new ViewRTree(this.root.getPares(), getMaxX()+20, getMaxY()+20, this.allObjects, deleted);
		r.setVisible(true);
	}


	private int getMaxX(){
		int max = 0;
		for(ObjectesMapa y : this.allObjects){
			if(y.getX2()>max){
				max = y.getX2();
			}
		}
		return max;
	}

	private int getMaxY(){
		int max = 0;
		for(ObjectesMapa y : this.allObjects){
			if(y.getY1()>max){
				max = y.getY1();
			}
		}
		return max;
	}

		public void viewHorizontalRTree() {
				System.out.print(navegar(root));
				System.out.println("\n");
		}

		public String navegar(RectanglePare arrel) {

				if (arrel == null) {
						return "";
				}

				StringBuilder sb = new StringBuilder();
				sb.append("Root");

				String pointerRight = "└──";
				String pointerLeft = (arrel.getPares().get(0) != null) ? "├──" : "└──";

				for(RectanglePare p : arrel.getPares()){
					RecorrerArbre(sb, "", pointerLeft, arrel, p, null, p.hasPares());
				}

				return sb.toString();
		}

		public void RecorrerArbre(StringBuilder sb, String espais, String punter, RectanglePare previous, RectanglePare node, ObjectesMapa obj, boolean teRight) {
				if (node != null) {
						sb.append("\n");
						sb.append(espais);
						if(!esUltim(previous, node)){
							sb.append(punter);
						}else{
							sb.append("└──");
						}
						sb.append("Coordenades: (" + node.getVertexs().get(0).getX() + ", " +  node.getVertexs().get(0).getY() + ") | (" +node.getVertexs().get(1).getX() + ", " + node.getVertexs().get(1).getY() + ") | Area: " + areaPare(node.getVertexs()));

						StringBuilder paddingBuilder = new StringBuilder(espais);
						String pointerLeft = (node.hasPares() ) ? "├──" : "└──";

						if (!esUltim(previous, node)) {
								paddingBuilder.append("│  ");
						}else {
								paddingBuilder.append("   ");
						}

						String paddingForBoth = paddingBuilder.toString();

						if(node.hasNodes()) {
							for (ObjectesMapa p : node.getNodes()) {
								RecorrerArbre(sb, paddingForBoth, pointerLeft, node, null, p, true);
							}
						}else{
							for(RectanglePare p : node.getPares()){
								RecorrerArbre(sb, paddingForBoth, pointerLeft, node, p, null, p.hasPares());
							}
						}



				}else{
					sb.append("\n");
					sb.append(espais);
					sb.append(punter);
					sb.append("Coordenades: (" + obj.getCoordinates().get(0).getX() + ", " +  obj.getCoordinates().get(0).getY() + ") | (" +obj.getCoordinates().get(1).getX() + ", " + obj.getCoordinates().get(1).getY() + ") | Area: " + areaPare(obj.getCoordinates()));
				}
		}

		public Double areaPare(LlistaPAED<Point2D> vertexs){

			//HAZ TU MAGIA LIDIA <3


			double minX, minY, maxX, maxY;

			if(vertexs.get(0).getX() <= vertexs.get(1).getX()){
				minX = vertexs.get(0).getX();
				maxX = vertexs.get(1).getX();
			} else {
				minX = vertexs.get(1).getX();
				maxX = vertexs.get(0).getX();
			}


			if(vertexs.get(0).getY() <= vertexs.get(1).getY()){
				minY = vertexs.get(0).getY();
				maxY = vertexs.get(1).getY();
			} else {
				minY = vertexs.get(1).getY();
				maxY = vertexs.get(0).getY();
			}

			return (maxX - minX) * (maxY - minY);

		}

		private boolean esUltim(RectanglePare previous, RectanglePare node){

			if(previous.getPares().get(previous.getPares().size()-1).equals(node)){
				return true;
			}else{
				return false;
			}

		}


}
