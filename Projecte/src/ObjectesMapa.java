import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class ObjectesMapa  {
    private int id;
    private int X1;
    private int Y1;
    private int X2;
    private int Y2;
		private RectanglePare pare;
		private ObjectesMapa germa1;
		private ObjectesMapa germa2;
		private LlistaPAED<Point2D> coordinates;


    public ObjectesMapa(int id, int x1, int y1, int x2, int y2) {
        this.id = id;
        X1 = x1;
        Y1 = y1;
        X2 = x2;
        Y2 = y2;
    }

    public void makeCoordinates(){

        this.coordinates = new LlistaPAED<>();
        this.coordinates.add(new Point2D.Double(this.X1, this.Y1));
        this.coordinates.add(new Point2D.Double(this.X2, this.Y2));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX1() {
        return X1;
    }

    public void setX1(int x1) {
        X1 = x1;
    }

    public int getY1() {
        return Y1;
    }

    public void setY1(int y1) {
        Y1 = y1;
    }

    public int getX2() {
        return X2;
    }

    public void setX2(int x2) {
        X2 = x2;
    }

    public int getY2() {
        return Y2;
    }

    public void setY2(int y2) {
        Y2 = y2;
    }

    public RectanglePare getPare() {
        return pare;
    }

    public void setPare(RectanglePare pare) {
        this.pare = pare;
    }

    public ObjectesMapa getGerma1() {
        return germa1;
    }

    public void setGerma1(ObjectesMapa germa1) {
        this.germa1 = germa1;
    }

    public ObjectesMapa getGerma2() {
        return germa2;
    }

    public void setGerma2(ObjectesMapa germa2) {
        this.germa2 = germa2;
    }

    public LlistaPAED<Point2D> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LlistaPAED<Point2D> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "id: " + id + " - X1: " + X1 + " - Y1:" + Y1 + " - X2:" + X2 + " - Y2:" + Y2;
    }

}
