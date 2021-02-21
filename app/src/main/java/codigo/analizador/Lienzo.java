package codigo.analizador;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.view.View;

import java.util.ArrayList;

import static android.graphics.Paint.Style.FILL;
import static android.graphics.Paint.Style.STROKE;

public class Lienzo extends View {

    public Canvas canv;
    public Paint pinc;
    public ArrayList<Instruccion> inst;

    int iCurStep = 0;
    int iCurStepY = 0;
    public Lienzo(Context context){
        super(context);
    }

    public Lienzo(Context context,ArrayList<Instruccion> figuras){
        super(context);
        this.inst = figuras;
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int ancho = getWidth();
        int alto = getHeight();
        Paint pincel1 = new Paint();
        pincel1.setColor(Color.BLACK);
        this.canv = canvas;
        this.pinc = pincel1;
        this.pinc.setStyle(FILL);
        dibujando_Trayectorias(100,100,500,100);
        dibujandoFiguras(inst);
        iCurStep++;
        iCurStepY++;
        invalidate();
    }
    public void agregarCirculo(){
        canv.drawCircle(20, 40, 10, pinc);
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
                    System.out.println("pintado cuadrado");
                    break;
                case "linea":
                    agregarLinea(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),Integer.parseInt(param.get(2)),Integer.parseInt(param.get(3)),param.get(4));
                    System.out.println("pintada linea");
                    break;
                case "poligono":
                    agregarPoligono(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),Integer.parseInt(param.get(2)),Integer.parseInt(param.get(3)),0,false,param.get(5));
                    System.out.println("pintado poligono");
                    break;
                case "rectangulo":
                    agregarRectangulo(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),Integer.parseInt(param.get(2)),Integer.parseInt(param.get(3)),param.get(4));
                    System.out.println("pintado rectangulo");
                    break;
                default:
                    System.out.println("no se pinto nada");
                    break;
            }
        }
    }

    public void agregarCirculo(int posx, int posy, int radio, String color){
        cambiar_color(color);
        canv.drawCircle(posx, posy, radio, pinc);
    }

    public void agregarLinea(int posx, int posy, int posxx, int posyy,String color){
        cambiar_color(color);
        canv.drawLine(posx, posy, posxx, posyy, pinc);
    }

    public void agregarPoligono(float x, float y, float radius, float sides, float startAngle, boolean anticlockwise, String color) {
        cambiar_color(color);
        if (sides < 3) { return; }

        float a = ((float) Math.PI *2) / sides * (anticlockwise ? -1 : 1);
        canv.save();
        canv.translate(x, y);
        canv.rotate(startAngle);
        Path path = new Path();
        path.moveTo(radius, 0);
        for(int i = 1; i < sides; i++) {
            path.lineTo(radius * (float) Math.cos(a * i), radius * (float) Math.sin(a * i));
        }
        path.close();

        canv.drawPath(path, pinc);
        canv.restore();
    }

    public void agregarPoligono(int x, int y, int ancho, int alto, int lados, String color){
        cambiar_color(color);
        int nuevo_ancho = ancho+ x;
        int nuevo_alto = alto+y;
        Path path = new Path();
        Path dos = new Path();
        dos.moveTo(x,y);
        dos.lineTo(x+200,y);
        path.moveTo(x,y);
        path.lineTo(nuevo_ancho,y);
        path.lineTo(nuevo_ancho,nuevo_alto);
        path.lineTo(x,nuevo_alto);
        path.close();
    }

    public void agregarRectangulo(int x, int y, int ancho, int alto, String color){
        cambiar_color(color);
        canv.drawRect(x,y,ancho,alto,pinc);
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

    public void dibujando_Trayectorias(int x, int y, int nx, int ny){
        Path sPath = new Path();
        if (y==ny){
            sPath.moveTo(x,y);
            sPath.cubicTo(x, y,(x+nx)/2,y+(y+ny)/2,nx,ny);
        } else {
            sPath.cubicTo(x, y,(x+nx)/2+(nx-x)/2,(y+ny)/2,nx,ny);
        }
        Paint pathPaint = new Paint();
        pathPaint.setColor(Color.BLUE);

        PathMeasure pm = new PathMeasure(sPath, false);
        float fSegmentLen = pm.getLength() / 200;//we'll get 20 points from path to animate the circle
        float afP[] = {0f,0f};

        pm.getPosTan(fSegmentLen * iCurStep, afP, null);
        canv.drawPath(sPath,pathPaint);
        canv.drawCircle(afP[0],afP[1],20,pathPaint);
        canv.drawLine(afP[0],afP[1],200+afP[0],200+afP[1],pathPaint);
        canv.drawRect(afP[0],afP[1],afP[0]+200,afP[1]+200,pathPaint);
        //canv.drawRect(afP[0],afP[1],0f,0f,pathPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = 3000;
        int height = 3000 + 50; // Since 3000 is bottom of last Rect to be drawn added and 50 for padding.
        setMeasuredDimension(width, height);
    }

}
