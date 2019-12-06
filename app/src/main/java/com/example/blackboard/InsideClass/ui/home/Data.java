package com.example.blackboard.InsideClass.ui.home;

public class Data {
    String uploaded_by;
    String data;
    String date_and_time;

    public Data(String uploaded_by, String data, String date_and_time) {
        this.uploaded_by = uploaded_by;
        this.data = data;
        this.date_and_time = date_and_time;
    }

    public Data() {
    }

    public String getUploaded_by() {
        return uploaded_by;
    }

    public void setUploaded_by(String uploaded_by) {
        this.uploaded_by = uploaded_by;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate_and_time() {
        return date_and_time;
    }

    public void setDate_and_time(String date_and_time) {
        this.date_and_time = date_and_time;
    }
}
