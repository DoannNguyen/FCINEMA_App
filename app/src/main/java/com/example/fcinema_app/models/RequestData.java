package com.example.fcinema_app.models;

import com.google.gson.JsonArray;

import java.util.List;

public class RequestData {
    private VeModel mVeModel;
    private ViTriGheModel mViTriGheModel;
    private String mJsonArray;
    private List<DoAnModel> mDoAnModels;

    public RequestData(VeModel veModel, ViTriGheModel viTriGheModel, String jsonArray, List<DoAnModel> doAnModels) {
        mVeModel = veModel;
        mViTriGheModel = viTriGheModel;
        mJsonArray = jsonArray;
        mDoAnModels = doAnModels;
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
