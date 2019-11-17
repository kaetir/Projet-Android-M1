package ovh.trustme.overdated.pojo;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("date")
    public String date;

    @SerializedName("name")
    public String name;


    @Override
    public String toString() {
        return name;
    }

}
