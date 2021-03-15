
package com.sudrives.sudrivespartner.models.subscriptionPlans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("discount_persent")
    @Expose
    private String discountPersent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getDiscountPersent() {
        return discountPersent;
    }

    public void setDiscountPersent(String discountPersent) {
        this.discountPersent = discountPersent;
    }

}
