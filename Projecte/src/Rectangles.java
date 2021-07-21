import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;


public class Rectangles extends JPanel {

    private LlistaPAED<LlistaPAED<RectanglePare>> all = new LlistaPAED<>();
    private LlistaPAED<ObjectesMapa> nodes = new LlistaPAED<>();
    private Line2D x;
    private Line2D y;
    private int total;

		private int maxY;

    public Rectangles(){
        all = new LlistaPAED<>();
    }

    public Rectangles(LlistaPAED<LlistaPAED<RectanglePare>> all, Line2D x, Line2D y, LlistaPAED<ObjectesMapa> nodes, int maxY, int total){
        this.all = all;
        this.x = x;
				this.maxY = maxY;
				this.total = total;
        this.y = y;
        this.nodes = nodes;
    }



    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        Random rand = new Random();

        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.black);

				Color[] coloreh = new Color[total];
				for(int i=0; i<total ;i++){
					float r = rand.nextFloat();
					float v = rand.nextFloat();
					float b = rand.nextFloat();
					while(validColor(r, v, b)){
							r = rand.nextFloat();
							v = rand.nextFloat();
							b = rand.nextFloat();
					}
					coloreh[i] = new Color(r, v, b);
				}

				for(int i=0; i<total-1 ;i++){
					g2d.setColor(coloreh[i]);
					for(RectanglePare r : all.get(i)){
							Rectangle2D.Double h = new Rectangle2D.Double(r.getVertexs().get(0).getX()+26, calculaY(r.getVertexs().get(1).getY())-4, (r.getVertexs().get(1).getX()-r.getVertexs().get(0).getX())+8, (r.getVertexs().get(0).getY()-r.getVertexs().get(1).getY())-32);
							g2d.draw(h);
					}
				}


        //g2d.setColor(Color.magenta);
				g2d.setStroke(new BasicStroke(2));
				g2d.setColor(Color.black);

				for(ObjectesMapa n : nodes){
						Rectangle2D.Double h = new Rectangle2D.Double(n.getCoordinates().get(0).getX()+30, calculaY(n.getCoordinates().get(1).getY()), (n.getCoordinates().get(1).getX()-n.getCoordinates().get(0).getX()), (n.getCoordinates().get(0).getY()-n.getCoordinates().get(1).getY())-40);
						g2d.draw(h);
        }
				g2d.setColor(Color.black);
				g2d.setStroke(new BasicStroke(5));
        g2d.draw(x);
        g2d.draw(y);

    }

		public boolean validColor(float r, float v, float b){
			boolean valid = false;
				if(r==0 && v==0 && b==0){
					valid = true;
				}else if(b==0){
					valid = true;
				}
				else if(v>200 || r>200){
					valid = true;
				}

			return valid;
		}

		private Double calculaY(Double y){
			return (this.maxY*0.01-30+72) - y*0.01;
		}
}
