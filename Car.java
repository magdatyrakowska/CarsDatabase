package cardb_dao;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Car {

    private int id;
    private int vin;
    private String brand;
    private String model;
    private GregorianCalendar productionDate;
    private String color;

    public Car() {
        id = 0;
        this.productionDate = new GregorianCalendar();
    }

    public Car(int id, int vin, String brand, String model, GregorianCalendar productionDate, String color) {
        this.id = id;
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.productionDate = productionDate;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductionDate(String datePattern) {
        DateFormat dataFormat = new SimpleDateFormat(datePattern);

        return dataFormat.format(productionDate.getTime());
    }

    public void setProductionDate(String date) {
        setProductionDateYear(Integer.parseInt(date.substring(0, 4)));
        setProductionDateMonth(Integer.parseInt(date.substring(5, 7)));
        setProductionDateDay(Integer.parseInt(date.substring(8)));
    }

    public void setProductionDate(GregorianCalendar productionDate) {
        this.productionDate = productionDate;
    }

    public void setProductionDateYear(int year) {
        this.productionDate.set(Calendar.YEAR, year);
    }

    public void setProductionDateMonth(int month) {
        this.productionDate.set(Calendar.MONTH, month - 1);
    }

    public void setProductionDateDay(int day) {
        this.productionDate.set(Calendar.DAY_OF_MONTH, day);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return  "id: " + id +
                ", vin: " + vin +
                ", " + brand +
                " " + model +
                ", wyprodukowany " + getProductionDate("dd-MM-yyyy")+
                ", kolor " + color;
    }
}
