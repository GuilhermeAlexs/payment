package com.br.payment.model;

/**
 * Created by GuilhermeAlexs on 10/10/2016.
 */
public class Payment {
    private double value;
    private PaymentType paymentType;

    public Payment(double value, PaymentType paymentType) {
        this.value = value;
        this.paymentType = paymentType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
