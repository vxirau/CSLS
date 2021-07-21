import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.Comparator;

public class RectanglePare implements Cloneable {
		private boolean visited;
		private RectanglePare pare;
		private LlistaPAED<ObjectesMapa> nodes;
		private LlistaPAED<RectanglePare> pares;
		private LlistaPAED<Point2D> vertexs;


    public RectanglePare(RectanglePare pare, LlistaPAED<ObjectesMapa> nodes, LlistaPAED<RectanglePare> pares, LlistaPAED<Point2D> vertexs) {
        this.pare = pare;
        this.nodes = nodes;
        this.pares = pares;
        this.vertexs = vertexs;
    }


    public RectanglePare(LlistaPAED<ObjectesMapa> nodes, Object passada) {
        Object o = passada;
        this.nodes = nodes;
    }

		public RectanglePare(RectanglePare obj){
			this.pare = obj.getPare();
			this.nodes = obj.getNodes();
			this.pares = obj.getPares();
			this.vertexs = obj.getVertexs();
		}

    public RectanglePare(LlistaPAED<Point2D> vertexs) {
				LlistaPAED<Point2D> aux = new LlistaPAED<>();
				aux.addAll(vertexs);
        this.vertexs = aux;
    }

		public RectanglePare() {
        this.nodes = new LlistaPAED<>();
				this.pares = new LlistaPAED<>();
    }

    public RectanglePare getPare() {
        return pare;
    }
		public void addNode(ObjectesMapa r){
			this.nodes.add(r);
		}

    public void addPare(RectanglePare r){
			this.pares.add(r);
    }

		public boolean notVisited(){
			return !this.visited;
		}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public void setPare(RectanglePare pare) {
        this.pare = pare;
    }

    public LlistaPAED<ObjectesMapa> getNodes() {
        return nodes;
    }

    public void setNodes(LlistaPAED<ObjectesMapa> nodes) {
        this.nodes = nodes;
    }

    public LlistaPAED<RectanglePare> getPares() {
        return pares;
    }

    public void setPares(LlistaPAED<RectanglePare> pares) {
        this.pares = pares;
    }

    public LlistaPAED<Point2D> getVertexs() {
        return vertexs;
    }

    public void setVertexs(LlistaPAED<Point2D> vertexs) {
				LlistaPAED<Point2D> aux = new LlistaPAED<>();
				aux.addAll(vertexs);
        this.vertexs = aux;
    }

		public boolean hasNodes(){
			if(this.nodes == null){
				return false;
			}else{
				return this.nodes.size() != 0;
			}
		}

		public boolean hasPares(){
			if(this.pares == null){
				return false;
			}else{
				return this.pares.size() != 0;
			}
		}

		public boolean ifFullNodes(){
			return this.nodes.size() == 2;
		}

		public boolean ifFullPares(){
			return this.pares.size() == 2;
		}




		public double area(RectanglePare r1){

			//En cas que ens pasin 3 rectangles, molaria rebre un array de RectanglePare i fer un for each buscant els min i max values, per despres fer els calculs.
			double minX, minY, maxX, maxY;
			if(r1.getVertexs().get(0).getX() <= this.getVertexs().get(0).getX()){
				minX = r1.getVertexs().get(0).getX();
			} else {
				minX = this.getVertexs().get(0).getX();
			}
			if(r1.getVertexs().get(0).getY() <= this.getVertexs().get(0).getY()){
				minY = r1.getVertexs().get(0).getY();
			} else {
				minY = this.getVertexs().get(0).getY();
			}
			if(r1.getVertexs().get(1).getX() >= this.getVertexs().get(1).getX()){
				maxX = r1.getVertexs().get(1).getX();
			} else {
				maxX = this.getVertexs().get(1).getX();
			}
			if(r1.getVertexs().get(1).getY() >= this.getVertexs().get(1).getY()){
				maxY = r1.getVertexs().get(1).getY();
			} else {
				maxY = this.getVertexs().get(1).getY();
			}

			return (maxX - minX) * (maxY - minY);

		}

    public void orderLeftRight(){
			//Collections.sort(this.getNodes(), Comparator.comparingInt(ObjectesMapa::getX1));

			//Collections.sort(this.getNodes(), int::);
    	//Collections.sort(list, Collections.reverseOrder());
    }

		public Object clone() throws
	                CloneNotSupportedException
	    {
	        // Assign the shallow copy to new reference variable t
	        RectanglePare t = (RectanglePare)super.clone();


	        return t;
	    }

	public void setupGermans(){

    	orderLeftRight();

    			if(this.nodes.size() == 2){

    				this.nodes.get(0).setGerma2(this.nodes.get(1));
    				this.nodes.get(1).setGerma1(this.nodes.get(0));

    			} else if(this.nodes.size() == 3) {

    				this.nodes.get(0).setGerma2(this.nodes.get(1));
    				this.nodes.get(1).setGerma1(this.nodes.get(0));
    				this.nodes.get(1).setGerma2(this.nodes.get(2));
    				this.nodes.get(2).setGerma1(this.nodes.get(1));

    			}
    }


}
