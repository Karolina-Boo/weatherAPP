package mg.studio.weatherappdesign;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("coord")
    public Coord coord;
    @SerializedName("country")
    public String country;

    public class Coord {
        @SerializedName("lat")
        public float lat;
        @SerializedName("lon")
        public float lon;
    }
}
