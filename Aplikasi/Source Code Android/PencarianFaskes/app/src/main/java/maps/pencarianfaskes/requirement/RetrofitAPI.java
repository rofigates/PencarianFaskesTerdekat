package maps.pencarianfaskes.requirement;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by RedLyst on 03/05/2017.
 */

public interface RetrofitAPI {


    @GET("/android/viewAllLaundry.php")
    Call<Values> viewAllLaundry();

    @FormUrlEncoded
    @POST("/android/searchLaundry.php")
    Call<Values> searchLaundry(@Field("search") String search);


}
