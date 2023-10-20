package com.example.fcinema_app.models;

import com.google.gson.JsonArray;

public class RequestData {
    private VeModel mVeModel;
    private ViTriGheModel mViTriGheModel;
    private JsonArray mJsonArray;

    public RequestData(VeModel veModel, ViTriGheModel viTriGheModel, JsonArray jsonArray) {
        mVeModel = veModel;
        mViTriGheModel = viTriGheModel;
        mJsonArray = jsonArray;
    }

    public VeModel getVeModel() {
        return mVeModel;
    }

    public void setVeModel(VeModel veModel) {
        mVeModel = veModel;
    }

    public ViTriGheModel getViTriGheModel() {
        return mViTriGheModel;
    }

    public void setViTriGheModel(ViTriGheModel viTriGheModel) {
        mViTriGheModel = viTriGheModel;
    }
}
