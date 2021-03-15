package com.sudrives.sudrivespartner.activities.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.textfield.TextInputEditText;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.sudrives.sudrivespartner.utils.AppConstants.ACCOUNT_DETAILS_TAG;
import static com.sudrives.sudrivespartner.utils.AppConstants.GET_ACCOUNT_DETAILS;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_ACCOUNT_ID;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_ACCOUNT_NUMBER;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_AMOUNT;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_BANK_NAME;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_HOLDER_NAME;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_IFSC;
import static com.sudrives.sudrivespartner.utils.AppConstants.SAVE_ACCOUNT_DETAILS;
import static com.sudrives.sudrivespartner.utils.AppConstants.SAVE_ACCOUNT_DETAILS_TAG;
import static com.sudrives.sudrivespartner.utils.AppConstants.UPDATE_ACCOUNT_DETAILS;
import static com.sudrives.sudrivespartner.utils.AppConstants.UPDATE_ACCOUNT_DETAILS_TAG;
import static com.sudrives.sudrivespartner.utils.AppConstants.WALLET_DETAILS;
import static com.sudrives.sudrivespartner.utils.AppConstants.WITHDRAW_MONEY;
import static com.sudrives.sudrivespartner.utils.AppConstants.WITHDRAW_MONEY_TAG;

public class ClaimWalletActivity extends AppCompatActivity implements View.OnClickListener, NetworkConn.OnRequestResponse {

