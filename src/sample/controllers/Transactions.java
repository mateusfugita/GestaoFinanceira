package sample.controllers;

public class Transactions {
    private String date,company;
    private double spent,value;


    public Transactions( String Date, double Spent,String Company ) {
        this.date = Date;
        this.spent = Spent ;
        this.company = Company;
    }

    public Transactions(String Date, double Value){
        this.date = Date;
        this.value = Value;
    }

    public String getDate() {
        return this.date;
    }

    public double getSpent() {
        return this.spent;
    }

    public String getCompany() {
        return this.company;
    }

    public double getValue(){return this.value;}
}
