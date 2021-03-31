
package com.sudrives.sudrivespartner.models.cashourRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("unique_no")
    @Expose
    private String uniqueNo;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("account_holder_name")
    @Expose
    private String accountHolderName;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("account_no")
    @Expose
    private String accountNo;
    @SerializedName("ifsc_code")
    @Expose
    private String ifscCode;
    @SerializedName("upi_number")
    @Expose
    private String upiNumber;
    @SerializedName("paytm_number")
    @Expose
    private String paytmNumber;
    @SerializedName("request_date")
    @Expose
    private String requestDate;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getUpiNumber() {
        return upiNumber;
    }

    public void setUpiNumber(String upiNumber) {
        this.upiNumber = upiNumber;
    }

    public String getPaytmNumber() {
        return paytmNumber;
    }

    public void setPaytmNumber(String paytmNumber) {
        this.paytmNumber = paytmNumber;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

}
