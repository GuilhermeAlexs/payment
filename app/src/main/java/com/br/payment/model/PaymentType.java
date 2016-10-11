package com.br.payment.model;

/**
 * Created by GuilhermeAlexs on 10/10/2016.
 */
public enum PaymentType {
    CARD("Cartão"), CASH("Dinheiro");

    private String type;

    PaymentType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
