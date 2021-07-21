import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class ViewRTree extends JFrame {

	private final int leftMargin=25;
	private final int bottomMargin=25;
	private int maxY;
	private int maxX;
	private static int counter = 0;

	public ViewRTree(LlistaPAED<RectanglePare> mainPares, int maxX, int maxY, LlistaPAED<ObjectesMapa> n, boolean isDeleted){
			this.maxX = 900;
			this.maxY = maxY+40;

			LlistaPAED<LlistaPAED<RectanglePare>> tots = new LlistaPAED<>();

			printarRectangles(tots, mainPares, true, counter);
			Line2D x = new Line2D.Float(leftMargin, maxY+20-bottomMargin, 900-leftMargin, maxY+20-bottomMargin);
			Line2D y = new Line2D.Float(leftMargin, maxY+20-bottomMargin, leftMargin, 20);

			Rectangles r = new Rectangles(tots, x, y, n, this.maxY, counter);
			JLabel nom = new JLabel();
			Font font = new Font("Courier", Font.BOLD,20);
			nom.setFont(font);
			if(isDeleted){
				nom.setText("Després de Eliminació");
			}else{
				nom.setText("Abans de Eliminació");
			}
			nom.setBounds(400, 50, 40, 100);
			r.add(nom);
			this.getContentPane().add(r);
			setSize(this.maxX, this.maxY);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setResizable(false);
			setTitle("R-Tree | Lídia Figueras - Marti Ejarque - Victor Xirau - Adrià Pajares");
	}



	private void printarRectangles(LlistaPAED<LlistaPAED<RectanglePare>> tots, LlistaPAED<RectanglePare> pares, boolean inici, int counter){
		if(pares == null || pares.size() == 0){
			return;
		}

		if(inici){
			for(RectanglePare p : pares){
					tots.add(new LlistaPAED<>());
			}
		}

		for(RectanglePare p : pares){
			if(inici){
				this.counter++;
				tots.get(this.counter-1).add(p);
				printarRectangles(tots, p.getPares(), false, this.counter-1);
			}else{
				tots.get(counter).add(p);
				printarRectangles(tots, p.getPares(), false, counter);
			}
		}

	}




}
