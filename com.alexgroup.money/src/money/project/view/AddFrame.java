package money.project.view;

import money.project.Constants;
import money.project.Model.Methods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alexey Sushko.
 * Класс создающий окно с помощья которого мы можем добаваить операцию по финансам(Затраты/Прибыль)
 */
public class AddFrame extends JFrame implements ActionListener{

    private JFrame mainFrame;
    private Box verticalmainBox, horizontalbuttonBox, horizontal1Box, horizontal2Box, horizontal3Box,
            horizontalDataBox, horizontalSumBox, horizontalCommentBox;
    private JLabel numberJL, sumJL, commentJL;
    public static JTextField numberJText, sumJText, commmentJText;
    public static JButton butAdd, butCancel, butDeduct;

    public AddFrame(){
        mainFrame = new JFrame(Constants.NAME_ADD_PROGRAM);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        horizontalDataBox = Box.createHorizontalBox();
        numberJL = new JLabel(Constants.TEXT_SELECTION_DATA);
        horizontalDataBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalDataBox.add(numberJL);
        horizontalDataBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));

        horizontal1Box = Box.createHorizontalBox();
        numberJText = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        numberJText.setHorizontalAlignment(SwingConstants.CENTER);
        numberJText.setPreferredSize(new Dimension(30, 40));
        horizontal1Box.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontal1Box.add(numberJText);
        horizontal1Box.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));

        horizontalSumBox = Box.createHorizontalBox();
        sumJL = new JLabel(Constants.TEXT_SUM);
        sumJL.setHorizontalTextPosition(SwingConstants.CENTER);
        horizontalSumBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalSumBox.add(sumJL);
        horizontalSumBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));

        horizontal2Box = Box.createHorizontalBox();
        sumJText = new JTextField();
        sumJText.setHorizontalAlignment(SwingConstants.CENTER);
        horizontal2Box.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontal2Box.add(sumJText);
        horizontal2Box.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));

        horizontalCommentBox = Box.createHorizontalBox();
        commentJL = new JLabel(Constants.TEXT_COMMENT);
        commentJL.setHorizontalTextPosition(SwingConstants.CENTER);
        horizontalCommentBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontalCommentBox.add(commentJL);
        horizontalCommentBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));


        horizontal3Box = Box.createHorizontalBox();
        commmentJText = new JTextField();
        commmentJText.setHorizontalAlignment(SwingConstants.CENTER);
        horizontal3Box.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));
        horizontal3Box.add(commmentJText);
        horizontal3Box.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_PANNEL));

        horizontalbuttonBox = Box.createHorizontalBox();
        butAdd = new JButton(Constants.NAME_BUTTON_ADD);
        butAdd.addActionListener(this);
        butDeduct = new JButton(Constants.NAME_BUTTON_DEDUCT);
        butDeduct.addActionListener(this);
        butCancel = new JButton(Constants.NAME_BUTTON_CANCEL);
        butCancel.addActionListener(this);
        horizontalbuttonBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_BUTTON));
        horizontalbuttonBox.add(butAdd);
        horizontalbuttonBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_BUTTON));
        horizontalbuttonBox.add(butDeduct);
        horizontalbuttonBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_BUTTON));
        horizontalbuttonBox.add(butCancel);
        horizontalbuttonBox.add(Box.createHorizontalStrut(Constants.SIZE_MEJDY_BUTTON));

        verticalmainBox = Box.createVerticalBox();
        verticalmainBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_PANNEL));
        verticalmainBox.add(horizontalDataBox);
        verticalmainBox.add(horizontal1Box);
        verticalmainBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_PANNEL));
        verticalmainBox.add(horizontalSumBox);
        verticalmainBox.add(horizontal2Box);
        verticalmainBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_PANNEL));
        verticalmainBox.add(horizontalCommentBox);
        verticalmainBox.add(horizontal3Box);
        verticalmainBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_PANNEL));
        verticalmainBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_PANNEL));
        verticalmainBox.add(horizontalbuttonBox);
        verticalmainBox.add(Box.createVerticalStrut(Constants.SIZE_MEJDY_PANNEL));

        mainFrame.setContentPane(verticalmainBox);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(330, 230);
        mainFrame.setResizable(false);
        mainFrame.setAlwaysOnTop(true);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if(actionCommand.equals(Constants.NAME_BUTTON_ADD)){
            Methods.operationDC = "debit";
            Methods.connectParametr = "write";
            Methods.Connection();//Коннектимся что бы записать новые данные
            mainFrame.setVisible(false);


            Methods.Connection();//Коннектимся считать данные
            Methods.sortedMyListDay();//Просто отсортировали
            Methods.workList();//Считаем дозод затраты и баланс
            //ниже сетим значения чтобы они обновились
            MainFrameProgram.getTextProfitV().setText(String.valueOf(Methods.profit));
            MainFrameProgram.getTextExpensesV().setText(Methods.expenses.toString());
            MainFrameProgram.getTextBallanceV().setText(Methods.ballance.toString());
            MainFrameProgram.northList.setListData(MainFrameProgram.data.toArray());

            MainFrameProgram.programFrame.setEnabled(true);
            Methods.nollMethod();//Обнуляем значения куда будут писаться занные для графика
            Methods.createListForGraphic();//создаем Лист для графика в котором нет повторяющихся дней, и баллансы как координаты
        }

        if(actionCommand.equals(Constants.NAME_BUTTON_DEDUCT)){
            Methods.operationDC = "credit";
            Methods.connectParametr = "write";
            Methods.Connection();
            mainFrame.setVisible(false);

            Methods.Connection();
            Methods.sortedMyListDay();
            Methods.workList();
            MainFrameProgram.getTextProfitV().setText(String.valueOf(Methods.profit));
            MainFrameProgram.getTextExpensesV().setText(Methods.expenses.toString());
            MainFrameProgram.getTextBallanceV().setText(Methods.ballance.toString());
            MainFrameProgram.northList.setListData(MainFrameProgram.data.toArray());
            MainFrameProgram.programFrame.setEnabled(true);
            Methods.nollMethod();
            Methods.createListForGraphic();
        }

        if(actionCommand.equals(Constants.NAME_BUTTON_CANCEL)){
            mainFrame.setVisible(false);
            MainFrameProgram.programFrame.setEnabled(true);

        }

    }

    public static String getNumberJText() {
        return numberJText.getText();
    }

    public static String getSumJText() {
        return sumJText.getText();
    }

    public static String getCommmentJText() {
        return commmentJText.getText();
    }
}
