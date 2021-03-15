package com.sudrives.sudrivespartner.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.activities.HomeMenuActivity;
import com.sudrives.sudrivespartner.models.GetTypeModelData;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppDialogs;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.Image_Picker;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class ReportIssueFragmentFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, NetworkConn.OnRequestResponse {

    private LinearLayout rlBookingid;
    private TextView tvBookingIdT;
    private TextView tvBookingId;
    private TextView tvTopicT;
    private RelativeLayout rlTopic;
    private Spinner spSelect;
    private TextView tvCommentT;
    private TextView tvUploadPicture;
    private ImageView ivCameraIcon;
    private TextView etWriteEmailT;
    private TextView etWriteEmail;
    private TextView etWriteMobNumT;
    private TextView etWriteMobNum;
    private String selectReason = "";
    private String selectedID = "";
    private RelativeLayout rl_upload_picture;
    private ImageView iv_upload_picture;
    List<String> selectList = new ArrayList<>();

    private Context mContext;
    private String mobile;
    private String bookingId;
    private String emailId;
    private GetTypeModelData getTypeModelData;

    private Image_Picker image_picker;
    private String imagePath = "";


    List<GetTypeModelData.ResultBean.ReportIssueBean> reportIssueBeans;

    ArrayAdapter<GetTypeModelData.ResultBean.ReportIssueBean> spinnerArrayAdapter;
    private String trip_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_issue_fragment, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        bookingId = bundle.getString(AppConstants.BOOKING_ID);
        emailId = bundle.getString(AppConstants.USER_EMAIL);
        mobile = bundle.getString(AppConstants.MOBILE_NUMBER);
        trip_id = bundle.getString(AppConstants.TRIP_ID);

        reportIssueBeans = new ArrayList<>();

        image_picker = new Image_Picker(getActivity());


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_upload_picture = view.findViewById(R.id.iv_upload_picture);
        rl_upload_picture = view.findViewById(R.id.rl_upload_picture);
        rlBookingid = view.findViewById(R.id.rl_bookingid);
        tvBookingIdT = view.findViewById(R.id.tv_booking_idT);
        tvBookingId = view.findViewById(R.id.tv_booking_id);
        tvTopicT = view.findViewById(R.id.tv_topicT);
        rlTopic = view.findViewById(R.id.rl_topic);
        spSelect = view.findViewById(R.id.sp_select);
        tvCommentT = view.findViewById(R.id.tv_commentT);
        tvUploadPicture = view.findViewById(R.id.tv_upload_picture);

        etWriteEmailT = view.findViewById(R.id.et_write_emailT);
        etWriteEmail = view.findViewById(R.id.et_write_email);
        etWriteMobNumT = view.findViewById(R.id.et_write_mob_numT);
        etWriteMobNum = view.findViewById(R.id.et_write_mob_num);
        view.findViewById(R.id.tv_submit).setOnClickListener(this);
        tvUploadPicture.setOnClickListener(this);

        spSelect.setOnItemSelectedListener(this);

        tvBookingId.setText(bookingId);
        etWriteEmail.setText(emailId);
        etWriteMobNum.setText(mobile);
        iv_upload_picture.setOnClickListener(this);
        rl_upload_picture.setOnClickListener(this);


        try {
            getType();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private EditText getEtComment() {
        return (EditText) getView().findViewById(R.id.et_comment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                //TODO implement

                if (checkEmpty()) {
                    reportIssue();

                }
                break;

            case R.id.tv_upload_picture:
            case R.id.iv_upload_picture:
            case R.id.rl_upload_picture:

                if (getActivity() != null) {
                    hideKeyboard(getActivity());
                }
                pickPhoto();
                break;
        }
    }

    private void pickPhoto() {

        image_picker.imageOptions();
    }


    private boolean checkEmpty() {

        if (TextUtils.isEmpty(selectReason)) {
            Toast.makeText(mContext, R.string.please_select_reason, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(getEtComment().getText().toString().trim())) {
            Toast.makeText(mContext, R.string.please_enter_comment, Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            selectReason = "";
            selectedID = "";
        } else {
            selectReason = getTypeModelData.result.report_issue.get(i).name;
            selectedID = getTypeModelData.result.report_issue.get(i).id;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void submitIssue() {

//        userid:16
//        trip_id:1
//        topic_id:16
//        mobile_no:9787456556465
//        email:a@b.com
//        comments:wegfwegfesgfesgfsegfsgf
//        picture: abc.png

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(mContext, AppPreference.KEY_USER_ID));
        hashMap.put(AppConstants.KEY_TRIP_ID, tvBookingId.getText().toString().trim());
        hashMap.put(AppConstants.TOPIC_ID, selectedID);
        hashMap.put(AppConstants.KEY_MOBILE, selectedID);
        hashMap.put(AppConstants.EMAIL, selectedID);
        hashMap.put(AppConstants.COMMENT, selectedID);
        hashMap.put(AppConstants.PICTURE, selectedID);
    }

    private void getType() throws JSONException {

//        {
//            "types" : "report_issue"
//        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AppConstants.KEY_TYPES, "report_issue");

        NetworkConn networkConn = NetworkConn.getInstance(mContext);

        networkConn.makeRequest(mContext, networkConn.createRawDataRequest(AppConstants.GET_TYPE, jsonObject.toString()), this, AppConstants.EVENT_GET_TYPE);


    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {

        try {


            switch (strEventType) {


                case AppConstants.EVENT_GET_TYPE:

                    JSONObject jsonObject = response;
                    Log.d("TYPERESPONCE", jsonObject.toString());

                    String s = jsonObject.toString();

                    getTypeModelData = new GetTypeModelData();

                    getTypeModelData = new Gson().fromJson(s, GetTypeModelData.class);

                    GetTypeModelData.ResultBean.ReportIssueBean reportIssueBean = new GetTypeModelData.ResultBean.ReportIssueBean();
                    reportIssueBean.name = getString(R.string.selecr_reason);
                    reportIssueBean.id = "";
                    reportIssueBean.types = "";

                    getTypeModelData.result.report_issue.add(0, reportIssueBean);

                    spinnerArrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, getTypeModelData.result.report_issue);

                    spSelect.setAdapter(spinnerArrayAdapter);


                    break;

                case AppConstants.EVENT_REPORT_ISSUE:
                    Log.e("REPORTISSUE", response.toString());


                    if (getActivity() != null) {
                        if (response.optInt("status") == 1) {

                            AppDialogs.showToast(mContext, response.getString(AppConstants.KEY_MESSAGE));
                            HomeMenuActivity.mOnFragmentChange.onFragmentChange(new HistoryFragment(), getString(R.string.nav_history));
                        } else {
                            AppDialogs.showToast(mContext, response.getString(AppConstants.KEY_MESSAGE));

                        }
                    }

                    break;

            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("dfgfdgfdg", "ffffffff: " + resultCode + "  :::: " + RESULT_OK + " :::::: " + RESULT_CANCELED);
        if (requestCode == Image_Picker.CAMERA_REQUEST) {
            Log.e("dfgfdgfdg", "ffffffff: " + resultCode + "  :::: " + RESULT_OK + " :::::: " + RESULT_CANCELED);

            switch (resultCode) {
                case RESULT_OK:

                    image_picker.cropImage(mContext, Uri.fromFile(Image_Picker.IMAGE_PATH), 1);

                    break;
                case RESULT_CANCELED:

                    break;
            }
        } else {

            if (requestCode == Image_Picker.GALLERY_REQUEST) {

                switch (resultCode) {
                    case RESULT_OK:

                        Uri selectedImageURI = data.getData();
                        try {

                            Log.e("dfgfdgfdg", "ffffffff: " + image_picker.checkURI(selectedImageURI) + "  :::: " + RESULT_OK + " :::::: " + RESULT_CANCELED);

                            if (image_picker.checkURI(selectedImageURI)) {
                                image_picker.cropImage(mContext, selectedImageURI, 1);
                            } else {
                                image_picker.cropImage(mContext, image_picker.getImageUrlWithAuthority(getActivity(), selectedImageURI), 1);
                            }
                        } catch (Exception ex) {

                            ex.printStackTrace();
                        }
                        break;

                    case RESULT_CANCELED:
                        //         Log.e("Get Data", ""+data.getData());

                        break;
                }
            } else {
                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        Uri resultUri = result.getUri();

                        imagePath = image_picker.getRealPathFromURI(resultUri);


                        //   previewCapturedImage();


                        // user_image.setImageBitmap(myBitmap);
                        tvUploadPicture.setText(imagePath);

                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                        Exception error = result.getError();
                        Log.e("error", "profilePicError :::: " + error.getMessage());
                        error.printStackTrace();
                    }
                } else {

                    super.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    private void reportIssue() {
        try {
            NetworkConn networkConn = NetworkConn.getInstance(mContext);

//                userid:16
//                trip_id:1
//                topic_id:16
//                mobile_no:9787456556465
//                email:a@b.com
//                comments:wegfwegfesgfesgfsegfsgf
//                picture: abc.png

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(mContext, AppPreference.KEY_USER_ID));
            hashMap.put(AppConstants.KEY_TRIP_ID, trip_id);
            hashMap.put(AppConstants.TOPIC_ID, selectedID);
            hashMap.put(AppConstants.EMAIL, etWriteEmail.getText().toString().trim());
            hashMap.put(AppConstants.COMMENT, getEtComment().getText().toString().trim());
            hashMap.put(AppConstants.KEY_MOBILE, etWriteMobNum.getText().toString().trim());

            int fileType = 0;
            if (!TextUtils.isEmpty(imagePath))
                fileType = 1;
            else
                fileType = 0;
            hashMap.put(AppConstants.PICTURE, imagePath);


            Log.e("Send Registration", "Registration: " + AppConstants.REPORT_ISSUE + "  :: " + hashMap.toString());

            networkConn.makeRequest(mContext, networkConn.createFormDataRequest(AppConstants.REPORT_ISSUE, hashMap, fileType), this, AppConstants.EVENT_REPORT_ISSUE);


            //  networkConn.makeRequest(mContext, networkConn.createFormDataRequest(AppConstants.REPORT_ISSUE, hashMap, fileType), this, AppConstants.EVENT_REPORT_ISSUE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
