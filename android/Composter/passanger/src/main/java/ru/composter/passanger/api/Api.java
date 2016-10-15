package ru.composter.passanger.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.composter.passanger.http.request.AuthRequest;
import ru.composter.passanger.http.request.RegistrationRequest;
import ru.composter.passanger.http.response.ProfileResponse;

public interface Api  {

    @POST("user.php?m=auth")
    public Call<ProfileResponse> auth(@Body AuthRequest authRequest);

    @POST("user.php?m=register")
    public Call<ProfileResponse> registration(@Body RegistrationRequest registrationRequest);

}