    // MainViewModel mainViewModel;
    String object = "";
    private TextView header_name, total_amount_earned_tv;
    private ImageView ib_back_button;
    private TextInputEditText account_holder_name_ev, bank_name_ev,
            bank_ifsc_ev, account_number_ev, claim_money_ev;
    private ImageView edit_account_details, edit_claim_money;
    private Button save_account_details;
    private NetworkConn networkConn;
    private int accountDetailsStatus = 0;
    private boolean isAccountDetailsEditCLicked = false;
    private String account_id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_wallet);

        initViews();

        networkConn = NetworkConn.getInstance(this);

        getWalletDetails();

        getAccountDetails();

    }


    private void initViews() {
        header_name = findViewById(R.id.header_name);
        ib_back_button = findViewById(R.id.ib_back_button);
        account_holder_name_ev = findViewById(R.id.account_holder_name_ev);
        bank_name_ev = findViewById(R.id.bank_name_ev);
        bank_ifsc_ev = findViewById(R.id.bank_ifsc_ev);
        account_number_ev = findViewById(R.id.account_number_ev);
        claim_money_ev = findViewById(R.id.claim_money_ev);

        total_amount_earned_tv = findViewById(R.id.total_amount_earned_tv);
        edit_account_details = findViewById(R.id.edit_account_details);
        edit_claim_money = findViewById(R.id.edit_claim_money);
        save_account_details = findViewById(R.id.save_account_details);

        header_name.setText("Wallet");

        disableAccountDetailsEditView();
        disableClaimMoneyEditView();

        // click listeners
        save_account_details.setOnClickListener(this);
        ib_back_button.setOnClickListener(this);
        edit_claim_money.setOnClickListener(this);
        edit_account_details.setOnClickListener(this);

    }

    private void disableClaimMoneyEditView() {
        claim_money_ev.setEnabled(false);
    }

    private void enableClaimMoneyEditView() {
        claim_money_ev.setEnabled(true);
    }

    private void disableAccountDetailsEditView() {
        account_holder_name_ev.setEnabled(false);
        bank_name_ev.setEnabled(false);
        bank_ifsc_ev.setEnabled(false);
        account_number_ev.setEnabled(false);
    }

    private void enableAccountDetailsEditView() {
        account_holder_name_ev.setEnabled(true);
        bank_name_ev.setEnabled(true);
        bank_ifsc_ev.setEnabled(true);
        account_number_ev.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back_button:
                finish();
                break;

            case R.id.edit_account_details:
                isAccountDetailsEditCLicked = true;
                edit_claim_money.setVisibility(View.GONE);
                enableAccountDetailsEditView();
                save_account_details.setText("Save");
                save_account_details.setVisibility(View.VISIBLE);
                break;

            case R.id.edit_claim_money:
                isAccountDetailsEditCLicked = false;
                edit_account_details.setVisibility(View.GONE);
                enableClaimMoneyEditView();
                save_account_details.setText("Claim");
                save_account_details.setVisibility(View.VISIBLE);
                break;

            case R.id.save_account_details:
                if (isAccountDetailsEditCLicked) {
                    if (accountDetailsStatus == 0) {
                        postAccountDetails(SAVE_ACCOUNT_DETAILS, SAVE_ACCOUNT_DETAILS_TAG);
                    } else {
                        postAccountDetails(UPDATE_ACCOUNT_DETAILS, UPDATE_ACCOUNT_DETAILS_TAG);
                    }
                } else {
                    postWithdrawMoney();
                }
                break;
        }
    }

    private void postWithdrawMoney() {
        HashMap<String, String> withdrawMap = new HashMap<>();
        withdrawMap.put(KEY_AMOUNT, claim_money_ev.getText().toString().trim());

        networkConn.makeRequest(this, networkConn.createFormDataRequest(WITHDRAW_MONEY, withdrawMap, 0), this, WITHDRAW_MONEY_TAG);

    }

    private void postAccountDetails(String url, String event) {

        if (checkAccountDetailsValidation()) {

            postAccountDetailsToServer(url, event);

        }
    }

    private void postAccountDetailsToServer(String url, String event) {

        HashMap<String, String> detailsMap = new HashMap<>();
        detailsMap.put(KEY_HOLDER_NAME, account_holder_name_ev.getText().toString());
        detailsMap.put(KEY_IFSC, bank_ifsc_ev.getText().toString());
        detailsMap.put(KEY_BANK_NAME, bank_name_ev.getText().toString());
        detailsMap.put(KEY_ACCOUNT_NUMBER, account_number_ev.getText().toString());

        if (event.equals(UPDATE_ACCOUNT_DETAILS_TAG)) {
            detailsMap.put(KEY_ACCOUNT_ID, account_id);
        }

        networkConn.makeRequest(this, networkConn.createFormDataRequest(url, detailsMap, 0), this, event);


    }

    private boolean checkAccountDetailsValidation() {
        if (account_holder_name_ev.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (bank_name_ev.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Bank name is missing", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (bank_ifsc_ev.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ifsc code is missing", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (account_number_ev.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Account Number is missing", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void getWalletDetails() {
        networkConn.makeRequest(this, networkConn.createGetRequest(AppConstants.GET_WALLET_DETAILS), this, WALLET_DETAILS);
    }

    private void getAccountDetails() {
        networkConn.makeRequest(this, networkConn.createGetRequest(GET_ACCOUNT_DETAILS), this, ACCOUNT_DETAILS_TAG);
    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {

        switch (strEventType) {
            case WALLET_DETAILS:
                int status = response.optInt("status");
                if (status == 1) {
                    try {
                        JSONObject result = response.getJSONObject("result");
                        String wallet_amount = result.optString("wallet_amount");
                        total_amount_earned_tv.setText("Rs " + wallet_amount);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case ACCOUNT_DETAILS_TAG:
                accountDetailsStatus = response.optInt("status");
                if (accountDetailsStatus == 1) {

                    try {
                        JSONObject jsonObject = response.getJSONObject("result");
                        String account_holder_name = jsonObject.optString(KEY_HOLDER_NAME);
                        String ifsc_code = jsonObject.optString(KEY_IFSC);
                        String bank_name = jsonObject.optString(KEY_BANK_NAME);
                        String account_number = jsonObject.optString(KEY_ACCOUNT_NUMBER);
                        account_id = jsonObject.optString(KEY_ACCOUNT_ID);

                        account_holder_name_ev.setText("" + account_holder_name);
                        bank_ifsc_ev.setText("" + ifsc_code);
                        bank_name_ev.setText("" + bank_name);
                        account_number_ev.setText("" + account_number);

                    } catch (JSONException e) {
                        Crashlytics.logException(e);
                    }

                }

                break;

            case UPDATE_ACCOUNT_DETAILS_TAG:
            case SAVE_ACCOUNT_DETAILS_TAG:
                save_account_details.setVisibility(View.GONE);
                Toast.makeText(this, "Account Details Saved", Toast.LENGTH_SHORT).show();
                disableAccountDetailsEditView();
                edit_claim_money.setVisibility(View.VISIBLE);
                break;

            case WITHDRAW_MONEY_TAG:
                save_account_details.setVisibility(View.GONE);
                edit_account_details.setVisibility(View.VISIBLE);
                claim_money_ev.setText("");
                Toast.makeText(this, "Successfully Requested", Toast.LENGTH_SHORT).show();
                disableClaimMoneyEditView();
                break;

        }
    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }
}
