package mg.studio.weatherappdesign;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather{
    public String cod;
    public float message;
    public int cnt;
    @SerializedName("list")
    public List<LList> listList;
    public City city;
}
