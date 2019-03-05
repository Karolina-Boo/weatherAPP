package mg.studio.weatherappdesign;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LList {
    public String dt;
    @SerializedName("main")
    public maininfo main;
    @SerializedName("weather")
    public List<weatherinfo> weatherinfoList;
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("rain")
    public Rain rain;
    @SerializedName("sys")
    public Sys sys;
    @SerializedName("dt_txt")
    public Dt_txt dt_txt;
}
