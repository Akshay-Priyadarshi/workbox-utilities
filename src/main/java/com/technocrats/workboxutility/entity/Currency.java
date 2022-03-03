package com.technocrats.workboxutility.entity;

public class Currency {
    private double amount;
    private String from;
    private String to;
    private String format;

    public Currency(double amount, String from, String to, String format) {
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.format = format;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void printCurrency(){
        System.out.println(this.from);
        System.out.println(this.to);
        System.out.println(this.amount);
        System.out.println(this.format);
    }

}