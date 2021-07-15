package com.pkalinov.autopartsmgmtserver.models;

public class SaleModel {
    private Long soldQuantity;

    public SaleModel(){
    }

    public SaleModel(Long soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public Long getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Long soldQuantity) {
        this.soldQuantity = soldQuantity;
    }
}
