package br.fatec.edu.ecommerce.model;

public class Voucher {

    public enum Type {
        PERCENTAGE,
        FIXED_AMOUNT;
    }

    private Type type;
    private double value;

    public Voucher() {
    }

    public Voucher(Type type, double value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}