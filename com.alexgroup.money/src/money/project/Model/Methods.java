package money.project.Model;

import money.project.Constants;
import money.project.Model.entity.Day;
import money.project.view.AddFrame;
import money.project.view.GraphicFrame;
import money.project.view.GraphicFrame2;
import money.project.view.MainFrameProgram;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexey Sushko.
 * В данном классе собраны все методы отвечающие за логику программы
 */
public class Methods {

    //для Connection
    public static List<Day> myListDay = new ArrayList<Day>();//Сюда помещаю все вытянутые из БД дни
    public static String connectParametr = "read";//параметр в который передаю слово запись(write) или чтeние(read),
    // а в методе Коннекта  будет два блока if в которых разный код будет, для записи и чтения соответственно. По
    // умолчанию read для чтения

    public static String operationDC = "";//значение debit/credit для записи в БД
    public static List<Day> newListDay = new ArrayList<Day>();//В котором не повторяются дни и хранится информация посчитанная
    public static List<Day> newListDayDebit = new ArrayList<Day>();//Список дней с прибылью
    public static List<Day> newListDayCredit = new ArrayList<Day>();//Список дней с затратами

    public static BigDecimal summaVDen = BigDecimal.valueOf(0);//Балланс в день
    public static BigDecimal debitVDen = BigDecimal.valueOf(0);//Доход в день
    public static BigDecimal creditVDen = BigDecimal.valueOf(0);//затраты в день

    public static List<Day> listForSum = new ArrayList<Day>();//балланс в день который будет хранится в массиве, он же
    //является координатами Y на графике
    public static List<Day> listForDebit = new ArrayList<Day>();//аналогичные координаты дохода
    public static List<Day> listForCredit = new ArrayList<Day>();// и аналогично затрат

    //Работа с ЛИСТОМ в методе workList
    public static BigDecimal profit = BigDecimal.valueOf(0);
    public static BigDecimal expenses = BigDecimal.valueOf(0);
    public static BigDecimal ballance = BigDecimal.valueOf(0);

    //Работа с координатами
    public static int max;
    public static int min;
    public static int maxDebit;
    public static int maxCredit;

    //Массив откуда будет браться значение в JList при нажатии на кнопки Все операции, Расход, Прибыль
    public static ArrayList<String> listProfit = new ArrayList<String>();
    public static ArrayList<String> listExpenses = new ArrayList<String>();
    public static ArrayList<String> listAll = new ArrayList<String>();//Создали для всех что бы не использовать myListDay,
    //так как потом мы не можем его очистить потому что к нему привязаны все значения


    /**
     * Метод подключения и записи в БД, тут сразу вытягиваются значения в myListDay, во умолчанию в этом методе
     * connectParametr = read для чтения из БД, но если его заменить на write то будет производиться запиь в БД
     */
    public static void Connection() {
        java.sql.Connection conn = null;
        Statement stmt = null;

        try{
            //STEP 2: Register JDBC driver
            Class.forName(Constants.JDBC_DRIVER);

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);

            //STEP 4: Execute a query
            stmt = conn.createStatement();
            String sql;

            //Чтение с БД
            if(connectParametr.equals("read")){

            sql = "SELECT costs_id, date, sum, carrency, operation, comment  FROM costs";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){

                int costs = rs.getInt("costs_id");
                Date date = rs.getDate("date");
                BigDecimal sum = rs.getBigDecimal("sum");
                String carrency = rs.getString("carrency");
                String operation = rs.getString("operation");
                String comment = rs.getString("comment");
                //Добавляем в myListDay ДНИ с параметрами
                myListDay.add(new Day(costs, date, sum, carrency, operation, comment));

            }

            //STEP 6: Clean-up environment
            rs.close();
            }

            //Запись в БД
            if(connectParametr.equals("write")){

                sql = "INSERT INTO costs(date, sum, carrency, operation, comment)" +
                        " values(?, ?, ?, ?, ?);";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, AddFrame.getNumberJText());
                statement.setString(2, AddFrame.getSumJText());
                statement.setString(3, "dollar");
                statement.setString(4, operationDC);
                statement.setString(5, AddFrame.getCommmentJText());
                statement.execute();

