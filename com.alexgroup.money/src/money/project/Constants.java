package money.project;

/**
 * Created by Alexey Sushko.
 * Класс в котором собраны все финальные значения
 */
public class Constants {

    //---------------------------------------------------
    //подключение
    // JDBC driver name and database URL
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/money";

    //  Database credentials
    public static final String USER = "root";
    public static final String PASS = "12345";
    //----------------------------------------------------------------

    public static final String TEXT_DEBIT = "debit";
    public static final String TEXT_CREDIT = "credit";

    //Текстовые поля
    public static final String NAME_PROGRAM = "Контроль расхода средств";
    public static final String NAME_ADD_PROGRAM = "Операции с финансами";
    public static final String TEXT_DATA = "Текущая дата";
    public static final String TEXT_SELECTION_DATA = "Введите дату";
    public static final String TEXT_PROFIT = "Прибыль :";
    public static final String TEXT_EXPENSES = "Расходы :";
    public static final String TEXT_BALLANCE = "Текущий баланс :";
    public static final String TEXT_REPORT = "Отчет";
    public static final String TEXT_SUM = "Сумма";
    public static final String TEXT_COMMENT = "Комментарии";
    public static final String TEXT_DAYS = "Дни";
    public static final String TEXT_SECOND_GRAPHIC = "График Прибыль/Затраты по дням";
    public static final String TEXT_FIRST_GRAPHIC ="График балланса по дням";

    //Кнопки
    public static final String NAME_BUTTON_DEBIT = "+";
    public static final String NAME_BUTTON_CREDIT = "-";
    public static final String NAME_BUTTON_ALL_OPERATION = "Все операции";
    public static final String NAME_BUTTON_EXPENSES = "Расход";
    public static final String NAME_BUTTON_PROFIT = "Доход";
    public static final String NAME_BUTTON_OK = "ОК";
    public static final String NAME_BUTTON_BACK = "Назад";
    public static final String NAME_BUTTON_CANCEL = "Отмена";
    public static final String NAME_BUTTON_SAVE_EXIT = "Сохранить и выйти";
    public static final String NAME_BUTTON_CURRENCY = "Валюта";
    public static final String NAME_BUTTON_ADD = "Прибыль";
    public static final String NAME_BUTTON_DEDUCT = "Затраты";
    public static final String NAME_BUTTON_GRAPHIC= "График балланса";
    public static final String NAME_BUTTON_GRAPHIC2= "График Прибыль/Расход";
    public static final String NAME_BUTTON_CLEAR = "Очистить БД";

    //Размер окна программы
    public  static final int SIZE_PROGRAM_WINDOW_WIDTH = 450;
    public  static final int SIZE_PROGRAM_WINDOW_HEIGHT = 650;

    //Размеры полей окна
    public  static final int SIZE_MEJDY_PANNEL =  12;
    public  static final int SIZE_MEJDY_BUTTON =  12;


}
