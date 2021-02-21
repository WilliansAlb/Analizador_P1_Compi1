package codigo.analizador;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
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
        System.out.println("entra aca");
        for (int i = 1; i<inst.size(); i++) {
            temporal = inst.get(i);
            if (!temporal.isGraficar()) {
                Instruccion b = inst.get(i - 1);
                if (b.isGraficar()) {
                    b.getParametros().add(temporal.getParametros().get(0));
                    b.getParametros().add(temporal.getParametros().get(1));
                    b.getParametros().add(temporal.getParametros().get(2));
                    System.out.println(b);
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
                    agregarCirculo(Integer.parseInt(param.get(0)),Integer.parseInt(param.get(1)),Integer.parseInt(param.get(2)),param.get(3),Integer.parseInt(param.get(4)),Integer.parseInt(param.get(5)),param.get(6));
                    break;
                default:
                    no_instruccion++;
                    break;
            }
        }
    }

    public void agregarCirculo(int posx, int posy, int radio, String color, int nuevax, int nuevay, String anima){
        cambiar_color(color);
        Path sPath = new Path();
        sPath.moveTo(posx,posy);
        if (anima.equalsIgnoreCase("curva")){
            if (posy==nuevay){
                sPath.cubicTo(posx, posy,(posx+nuevax)/2,posy+(posy+nuevay)/2,nuevax,nuevay);
            } else {
                sPath.cubicTo(posx, posy,(posx+nuevax)/2+(nuevax-posx)/2,(posy+nuevay)/2,nuevax,nuevay);
            }
        } else {
            sPath.lineTo(nuevax, nuevay);
        }
        PathMeasure pm = new PathMeasure(sPath, false);
        float fSegmentLen = pm.getLength() / 200;//we'll get 20 points from path to animate the circle
        float afP[] = {0f, 0f};
        pm.getPosTan(fSegmentLen * iCurStep, afP, null);
        can.drawCircle(afP[0],afP[1],radio,pinc);
        System.out.println("Pos X: "+posx+" y Pos Y: "+posy+" anima: "+anima);
        if (afP[0]==nuevax && afP[1]==nuevay){
            no_instruccion++;
            iCurStep = 0;
            can.drawCircle(nuevax,nuevay,radio,pinc);
        } 
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