                //Обнуляем все значения. Так как после записи будем сразу читать снова инфу с БД
                myListDay = new ArrayList<Day>();
                newListDay = new ArrayList<Day>();
                //обнуляем значения что бы не дублировало одинаковые значения и не показывало ложный результат расчетов
                profit = BigDecimal.valueOf(0);
                expenses = BigDecimal.valueOf(0);
                ballance = BigDecimal.valueOf(0);
                MainFrameProgram.data = new ArrayList<String>();
                connectParametr = "read";// меняем на read что бы после записи могло считать в БД информацию
            }
            stmt.close();
            conn.close();

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }


    /**
     * Метод работает с вытянутыми значениями из БД, Делает расчет и присваивает параметры Прибыль Затраты Баланс
     */
    public static void workList(){
        int size = myListDay.size();

        for(int i = 0; i<size; i++ ){
            Day dayVal = myListDay.get(i);
            int y = MainFrameProgram.data.size();

            MainFrameProgram.data.add(y, " " + dayVal.getDate() +
                    " " + dayVal.getSum() +
                    " " + dayVal.getCarrency() +
                    " " + dayVal.getOperation() +
                    " " + dayVal.getComment());

            if(dayVal.getOperation().equals(Constants.TEXT_DEBIT)){
                profit = profit.add(dayVal.getSum());
                ballance = ballance.add(dayVal.getSum());
            }
            if(dayVal.getOperation().equals(Constants.TEXT_CREDIT)){
                expenses = expenses.add(dayVal.getSum());
                ballance = ballance.subtract(dayVal.getSum());
            }
        }
    }


    /**
     * Метод сортирующий полученое из БД по дате от самой "свежей" до старой
     */
    public static void sortedMyListDay(){
       Collections.sort(myListDay, new Comparator<Day>() {
           @Override
           public int compare(Day o2, Day o1) {
               return o1.getDate().compareTo(o2.getDate());
           }
       });
    }


    /**
     * Метод создающий фрейм где будет график балланса по дням
     */
    public static void createGraficFrame() {
        JPanel panel = new GraphicFrame();
        panel.setOpaque(true);
        JFrame mainFrame = new JFrame("Graphics Frame");
        mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        mainFrame.setContentPane(panel);
        mainFrame.setSize(600, 600);
        mainFrame.setVisible(true);
    }

    /**
     * Метод создающий фрейм где будет график суммарнй прибыли и затрат по дням
     */
    public static void createGraficFrame2() {
        JPanel panel = new GraphicFrame2();
        panel.setOpaque(true);
        JFrame mainFrame = new JFrame("Graphics Frame2");
        mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        mainFrame.setContentPane(panel);
        mainFrame.setSize(600, 600);
        mainFrame.setVisible(true);
    }

    /**
     * Метод сортирует от старой до новой даты что б расположить в такой последовательности на графике,
     * считает для каждого дня сумму которая в итоге останется на руках,
     * берет количество дней что б расчитать длинну оси Х,
     * берет максимальные значения Прибыли и Затрат что бы расчитать размеры оси Y.
     */
    public static void createListForGraphic(){

        //упорядочим по дате от Старого до Нового чтобы првильно вытягивать значения
        Collections.sort(myListDay, new Comparator<Day>() {
            @Override
            public int compare(Day o1, Day o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        //сначала считаем для каждого дня сумму в цикле for а за циклом дописываем последнй день
        for(int i = 0; i< myListDay.size()-1; i++){

            Day dayVal = myListDay.get(i);
            Day dayVal2 = myListDay.get(i+1);

            //Если даты совпадают сумируем деньги в зависимости от Кредит/Дебит и записываем в новый лист где дни не повторяются
            if(dayVal.getDate().equals(dayVal2.getDate())){

                if(dayVal.getOperation().equals("debit")){
                    summaVDen = summaVDen.add(dayVal.getSum());
                    debitVDen = debitVDen.add(dayVal.getSum());
                }
                if(dayVal.getOperation().equals("credit")){
                    summaVDen = summaVDen.subtract(dayVal.getSum());
                    creditVDen = creditVDen.add(dayVal.getSum());
                }
            }
            //Если даты не равны то с первой добавляю в общую сумму в день и создаю в новый лист день с суммой для числа ДНЯ
            if(!(dayVal.getDate().equals(dayVal2.getDate()))){
                if(dayVal.getOperation().equals("debit")){
                    summaVDen = summaVDen.add(dayVal.getSum());
                    debitVDen = debitVDen.add(dayVal.getSum());
                }
                if(dayVal.getOperation().equals("credit")){
                    summaVDen = summaVDen.subtract(dayVal.getSum());
                    creditVDen = creditVDen.add(dayVal.getSum());
                }
                //При таком создании дня значения поля operation(для графика балланса) и комменты будут ошибочные,
                // так как они нам не важны для построения графика
                newListDay.add(new Day(dayVal.getId(), dayVal.getDate(), summaVDen, dayVal.getCarrency(),
                        dayVal.getOperation(), dayVal.getComment()));

                //Тут уже прибыль и убытки будут в отдельных листаах по днях
                newListDayDebit.add(new Day(dayVal.getId(), dayVal.getDate(), debitVDen, dayVal.getCarrency(),
                        dayVal.getOperation(), dayVal.getComment()));
                debitVDen = BigDecimal.valueOf(0);//обнуляем что бы считать для следующего дня

                newListDayCredit.add(new Day(dayVal.getId(), dayVal.getDate(), creditVDen, dayVal.getCarrency(),
                        dayVal.getOperation(), dayVal.getComment()));
                creditVDen = BigDecimal.valueOf(0);//обнуляем что бы считать для следующего дня
            }
        }
        //Дописываем последний день в список и список можно брать дальше и работать
        if(myListDay.size()!=0){
            Day lastDay = myListDay.get(myListDay.size()-1);
            if(lastDay.getOperation().equals("debit")){
                summaVDen = summaVDen.add(lastDay.getSum());
                newListDay.add(new Day(lastDay.getId(), lastDay.getDate(), summaVDen, lastDay.getCarrency(),
                        lastDay.getOperation(), lastDay.getComment()));

                debitVDen = debitVDen.add(lastDay.getSum());
                newListDayDebit.add(new Day(lastDay.getId(), lastDay.getDate(), debitVDen, lastDay.getCarrency(),
                        lastDay.getOperation(), lastDay.getComment()));
                //Создаем с пустым что бы не нарушать колво дней
                newListDayCredit.add(new Day(lastDay.getId(), lastDay.getDate(), creditVDen, lastDay.getCarrency(),
                        lastDay.getOperation(), lastDay.getComment()));


            }
            if(lastDay.getOperation().equals("credit")){
                summaVDen = summaVDen.subtract(lastDay.getSum());
                newListDay.add(new Day(lastDay.getId(), lastDay.getDate(), summaVDen, lastDay.getCarrency(),
                        lastDay.getOperation(), lastDay.getComment()));

                creditVDen = creditVDen.add(lastDay.getSum());
                newListDayCredit.add(new Day(lastDay.getId(), lastDay.getDate(), creditVDen, lastDay.getCarrency(),
                        lastDay.getOperation(), lastDay.getComment()));


                newListDayDebit.add(new Day(lastDay.getId(), lastDay.getDate(), debitVDen, lastDay.getCarrency(),
                        lastDay.getOperation(), lastDay.getComment()));
            }
        }

        //Сортируем что бы  получить макс значение Дебита
        Collections.sort(newListDayDebit, new Comparator<Day>() {
            @Override
            public int compare(Day o1, Day o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        //сразу создадим массив ИКСОВ
        GraphicFrame.xArrayM = new int[newListDay.size()];
        GraphicFrame2.xArrayM = new int[newListDay.size()];
        for(int i = 0; i< newListDay.size(); i++){
            if(i==0){
                GraphicFrame.xArrayM[0] = 20;//20- отступ координаты Х что бы график начинался с нуля
                GraphicFrame2.xArrayM[0] = 20;
            }
            if(i>0){
                GraphicFrame.xArrayM[i] = 20+(i*20);//первое число 20 это отступ координаты Х, второе шаг по дням
                GraphicFrame2.xArrayM[i] = 20+(i*20);
            }
        }
        //Возьмем значения Y
        GraphicFrame.yArrayM = new int[newListDay.size()];//Сюда записываем значения балланча по дням, это координаты Y
        GraphicFrame2.yArrayMDebit = new int[newListDay.size()];
        GraphicFrame2.yArrayMCredit = new int[newListDay.size()];
        for(int y = 0; y < newListDay.size(); y++){
            Day sym = newListDay.get(y);
            //запишем прибылль в день в координ Y
            GraphicFrame.yArrayM[y] = sym.getSum().intValue();
            Day symDebit = newListDayDebit.get(y);
            GraphicFrame2.yArrayMDebit[y] = symDebit.getSum().intValue();
            //Запигем расходы в день в координаты Х
            GraphicFrame.yArrayM[y] = sym.getSum().intValue();
            Day symCredit = newListDayCredit.get(y);
            GraphicFrame2.yArrayMCredit[y] = symCredit.getSum().intValue();

        }

        //Выбираем МАКС и МИН значения что бы значть длинны осей графиков и кол-во дней
        //Узнаем макс/min значения и присваиваем их в max/min значения
        listForSum = newListDay; //Создали для того что бы потом опять не упорядовачевать newListDay
        Collections.sort(listForSum, new Comparator<Day>() {
            @Override
            public int compare(Day o2, Day o1) {
                return o1.getSum().compareTo(o2.getSum());
            }
        });

        //Сортируем что бы  получить макс значение Дебита
        listForDebit = newListDayDebit;
        Collections.sort(listForDebit, new Comparator<Day>() {
            @Override
            public int compare(Day o2, Day o1) {
                return o1.getSum().compareTo(o2.getSum());
            }
        });

        //Сортируем что бы  получить макс значение Credit
        listForCredit = newListDayCredit;
        Collections.sort(listForCredit, new Comparator<Day>() {
            @Override
            public int compare(Day o2, Day o1) {
                return o1.getSum().compareTo(o2.getSum());
            }
        });

        //если ноль сделать

        if(myListDay.size()!=0){
            max = listForSum.get(0).getSum().intValue();
            min = listForSum.get(listForSum.size()-1).getSum().intValue();
            maxDebit = listForDebit.get(0).getSum().intValue();
            maxCredit = listForCredit.get(0).getSum().intValue();
        }
    }

    /**
     * Этот и метод что бы уменьшить кол-во текста в функциях кнопок при обнулении
     */
    public static void nollMethod(){
        newListDayDebit = new ArrayList<Day>();//Что бы не дублировало значения
        newListDayCredit = new ArrayList<Day>();
        if(listForSum.size() != 0){
            max = listForSum.get(0).getSum().intValue();
            min = listForSum.get(listForSum.size()-1).getSum().intValue();
            maxDebit = listForDebit.get(0).getSum().intValue();
            maxCredit = listForCredit.get(0).getSum().intValue();

            summaVDen = new BigDecimal(0);
            debitVDen = new BigDecimal(0);
            creditVDen = new BigDecimal(0);
            GraphicFrame.centerY= max + 30;
        }
    }


    /**
     * метод получения только прибыли чтоб отобразить в JList-е
     */
    public static void sortedProfit(){
        for(Day myDay: myListDay){
            if(myDay.getOperation().equals("debit")){
                listProfit.add(0, " " + myDay.getDate()+
                " " + myDay.getSum() +
                " " + myDay.getCarrency() +
                " " + myDay.getOperation() +
                " " + myDay.getComment());
            }
            if(myDay.getOperation().equals("credit")){
                listExpenses.add(0, " " + myDay.getDate()+
                        " " + myDay.getSum() +
                        " " + myDay.getCarrency() +
                        " " + myDay.getOperation() +
                        " " + myDay.getComment());
            }
        }
    }


//    /**
//     * Сортирует по Затратам для JList
//     */
//    public static void sortedExpenses(){
//        for(Day myDay: myListDay){
//
//        }
//    }


    /**
     * Берет все значения myListDay для JList
     */
    public static void sortedAll(){
        for(Day myDay: myListDay){
            listAll.add(0, " " + myDay.getDate()+
                    " " + myDay.getSum() +
                    " " + myDay.getCarrency() +
                    " " + myDay.getOperation() +
                    " " + myDay.getComment());
        }
    }




    public static ArrayList<String> getListProfit() {
        return listProfit;
    }

    public static ArrayList<String> getListExpenses() {
        return listExpenses;
    }

    public static ArrayList<String> getListAll() {
        return listAll;
    }


}



