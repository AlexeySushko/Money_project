package money.project.view;

import money.project.Constants;
import money.project.Model.Methods;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

/**
 * Created by Alexey Sushko.
 * Класс в котором прорисуется график Прибыль/Затраты по дням
 */
public class GraphicFrame2 extends JPanel {

    private int maxValue = Methods.maxDebit;//чтобы правильно задать длинну координаты Y
    private int minValue = Methods.maxCredit;//чтобы правильно задать длинну координаты Y
    private int longY;//длинна координаты Y

    public static int howMuchDay = Methods.newListDay.size();//для того что бы знать какой длинны делатьХ. будет умножено на раст. м-ду делениями
    public static int [] xArrayM = new int[Methods.newListDay.size()];//Координаты дней
    //Ниже массивы координат для доход расход
    public static int [] yArrayMDebit = new int[Methods.newListDay.size()];//Координаты дней
    public static int [] yArrayMCredit = new int[Methods.newListDay.size()];//координаты сумм, это коорд Y
    public  static int centerY;
    public static int longX = (howMuchDay * 20) + 20 + 10;//20-шаг между днями 20- отступ, 10 запас по длинне


    /**
     * Метод который прорисует сам график
     * @param gr
     */
    public void paint(Graphics gr){
        //Расчет длинны оси
        if (minValue < 0){
            longY = ( maxValue - minValue) + 20 +10 +10;//20-отступ 10+10 запас по длинне
        }
        if (minValue >= 0){
            longY = maxValue + minValue + 20 +10;//20-отступ 10 запас по длинне
        }
        Graphics2D g = (Graphics2D)gr;
        Color oldColor = g.getColor();

        centerY= maxValue + 30;// координаты НУЛЯ по игрику, это начало координаты Х
        //Создаем оси
        g.drawLine(20, 20, 20, longY);// Y---4-е значение зависит от maxПриход + maxУбыток + 40(на каждую сторону запас для красоты)
        g.drawLine(20, centerY, longX, centerY);// X---2-e и 4-e будет нулевым иксом = максЗнач + 30
        // 3-е значение отвечает за длинну икса = (длинна листа дней * 20) + 10 запаса + 20 снала отступ

        //рисую сами стрелочки на иксе
        g.drawLine(longX - 10,centerY - 3, longX, centerY);
        g.drawLine(longX - 10,centerY + 3, longX, centerY);
        //И сразу по игрику стрелки добавляю
        g.drawLine(17, 30,20,20);
        g.drawLine(23, 30,20,20);
        g.drawString(Constants.TEXT_SUM, 5, 15);
        g.drawString(Constants.TEXT_DAYS, longX-15, centerY+15); //первое значение нулевой Х

        Color newColor = new Color(0, 0, 255);
        g.setColor(newColor);
        GeneralPath genPath = new GeneralPath();

        //Строим сам график
        //Делаю цикл записывающий координаты Х в зависимости от кол-ва дней
        genPath.moveTo(xArrayM[0], yArrayMDebit[0]);
        for(int j = 0; j < howMuchDay; j++){
            //Рисуем прибыль
            g.setStroke(new BasicStroke(10));
            g.setColor(new Color(28, 205, 16));
            g.draw(new Line2D.Float(xArrayM[j], centerY, xArrayM[j], centerY - yArrayMDebit[j]));
            g.setColor(new Color(0,0,0));
            g.drawString(String.valueOf(yArrayMDebit[j]), xArrayM[j]-10, centerY - yArrayMDebit[j]-7);

            //Рисуем затраты
            g.setColor(new Color(255, 34, 17));
            g.draw(new Line2D.Float(xArrayM[j], centerY, xArrayM[j], centerY + yArrayMCredit[j]));
            g.setColor(new Color(0,0,0));
            g.drawString("-" + String.valueOf(yArrayMCredit[j]), xArrayM[j]-15, centerY + yArrayMCredit[j]+15);
        }
        g.draw(genPath);
        g.setColor(oldColor);
        g.drawString(Constants.TEXT_SECOND_GRAPHIC, 180, 180);//Название графика с координатами размещения

    }

    public static void setLongX(int longX) {
        GraphicFrame2.longX = longX;
    }

    public static void setHowMuchDay(int howMuchDay) {
        GraphicFrame2.howMuchDay = howMuchDay;
    }
}