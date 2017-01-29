package money.project.view;

import money.project.Constants;
import money.project.Model.Methods;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Created by Alexey Sushko.
 * Класс в котором полностью отрисовывается график баланса по дням
 */
public class GraphicFrame extends JPanel {

    private int maxValue = Methods.max;//чтобы правильно задать длинну координаты Y
    private int minValue = Methods.min;//чтобы правильно задать длинну координаты Y
    private int longY;//длинна координаты Y

    public static int howMuchDay = Methods.newListDay.size();//для того что бы знать какой длинны делатьХ. будет умножено на раст. м-ду делениями
    public static int[] xArrayM = new int[Methods.newListDay.size()];//Координаты дней
    public static int[] yArrayM = new int[Methods.newListDay.size()];//координаты сумм, это коорд Y
    public static String[] xDate = new String[Methods.newListDay.size()];//Номера дней которые буду показывать на графике
    public static int centerY;
    public static int longX = (howMuchDay * 20) + 20 + 10;//20-шаг между днями 20- отступ, 10 запас по длинне


    /**
     * Метод в котором рисуется график балланса по дням
     * @param graphics
     */
    public void paint(Graphics graphics) {
        //Расчет длинны оси
        if (minValue < 0) {
            longY = (maxValue - minValue) + 20 + 10 + 10;//20-отступ 10+10 запас по длинне
        }
        if (minValue >= 0) {
            longY = maxValue + 20 + 10;//20-отступ 10 запас по длинне
        }

        Graphics2D g = (Graphics2D) graphics;
        Color oldColor = g.getColor();

        centerY = maxValue + 30;// координаты НУЛЯ по игрику, это начало координаты Х
        //Создаем оси
        g.drawLine(20, 20, 20, longY);// Y---4-е значение зависит от maxПриход + maxУбыток + 40(на каждую сторону запас  по 20 для красоты)
        g.drawLine(20, centerY, longX, centerY);// X---2-e и 4-e будет нулевым иксом = максЗнач + 30
        //рисую сами стрелочки на иксе
        g.drawLine(longX - 10,centerY - 3, longX, centerY);
        g.drawLine(longX - 10,centerY + 3, longX, centerY);
        //И сразу по игрику стрелки добавляю
        g.drawLine(17, 30,20,20);
        g.drawLine(23, 30,20,20);
        //добавляю названия осей Y,X
        g.drawString(Constants.TEXT_SUM, 5, 15);
        g.drawString(Constants.TEXT_DAYS, longX, centerY + 15); //первое значение нулевой Х

        Color newColor = new Color(0, 0, 255);
        g.setColor(newColor);
        GeneralPath genPath = new GeneralPath();

        //Строим сам график
        //Делаю цикл записывающий координаты Х в зависимости от кол-ва дней
        genPath.moveTo(xArrayM[0], centerY - yArrayM[0]);
        for (int j = 0; j < howMuchDay; j++) {
            genPath.lineTo(xArrayM[j], centerY - yArrayM[j]);//создание точек по которым будет строиться график
            g.drawString("*", xArrayM[j] - 3, centerY - yArrayM[j] + 6);//покажем точки на графике
            g.drawString(String.valueOf(yArrayM[j]), xArrayM[j] + 5, centerY - yArrayM[j] + 10);//размещение текста балланса на графике
            //дорисуем черточки дней
            g.drawLine(xArrayM[j], centerY - 3, xArrayM[j], centerY + 3);
        }
        g.draw(genPath);
        g.setColor(oldColor);
        g.drawString(Constants.TEXT_FIRST_GRAPHIC, 180, 180);//Название графика с координатами размещения
    }

    public static void setLongX(int longX) {
        GraphicFrame.longX = longX;
    }

    public static void setHowMuchDay(int howMuchDay) {
        GraphicFrame.howMuchDay = howMuchDay;
    }
}
