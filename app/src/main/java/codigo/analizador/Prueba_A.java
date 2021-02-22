package codigo.analizador;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.view.View;

import java.util.ArrayList;

import static android.graphics.Paint.Style.FILL;

public class Prueba_A extends View {
    public int no_instruccion = 0;
    private Canvas can;
    private Paint pinc;
    public ArrayList<Instruccion> inst = new ArrayList<>();
    public ArrayList<Instruccion> para_animar = new ArrayList<>();

    int iCurStep = 0;

    public Prueba_A(Context contexto){
        super(contexto);
    }

    public Prueba_A(Context contexto, ArrayList<Instruccion> figuras){
        super(contexto);
        this.inst = figuras;
        Instruccion temporal;
        for (int i = 1; i<inst.size(); i++) {
            temporal = inst.get(i);
            if (!temporal.isGraficar()) {
                Instruccion b = inst.get(i - 1);
                if (b.isGraficar()) {
                    b.getParametros().add(temporal.getParametros().get(0));
                    b.getParametros().add(temporal.getParametros().get(1));
                    b.getParametros().add(temporal.getParametros().get(2));
                    para_animar.add(b);
                }
            }
        }
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Paint pincel1 = new Paint();
        pincel1.setColor(Color.BLACK);
        this.can = canvas;
        this.pinc = pincel1;
        this.pinc.setStyle(FILL);
        animandoFiguras(para_animar);
        iCurStep++;
        invalidate();
    }

