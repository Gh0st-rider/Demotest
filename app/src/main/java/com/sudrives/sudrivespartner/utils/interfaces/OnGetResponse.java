package com.sudrives.sudrivespartner.utils.interfaces;

import com.sudrives.sudrivespartner.models.ProfileModel;

import org.json.JSONObject;

public interface OnGetResponse {

    public void onSuccessfullGetResponse(ProfileModel loginModel, String from);
    public void onSuccessfullGetResponse(JSONObject json, String from);

}
