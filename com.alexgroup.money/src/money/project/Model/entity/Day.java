package money.project.Model.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexey Sushko
 * Сущность Day в которой следующие поля:
 * id - уникальное поле из-за того что в БД оно заполняется автоматически автоинкрементом;
 * date - дата совершения валютной операции
 * sum - сумма операции;
 * carrency - валюта операции, везде используется доллар;
 * operation - операция добавления(debit) и расходов(credit) средств;
 * comment - коментарии(Например на что потратили или откуда получили средства).
 */
public class Day {

    private int id;
    private Date date;
    private BigDecimal sum;
    private String carrency;
    private String operation;
    private String comment;


    @Override
    public String toString() {
        return "Day{" +
                "id=" + id +
                ", date=" + date +
                ", sum=" + sum +
                ", carrency='" + carrency + '\'' +
                ", operation='" + operation + '\'' +
                ", comment='" + comment + "'}\n";
    }

    public Day(int id, Date date, BigDecimal sum, String carrency, String operation, String comment){
        this.id = id;
        this.date = date;
        this.sum = sum;
        this.carrency = carrency;
        this.operation = operation;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public String getCarrency() {
        return carrency;
    }

    public String getOperation() {
        return operation;
    }

    public String getComment() {
        return comment;
    }
}