    public void animandoFiguras(ArrayList<Instruccion> figuras){
        if (figuras.size()>0 && no_instruccion < figuras.size()){
            Instruccion nueva = figuras.get(no_instruccion);
            ArrayList<String> param = nueva.getParametros();
            switch (nueva.getFigura()){
                case "circulo":
                    agregarCirculo(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),
                            Integer.parseInt(param.get(2)),param.get(3),Integer.parseInt(param.get(4)),
                            Integer.parseInt(param.get(5)),param.get(6));
                    break;
                case "cuadrado":
                    agregarRectangulo(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),
                            Integer.parseInt(param.get(2)),Integer.parseInt(param.get(2)),param.get(3),
                            Integer.parseInt(param.get(4)),Integer.parseInt(param.get(5)),param.get(6));
                    break;
                case "rectangulo":
                    agregarRectangulo(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),
                            Integer.parseInt(param.get(2)),Integer.parseInt(param.get(3)),param.get(4),
                            Integer.parseInt(param.get(5)),Integer.parseInt(param.get(6)),param.get(7));
                    break;
                case "linea":
                    agregarLinea(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),
                            Integer.parseInt(param.get(2)),Integer.parseInt(param.get(3)),param.get(4),
                            Integer.parseInt(param.get(5)),Integer.parseInt(param.get(6)),param.get(7));
                    break;
                case "poligono":
                    agregarPoligono(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),
                            Integer.parseInt(param.get(2)),Integer.parseInt(param.get(3)),Integer.parseInt(param.get(4)),param.get(5),
                            Integer.parseInt(param.get(6)),Integer.parseInt(param.get(7)),param.get(8));
                    break;
                default:
                    no_instruccion++;
                    break;
            }
        }
    }

    public void agregarCirculo(int posx, int posy, int radio, String color, int nuevax, int nuevay, String anima){
        cambiar_color(color);
        Path sPath = trayectoria(posx,posy,nuevax,nuevay,anima);
        PathMeasure pm = new PathMeasure(sPath, false);
        float fSegmentLen = pm.getLength() / 200;//we'll get 20 points from path to animate the circle
        float afP[] = {0f, 0f};
        pm.getPosTan(fSegmentLen * iCurStep, afP, null);
        can.drawCircle(afP[0],afP[1],radio,pinc);
        pinc.setTextSize(20);
        String animacion = "ANIMACION "+(no_instruccion+1)+"/"+para_animar.size();
        animacion+= "FIGURA: circulo - X: "+posx+" -> "+nuevax+" | Y: "+posy+" -> "+nuevay+" | TIPO: "+anima+" | COLOR: "+color;
        can.drawText(animacion, 10, 25, pinc);
        if (afP[0]==nuevax && afP[1]==nuevay){
            no_instruccion++;
            iCurStep = 0;
            can.drawCircle(nuevax,nuevay,radio,pinc);
        }
    }
    public void agregarRectangulo(int posx, int posy, int ancho, int alto, String color, int nuevax, int nuevay, String anima){
        cambiar_color(color);
        Path sPath = trayectoria(posx,posy,nuevax,nuevay,anima);
        PathMeasure pm = new PathMeasure(sPath, false);
        float fSegmentLen = pm.getLength() / 200;//we'll get 20 points from path to animate the circle
        float afP[] = {0f, 0f};
        pm.getPosTan(fSegmentLen * iCurStep, afP, null);
        can.drawRect(afP[0],afP[1],afP[0]+ancho,afP[1]+alto,pinc);
        pinc.setTextSize(20);
        String animacion = "ANIMACION "+(no_instruccion+1)+"/"+para_animar.size();
        animacion+= "FIGURA: rectangulo - X: "+posx+" -> "+nuevax+" | Y: "+posy+" -> "+nuevay+" | TIPO: "+anima+" | COLOR: "+color;
        can.drawText(animacion, 10, 25, pinc);
        if (afP[0]==nuevax && afP[1]==nuevay){
            no_instruccion++;
            iCurStep = 0;
        }
    }

    public void agregarLinea(int posx, int posy, int hastax, int hastay, String color, int nuevax, int nuevay, String anima){
        cambiar_color(color);
        Path sPath = trayectoria(posx,posy,nuevax,nuevay,anima);
        PathMeasure pm = new PathMeasure(sPath, false);
        float fSegmentLen = pm.getLength() / 200;//we'll get 20 points from path to animate the circle
        float afP[] = {0f, 0f};
        pm.getPosTan(fSegmentLen * iCurStep, afP, null);
        can.drawLine(afP[0],afP[1],afP[0]+hastax,afP[1]+hastay,pinc);
        pinc.setTextSize(20);
        String animacion = "ANIMACION "+(no_instruccion+1)+"/"+para_animar.size();
        String animacion2 = "FIGURA: linea - X: "+posx+" -> "+nuevax+" | Y: "+posy+" -> "+nuevay+" | TIPO: "+anima+" | COLOR: "+color;
        can.drawText(animacion, 10, 20, pinc);
        can.drawText(animacion2, 10, 40, pinc);
        if (afP[0]==nuevax && afP[1]==nuevay){
            no_instruccion++;
            iCurStep = 0;
        }
    }


    public void agregarPoligono(int x, int y, int ancho, int alto, int lados, String color, int nuevax, int nuevay, String anima){
        cambiar_color(color);
        Path sPath = trayectoria(x,y,nuevax,nuevay,anima);
        PathMeasure pm = new PathMeasure(sPath, false);
        float fSegmentLen = pm.getLength() / 200;//we'll get 20 points from path to animate the circle
        float afP[] = {0f, 0f};
        pm.getPosTan(fSegmentLen * iCurStep, afP, null);
        switch (lados){
            case 3:
                can.drawPath(agregarTriangulo(afP[0],afP[1],ancho,alto),pinc);
                break;
            case 4:
                can.drawRect(afP[0],afP[1],afP[0]+ancho,afP[1]+alto,pinc);
                break;
            case 5:
                can.drawPath(agregarCinco(afP[0],afP[1],ancho,alto),pinc);
                break;
            case 6:
                can.drawPath(agregarHex(afP[0],afP[1],ancho,alto),pinc);
                break;
            case 7:
                can.drawPath(agregarHep(afP[0],afP[1],ancho,alto),pinc);
                break;
            case 8:
                can.drawPath(agregarOctagono(afP[0],afP[1],ancho,alto),pinc);
                break;
            default:
                break;
        }
        if (lados>8){
            agregarPoligono2(afP[0],afP[1],ancho,alto,lados,color);
        }
        pinc.setTextSize(20);
        String animacion = "ANIMACION "+(no_instruccion+1)+"/"+para_animar.size();
        String animacion2 = "FIGURA: poligono - X: "+x+" -> "+nuevax+" | Y: "+y+" -> "+nuevay+" | TIPO: "+anima+" | COLOR: "+color;
        can.drawText(animacion, 10, 20, pinc);
        can.drawText(animacion2, 10, 40, pinc);
        if (afP[0]==nuevax && afP[1]==nuevay){
            no_instruccion++;
            iCurStep = 0;
        }
    }

    public Path agregarTriangulo(float x, float y, float ancho, float alto){
        Path path = new Path();
        path.moveTo(x,y);
        path.lineTo(x+ancho,y+ (float)alto/2);
        path.lineTo(x,y+alto);
        path.close();
        return path;
    };

    public Path agregarCinco(float x, float y, float ancho, float alto){
        Path path = new Path();
        path.moveTo(x,y);
        path.lineTo(x+((2*ancho)/3),y);
        path.lineTo(x+ancho,y+(alto/3));
        path.lineTo(x+ancho,y+(alto));
        path.lineTo(x,y+alto);
        path.close();
        return path;
    }
    public Path agregarHex(float x, float y, float ancho, float alto){
        Path path = new Path();
        path.moveTo(x,y);
        path.lineTo(x+(2*ancho)/3,y);
        path.lineTo(x+ancho,y+(alto/3));
        path.lineTo(x+ancho,y+(2*alto)/3);
        path.lineTo(x+(2*ancho)/3,y+(alto));
        path.lineTo(x,y+alto);
        path.close();
        return path;
    }

    public Path agregarHep(float x, float y, float ancho, float alto){
        Path path = new Path();
        path.moveTo(x,y);
        path.lineTo(x+(2*ancho)/3,y);
        path.lineTo(x+ancho,y+(alto/3));
        path.lineTo(x+ancho,y+(2*alto)/3);
        path.lineTo(x+(2*ancho)/3,y+(alto));
        path.lineTo(x+(ancho/3),y+alto);
        path.lineTo(x,y+(2*alto)/3);
        path.close();
        return path;
    }
    public Path agregarOctagono(float x, float y, float ancho, float alto){
        Path path = new Path();
        path.moveTo(x+(ancho/3),y);
        path.lineTo(x+(2*ancho)/3,y);
        path.lineTo(x+ancho,y+(alto/3));
        path.lineTo(x+ancho,y+(2*alto)/3);
        path.lineTo(x+(2*ancho)/3,y+(alto));
        path.lineTo(x+(ancho/3),y+alto);
        path.lineTo(x,y+(2*alto)/3);
        path.lineTo(x,y+(alto/3));
        path.close();
        return path;
    }

    public void agregarPoligono2(float x, float y, int ancho, int alto, float sides, String color) {
        cambiar_color(color);
        boolean anticlockwise = true;
        float startAngle = 0;

        float radius = 1;
        if (sides < 3) { return; }

        float a = ((float) Math.PI *2) / sides * (anticlockwise ? -1 : 1);
        can.save();
        can.translate(x+(float)ancho/2, y+(float)alto/2);
        can.rotate(startAngle);
        Path path = new Path();
        path.moveTo(radius, 0);
        for(int i = 1; i < sides; i++) {
            path.lineTo(radius * (float) Math.cos(a * i), radius * (float) Math.sin(a * i));
        }
        path.close();
        Matrix scaleMatrix = new Matrix();
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        scaleMatrix.setScale((float)ancho/2,(float)alto/2);
        path.transform(scaleMatrix);
        can.drawPath(path, pinc);
        can.restore();
    }

    public Path trayectoria(int x, int y, int nx, int ny, String anima){
        Path sPath = new Path();
        sPath.moveTo(x,y);
        if (anima.equalsIgnoreCase("curva")){
            if (y==ny){
                sPath.cubicTo(x, y,(x+nx)/2,y+(y+ny)/2,nx,ny);
            } else {
                sPath.cubicTo(x, y,(x+nx)/2+(nx-x)/2,(y+ny)/2,nx,ny);
            }
        } else {
            sPath.lineTo(nx, ny);
        }
        return sPath;
    }

    public Canvas getCan() {
        return can;
    }

    public void setCan(Canvas can) {
        this.can = can;
    }

    public void cambiar_color(String color){
        switch (color){
            case "azul":
                pinc.setColor(Color.BLUE);
                break;
            case "verde":
                pinc.setColor(Color.GREEN);
                break;
            case "amarillo":
                pinc.setColor(Color.YELLOW);
                break;
            case "rojo":
                pinc.setColor(Color.RED);
                break;
            case "morado":
                pinc.setColor(Color.rgb(128,0,128));
                break;
            case "naranja":
                pinc.setColor(Color.rgb(225, 165, 0));
                break;
            case "negro":
                pinc.setColor(Color.BLACK);
                break;
            case "cafe":
                pinc.setColor(Color.rgb(165,42,42));
                break;
            default:
                pinc.setColor(Color.WHITE);
                break;
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = 3000;
        int height = 3000 + 50; // Since 3000 is bottom of last Rect to be drawn added and 50 for padding.
        setMeasuredDimension(width, height);
    }

}
