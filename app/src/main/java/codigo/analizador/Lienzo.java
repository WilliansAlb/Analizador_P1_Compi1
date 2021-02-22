package codigo.analizador;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.view.View;

import java.util.ArrayList;

import static android.graphics.Paint.Style.FILL;
import static android.graphics.Paint.Style.STROKE;

public class Lienzo extends View {

    public Canvas canv;
    public Paint pinc;
    private boolean animados = false;
    public int no_instruccion = 0;
    public ArrayList<Instruccion> inst = new ArrayList<>();
    public ArrayList<Instruccion> para_animar = new ArrayList<>();

    public boolean isAnimados() {
        return animados;
    }

    public void setAnimados(boolean animados) {
        this.animados = animados;
    }

    int iCurStep = 0;
    int iCurStepY = 0;
    public Lienzo(Context context){
        super(context);
    }

    public Lienzo(Context context,ArrayList<Instruccion> figuras){
        super(context);
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

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if (!animados){
            int ancho = getWidth();
            int alto = getHeight();
            Paint pincel1 = new Paint();
            pincel1.setColor(Color.BLACK);
            this.canv = canvas;
            this.pinc = pincel1;
            this.pinc.setStyle(FILL);
            dibujandoFiguras(inst);
        } else {
            Paint pincel1 = new Paint();
            pincel1.setColor(Color.BLACK);
            this.canv = canvas;
            this.pinc = pincel1;
            this.pinc.setStyle(FILL);
            dibujandoFiguras(inst);
            animandoFiguras(para_animar);
            iCurStep++;
            if (no_instruccion==para_animar.size()){
                animados = false;
                iCurStep = 0;
                no_instruccion = 0;
            }
            invalidate();
        }
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

    public void dibujandoFiguras(ArrayList<Instruccion> figuras){
        for (int i = 0; i < figuras.size(); i++){
            Instruccion nueva = figuras.get(i);
            ArrayList<String> param = nueva.getParametros();
            switch (nueva.getFigura()){
                case "circulo":
                    agregarCirculo(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),Integer.parseInt(param.get(2)),param.get(3));
                    break;
                case "cuadrado":
                    agregarRectangulo(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),Integer.parseInt(param.get(2)),Integer.parseInt(param.get(2)),param.get(3));
                    break;
                case "linea":
                    agregarLinea(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),Integer.parseInt(param.get(2)),Integer.parseInt(param.get(3)),param.get(4));
                    break;
                case "poligono":
                    agregarPoligono(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),
                            Integer.parseInt(param.get(2)),Integer.parseInt(param.get(3)),Integer.parseInt(param.get(4)),param.get(5));
                    break;
                case "rectangulo":
                    agregarRectangulo(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),Integer.parseInt(param.get(2)),Integer.parseInt(param.get(3)),param.get(4));
                    break;
                default:
                    System.out.println("no se pinto nada");
                    break;
            }
        }
    }

    public void agregarCirculo(int posx, int posy, int radio, String color){
        if (animados){
            pinc.setColor(Color.LTGRAY);
        } else {
            cambiar_color(color);
        }
        canv.drawCircle(posx, posy, radio, pinc);
    }

    public void agregarLinea(int posx, int posy, int posxx, int posyy,String color){
        if (animados){
            pinc.setColor(Color.LTGRAY);
        } else {
            cambiar_color(color);
        }
        canv.drawLine(posx, posy, posxx, posyy, pinc);
    }


    public void agregarPoligono2(float x, float y, int ancho, int alto, float sides, String color, boolean animar) {
        if (!animar && animados){
            pinc.setColor(Color.LTGRAY);
        } else {
            cambiar_color(color);
        }
        boolean anticlockwise = true;
        float startAngle = 0;

        float radius = 1;
        if (sides < 3) { return; }

        float a = ((float) Math.PI *2) / sides * (anticlockwise ? -1 : 1);
        canv.save();
        canv.translate(x+(float)ancho/2, y+(float)alto/2);
        canv.rotate(startAngle);
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
        canv.drawPath(path, pinc);
        canv.restore();
    }

    public void agregarPoligono(int x, int y, int ancho, int alto, int lados, String color){
        if (animados){
            pinc.setColor(Color.LTGRAY);
        } else {
            cambiar_color(color);
        }
        switch (lados){
            case 3:
                canv.drawPath(agregarTriangulo(x,y,ancho,alto),pinc);
                break;
            case 4:
                agregarRectangulo(x,y,ancho,alto,color);
                break;
            case 5:
                canv.drawPath(agregarCinco(x,y,ancho,alto),pinc);
                break;
            case 6:
                canv.drawPath(agregarHex(x,y,ancho,alto),pinc);
                break;
            case 7:
                canv.drawPath(agregarHep(x,y,ancho,alto),pinc);
                break;
            case 8:
                canv.drawPath(agregarOctagono(x,y,ancho,alto),pinc);
                break;
            default:
                break;
        }
        if (lados>8){
            agregarPoligono2(x,y,ancho,alto,lados,color,false);
        }
    }

    public Path agregarTriangulo(int x, int y, int ancho, int alto){
        Path path = new Path();
        path.moveTo(x,y);
        path.lineTo(x+ancho,y+ (float)alto/2);
        path.lineTo(x,y+alto);
        path.close();
        return path;
    };

    public Path agregarCinco(int x, int y, int ancho, int alto){
        Path path = new Path();
        path.moveTo(x,y);
        path.lineTo(x+((2*ancho)/3),y);
        path.lineTo(x+ancho,y+(alto/3));
        path.lineTo(x+ancho,y+(alto));
        path.lineTo(x,y+alto);
        path.close();
        return path;
    }
    public Path agregarHex(int x, int y, int ancho, int alto){
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

    public Path agregarHep(int x, int y, int ancho, int alto){
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
    public Path agregarOctagono(int x, int y, int ancho, int alto){
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

    public void agregarRectangulo(int x, int y, int ancho, int alto, String color){
        if (animados){
            pinc.setColor(Color.LTGRAY);
        } else {
            cambiar_color(color);
        }
        canv.drawRect(x,y,x+ancho,y+alto,pinc);
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

    //METODOS PARA ANIMAR
    public void agregarCirculo(int posx, int posy, int radio, String color, int nuevax, int nuevay, String anima){
        cambiar_color(color);
        Path sPath = trayectoria(posx,posy,nuevax,nuevay,anima);
        PathMeasure pm = new PathMeasure(sPath, false);
        float fSegmentLen = pm.getLength() / 200;//we'll get 20 points from path to animate the circle
        float afP[] = {0f, 0f};
        pm.getPosTan(fSegmentLen * iCurStep, afP, null);
        canv.drawCircle(afP[0],afP[1],radio,pinc);
        pinc.setTextSize(20);
        dibujar_informacion(posx,posy,nuevax,nuevay,anima,color,"circulo");
        if (afP[0]==nuevax && afP[1]==nuevay){
            no_instruccion++;
            iCurStep = 0;
            canv.drawCircle(nuevax,nuevay,radio,pinc);
        }
    }

    public void dibujar_informacion(int posx, int posy, int nuevax, int nuevay, String anima, String color, String tipo){
        pinc.setTextSize(20);
        pinc.setColor(Color.BLACK);
        String animacion = "ANIMACION "+(no_instruccion+1)+"/"+para_animar.size();
        String animacion2 = "FIGURA: "+tipo+" - X: "+posx+" -> "+nuevax+" | Y: "+posy+" -> "+nuevay+" | ANIMACION: "+anima+" | COLOR: "+color;
        canv.drawText(animacion, 10, 20, pinc);
        canv.drawText(animacion2, 10, 40, pinc);
    }

    public void agregarRectangulo(int posx, int posy, int ancho, int alto, String color, int nuevax, int nuevay, String anima){
        cambiar_color(color);
        Path sPath = trayectoria(posx,posy,nuevax,nuevay,anima);
        PathMeasure pm = new PathMeasure(sPath, false);
        float fSegmentLen = pm.getLength() / 200;//we'll get 20 points from path to animate the circle
        float afP[] = {0f, 0f};
        pm.getPosTan(fSegmentLen * iCurStep, afP, null);
        canv.drawRect(afP[0],afP[1],afP[0]+ancho,afP[1]+alto,pinc);
        dibujar_informacion(posx,posy,nuevax,nuevay,anima,color,"rectangulo");
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
            canv.drawLine(afP[0],afP[1],afP[0]+(hastax-posx),afP[1]+(hastay-posy),pinc);
        dibujar_informacion(posx,posy,nuevax,nuevay,anima,color,"linea");
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
                canv.drawPath(agregarTriangulo(afP[0],afP[1],ancho,alto),pinc);
                break;
            case 4:
                canv.drawRect(afP[0],afP[1],afP[0]+ancho,afP[1]+alto,pinc);
                break;
            case 5:
                canv.drawPath(agregarCinco(afP[0],afP[1],ancho,alto),pinc);
                break;
            case 6:
                canv.drawPath(agregarHex(afP[0],afP[1],ancho,alto),pinc);
                break;
            case 7:
                canv.drawPath(agregarHep(afP[0],afP[1],ancho,alto),pinc);
                break;
            case 8:
                canv.drawPath(agregarOctagono(afP[0],afP[1],ancho,alto),pinc);
                break;
            default:
                break;
        }
        if (lados>8){
            agregarPoligono2(afP[0],afP[1],ancho,alto,lados,color,true);
        }
        dibujar_informacion(x,y,nuevax,nuevay,anima,color,"poligono");
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


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = 3000;
        int height = 3000 + 50; // Since 3000 is bottom of last Rect to be drawn added and 50 for padding.
        setMeasuredDimension(width, height);
    }

}
