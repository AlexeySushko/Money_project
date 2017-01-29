package money.project.view;

import money.project.Constants;
import money.project.Model.Methods;
import money.project.Model.entity.Day;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alexey Sushko.
 * Класс создающий главное окно программы со всеми его компонентами
 */
public class MainFrameProgram  extends JFrame implements ActionListener {

    public static JFrame programFrame;

    private Box verticalMainBox, horizontalTopBox, verticalMainButtonBox, verticalIformationBox, horizontalDataBox,
            horizontalProfitBox, horizontalExpensesBox, horizontalBalanceBox, horizontalReportBox, horizontal3ButtonBox,
            horizontalGraphicBoxBut, horizontalClearBox;

    public static JLabel textData, textDataV, textProfit, textProfitV, textExpenses, textExpensesV, textBallance, textBallanceV,
            textReport;
    public static JList northList;

    private JButton butDebit, butCredit, butAllOperation, butExpenses, butProfit, butGraphic, butGraphic2, butClear;

    private JPanel mainPanel;


    public static ArrayList<String> data = new ArrayList<String>();

    public MainFrameProgram(){

        programFrame = new JFrame(Constants.NAME_PROGRAM);
        programFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        horizontalDataBox = Box.createHorizontalBox();
        textData = new JLabel(Constants.TEXT_DATA);
        textDataV = new JLabel(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//Сразу установили текущую ДАТУ
        horizontalDataBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalDataBox.add(textData);
        horizontalDataBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalDataBox.add(textDataV);
        horizontalDataBox.add(Box.createHorizontalGlue());

        horizontalProfitBox = Box.createHorizontalBox();
        textProfit = new JLabel(Constants.TEXT_PROFIT);
        textProfitV = new JLabel(Methods.profit.toString());
        horizontalProfitBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalProfitBox.add(textProfit);
        horizontalProfitBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalProfitBox.add(textProfitV);
        horizontalProfitBox.add(Box.createHorizontalGlue());

        horizontalExpensesBox = Box.createHorizontalBox();
        textExpenses = new JLabel(Constants.TEXT_EXPENSES);
        textExpensesV = new JLabel(Methods.expenses.toString());
        horizontalExpensesBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalExpensesBox.add(textExpenses);
        horizontalExpensesBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalExpensesBox.add(textExpensesV);
        horizontalExpensesBox.add(Box.createHorizontalGlue());

        horizontalBalanceBox = Box.createHorizontalBox();
        textBallance = new JLabel(Constants.TEXT_BALLANCE);
        textBallanceV = new JLabel(Methods.ballance.toString());
        horizontalBalanceBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalBalanceBox.add(textBallance);
        horizontalBalanceBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalBalanceBox.add(textBallanceV);
        horizontalBalanceBox.add(Box.createHorizontalGlue());


        verticalIformationBox = Box.createVerticalBox();
        verticalIformationBox.add(horizontalDataBox);
        verticalIformationBox.add(horizontalProfitBox);
        verticalIformationBox.add(horizontalExpensesBox);
        verticalIformationBox.add(horizontalBalanceBox);

        verticalMainButtonBox = Box.createVerticalBox();
        butDebit = new JButton(Constants.NAME_BUTTON_DEBIT);//new ImageIcon("+.png"));
        butDebit.addActionListener(this);
        butDebit.setMaximumSize(new Dimension(Integer.MAX_VALUE, butDebit.getMinimumSize().width));
        butCredit = new JButton(Constants.NAME_BUTTON_CREDIT);//new ImageIcon("-.png"));
        butCredit.addActionListener(this);
        butCredit.setMaximumSize(new Dimension(Integer.MAX_VALUE, butCredit.getMinimumSize().width));
        verticalMainButtonBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_BUTTON));
        verticalMainButtonBox.add(butDebit);
        verticalMainButtonBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_BUTTON));
        verticalMainButtonBox.add(butCredit);
        verticalMainButtonBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_BUTTON));

        horizontalTopBox = Box.createHorizontalBox();
        horizontalTopBox.add(verticalIformationBox);
        horizontalTopBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalTopBox.add(verticalMainButtonBox);
        horizontalTopBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalTopBox.setSize(400, 250);

        horizontalReportBox = Box.createHorizontalBox();
        textReport = new JLabel(Constants.TEXT_REPORT);
        textReport.setHorizontalTextPosition(SwingConstants.CENTER);
        horizontalReportBox.add(textReport);

        horizontal3ButtonBox = Box.createHorizontalBox();
        butAllOperation = new JButton(Constants.NAME_BUTTON_ALL_OPERATION);
        butAllOperation.addActionListener(this);
        butAllOperation.setMaximumSize(new Dimension(Integer.MAX_VALUE, butAllOperation.getMinimumSize().height));
        butExpenses = new JButton(Constants.NAME_BUTTON_EXPENSES);
        butExpenses.addActionListener(this);
        butExpenses.setMaximumSize(new Dimension(Integer.MAX_VALUE, butExpenses.getMinimumSize().height));
        butProfit = new JButton(Constants.NAME_BUTTON_PROFIT);
        butProfit.addActionListener(this);
        butProfit.setMaximumSize(new Dimension(Integer.MAX_VALUE, butProfit.getMinimumSize().height));
        horizontal3ButtonBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_BUTTON));
        horizontal3ButtonBox.add(butAllOperation);
        horizontal3ButtonBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_BUTTON));
        horizontal3ButtonBox.add(butExpenses);
        horizontal3ButtonBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_BUTTON));
        horizontal3ButtonBox.add(butProfit);
        horizontal3ButtonBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_BUTTON));

        horizontalGraphicBoxBut = Box.createHorizontalBox();
        butGraphic = new JButton(Constants.NAME_BUTTON_GRAPHIC);
        butGraphic.addActionListener(this);
        butGraphic2 = new JButton(Constants.NAME_BUTTON_GRAPHIC2);
        butGraphic2.addActionListener(this);
        horizontalGraphicBoxBut.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_BUTTON));
        horizontalGraphicBoxBut.add(butGraphic);
        horizontalGraphicBoxBut.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_BUTTON));
        horizontalGraphicBoxBut.add(butGraphic2);
        horizontalGraphicBoxBut.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_BUTTON));

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        northList = new JList(data.toArray());
        northList.setLayoutOrientation(JList.VERTICAL);

        northList.setVisibleRowCount(0);

        JScrollPane northScroll = new JScrollPane(northList);
        northScroll.setPreferredSize(new Dimension(200, 200));
        mainPanel.add(northScroll);

        horizontalClearBox = Box.createHorizontalBox();
        butClear = new JButton(Constants.NAME_BUTTON_CLEAR);
        butClear.addActionListener(this);
        horizontalClearBox.add(butClear);

        verticalMainBox = Box.createVerticalBox();
        verticalMainBox.add(horizontalTopBox);
        verticalMainBox.add(horizontalReportBox);
        verticalMainBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_PANNEL));
        verticalMainBox.add(horizontal3ButtonBox);
        verticalMainBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_PANNEL));
        verticalMainBox.add(horizontalGraphicBoxBut);
        verticalMainBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_PANNEL));

        verticalMainBox.add(mainPanel);
        verticalMainBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_PANNEL));
        verticalMainBox.add(horizontalClearBox);
        verticalMainBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_PANNEL));

        programFrame.setContentPane(verticalMainBox);

        programFrame.pack();
        programFrame.setSize(Constants.SIZE_PROGRAM_WINDOW_WIDTH, Constants.SIZE_PROGRAM_WINDOW_HEIGHT);
        programFrame.setLocationRelativeTo(null);
        programFrame.setResizable(false);
        programFrame.setVisible(true);


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if(actionCommand.equals(Constants.NAME_BUTTON_DEBIT)){
            new AddFrame();
            programFrame.setEnabled(false);
            AddFrame.butDeduct.setVisible(false);



        }
        if(actionCommand.equals(Constants.NAME_BUTTON_CREDIT)){
            new AddFrame();
            programFrame.setEnabled(false);
            AddFrame.butAdd.setVisible(false);

        }
        if(actionCommand.equals(Constants.NAME_BUTTON_ALL_OPERATION)){
            Methods.sortedAll();
            northList.setListData(Methods.getListAll().toArray());
            Methods.listAll = new ArrayList<String>();


        }
        if(actionCommand.equals(Constants.NAME_BUTTON_EXPENSES)){
            Methods.sortedProfit();
            northList.setListData(Methods.getListExpenses().toArray());
            Methods.listExpenses = new ArrayList<String>();
            Methods.listProfit = new ArrayList<String>();


        }
        if(actionCommand.equals(Constants.NAME_BUTTON_PROFIT)){

            Methods.sortedProfit();//Сортируем выбирая прибыль

            northList.setListData(Methods.getListProfit().toArray());//Сетим значение в скрул
            Methods.listProfit = new ArrayList<String>();//обнуляем лист с прибылью что бы не добавляло тот же самый текст к имеющемуся
            Methods.listExpenses = new ArrayList<String>();
        }

        if(actionCommand.equals(Constants.NAME_BUTTON_GRAPHIC)){
            GraphicFrame.setLongX((Methods.newListDay.size() * 20) + 20 + 10);//обновили длииннуХ
            GraphicFrame.setHowMuchDay(Methods.newListDay.size());//обновили количество дней
            Methods.createGraficFrame();
        }

        if(actionCommand.equals(Constants.NAME_BUTTON_GRAPHIC2)){
            GraphicFrame2.setLongX((Methods.newListDay.size() * 20) + 20 + 10);//обновили длииннуХ);
            GraphicFrame2.setHowMuchDay(Methods.newListDay.size());
            Methods.createGraficFrame2();
        }

        if(actionCommand.equals(Constants.NAME_BUTTON_CLEAR)){

            Methods.connectParametr = "clear";
            Methods.Connection();
            Methods.myListDay = new ArrayList<Day>();
            Methods.connectParametr = "read";
            Methods.Connection();
            Methods.sortedMyListDay();
            Methods.workList();

            GraphicFrame2.yArrayMDebit = new int[Methods.newListDay.size()];
            MainFrameProgram.getTextProfitV().setText("0");
            MainFrameProgram.getTextExpensesV().setText("0");
            MainFrameProgram.getTextBallanceV().setText("0");
            data = new ArrayList<String>();
            MainFrameProgram.northList.setListData(MainFrameProgram.data.toArray());
        }
    }


    public static JLabel getTextProfitV() {
        return textProfitV;
    }

    public static JLabel getTextExpensesV() {
        return textExpensesV;
    }

    public static JLabel getTextBallanceV() {
        return textBallanceV;
    }
}
