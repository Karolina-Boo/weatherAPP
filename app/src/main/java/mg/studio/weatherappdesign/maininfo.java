package mg.studio.weatherappdesign;

import com.google.gson.annotations.SerializedName;

public class maininfo {
    @SerializedName("temp")
    public int temp;
    @SerializedName("temp_min")
    public int temp_min;
    @SerializedName("temp_max")
    public int temp_max;
    @SerializedName("pressure")
    public int pressure;
    @SerializedName("sea_level")
    public int sea_level;
    @SerializedName("grnd_level")
    public int grnd_level;
    @SerializedName("humidity")
    public int humidity;
    @SerializedName("temp_kf")
    public int temp_kf;
}
