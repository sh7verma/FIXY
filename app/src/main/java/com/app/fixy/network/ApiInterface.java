package com.app.fixy.network;

import com.app.fixy.models.CityModel;
import com.app.fixy.models.GooglePlaceModal;
import com.app.fixy.models.NearbyPlaceModel;
import com.app.fixy.models.ServicesModel;
import com.app.fixy.models.UserModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiInterface {

    //API Method to get Questions From API
    @GET
    public Call<GooglePlaceModal> getGooglePlaces(@Url String url);

    @GET
    public Call<NearbyPlaceModel> getGoogleNearByPlaces(@Url String url);

    @FormUrlEncoded
    @POST("api/v1/users/create_user")
    Call<UserModel> create_user(@Field("country_code") String country_code,
                                @Field("phone") String phone,
                                @Field("application_mode") String application_mode,
                                @Field("platform_type") String platform_type,
                                @Field("device_token") String device_token,
                                @Field("user_type") String user_type);

    @FormUrlEncoded
    @POST("api/v1/users/confirm_otp")
    Call<UserModel> confirm_otp(@Field("access_token") String access_token,
                                @Field("device_token") String device_token,
                                @Field("otp") String otp);

    @FormUrlEncoded
    @POST("api/v1/users/resend_otp")
    Call<UserModel> resend_otp(@Field("access_token") String access_token,
                               @Field("device_token") String device_token,
                               @Field("country_code") String country_code,
                               @Field("phone_number") String phone_number);

    @Multipart
    @POST("api/v1/users/create_profile")
    Call<UserModel> create_profile(@Part("access_token") RequestBody access_token,
                                   @Part("fullname") RequestBody Name,
                                   @Part("email") RequestBody email,
                                   @Part("gender") RequestBody gender,
                                   @Part("edit_profile") RequestBody edit_profile,
                                   @Part("referral_code") RequestBody referral_code,
                                   @Part("user_type") RequestBody user_type,
                                   @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("api/v1/users/city")
    Call<CityModel> city(@Field("access_token") String access_token,
                         @Field("device_token") String device_token);

    @FormUrlEncoded
    @POST("api/v1/services")
    Call<ServicesModel> services(@Field("access_token") String access_token,
                                 @Field("device_token") String device_token,
                                 @Field("city_id") String city_id);

    @FormUrlEncoded
    @POST("api/v1/requests/create_request")
    Call<ServicesModel> create_request(@Field("access_token") String access_token,
                                 @Field("category_id") String category_id,
                                 @Field("selected_user_id") String selected_user_id,
                                 @Field("city_id") String city_id,
                                 @Field("address") String address,
                                 @Field("additional_notes") String additional_notes,
                                 @Field("request_price") String request_price,
                                 @Field("required_coins") String required_coins,
                                 @Field("request_latitude") String request_latitude,
                                 @Field("request_longitude") String request_longitude);


//    @FormUrlEncoded
//    @POST("/users/signin")
//    Call<SignupModel> userSignin(@Field("email") String email,
//                                 @Field("password") String password,
//                                 @Field("platform_status") String platform_status,
//                                 @Field("device_token") String device_token);
//
//    @Multipart
//    @POST("/users/create_profile")
//    Call<SignupModel> createProfile(@Part("first_name") RequestBody first_name,
//                                    @Part("last_name") RequestBody last_name,
//                                    @Part("country_code") RequestBody country_code,
//                                    @Part("phone_number") RequestBody phone_number,
//                                    @Part("access_token") RequestBody access_token,
//                                    @Part MultipartBody.Part image);
//
//    @FormUrlEncoded
//    @POST("/users/verify")
//    Call<SignupModel> userVerify(@Field("access_token") String access_token,
//                                 @Field("otp") String otp);
//
//    @FormUrlEncoded
//    @POST("/users/call")
//    Call<CallResendModel> callUser(@Field("access_token") String access_token);
//
//    @FormUrlEncoded
//    @POST("/users/resend")
//    Call<CallResendModel> callResend(@Field("access_token") String access_token);
//
//    @FormUrlEncoded
//    @POST("/users/forget_password")
//    Call<CallResendModel> forgetPassword(@Field("email") String email);
//
//    @POST("/tools/categories")
//    Call<CategoriesModel> getCategories();
//
//
//    @Multipart
//    @POST("/users/update_profile")
//    Call<SignupModel> updateProfile(@Part("access_token") RequestBody access_token,
//                                    @Part("first_name") RequestBody first_name,
//                                    @Part("last_name") RequestBody last_name,
//                                    @Part MultipartBody.Part image);
//
//    @FormUrlEncoded
//    @POST("users/view_profile")
//    Call<ProfileModel> viewProfile(@Field("access_token") String access_token);
//
//    @Multipart
//    @POST("payment/stripe_account")
//    Call<CreateStripeAccountModel> stripeBank(@Part("access_token") RequestBody access_token,
//                                              @Part("country_code") RequestBody country_code,
//                                              @Part("currency") RequestBody currency,
//                                              @Part("account_number") RequestBody account_number,
//                                              @Part("first_name") RequestBody first_name,
//                                              @Part("last_name") RequestBody last_name,
//                                              @Part("address") RequestBody address,
//                                              @Part("city") RequestBody city,
//                                              @Part("state") RequestBody state,
//                                              @Part("postal_code") RequestBody postal_code,
//                                              @Part("dob") RequestBody dob,
//                                              @Part("sort_code") RequestBody sort_code,
//                                              @Part MultipartBody.Part document);
//
//    @Multipart
//    @POST("tools/create_tool")
//    Call<CreateTulModel> createTul(@Part("access_token") RequestBody access_token,
//                                   @Part("title") RequestBody title,
//                                   @Part("category_id") RequestBody category_id,
//                                   @Part("description") RequestBody description,
//                                   @Part("quantity") RequestBody quantity,
//                                   @Part("price") RequestBody price,
//                                   @Part("tool_currency") RequestBody tool_currency,
//                                   @Part("additional_price") RequestBody additional_price,
//                                   @Part("tool_address") RequestBody tool_address,
//                                   @Part("latitude") RequestBody latitude,
//                                   @Part("longitude") RequestBody longitude,
//                                   @Part("rules") RequestBody rules,
//                                   @Part("preferences") RequestBody preferences,
//                                   @Part("stripe_account") RequestBody stripe_account,
//                                   @Part List<MultipartBody.Part> files,
//                                   @Part MultipartBody.Part document,
//                                   @Part MultipartBody.Part v_thumbnail);
//
//    @Multipart
//    @POST("add_image")
//    Call<CreateTulModel> demoAPI(@Part List<MultipartBody.Part> files);
//
//    @FormUrlEncoded
//    @POST("tools/view_tool")
//    Call<ViewTulModel> viewTul(@Field("access_token") String access_token,
//                               @Field("tool_id") int tool_id);
//
//    @FormUrlEncoded
//    @POST("tools/delete_tool")
//    Call<DemoModel> deleteTul(@Field("access_token") String access_token,
//                              @Field("tool_id") int tool_id);
//
//
//    @FormUrlEncoded
//    @POST("tools/near_by_tools")
//    Call<NearByTulModel> nearByTools(@Field("latitude") String latitude,
//                                     @Field("longitude") String longitude);
//
//    @FormUrlEncoded
//    @POST("tools/get_landing_tools")
//    Call<NearByTulListingModel> getNearByToolsList(@Field("access_token") String access_token,
//                                                   @Field("tool_ids") String tool_ids);
//
//    @FormUrlEncoded
//    @POST("tools/tools_by_category")
//    Call<NearByTulListingModel> getToolsByCategory(@Field("access_token") String access_token,
//                                                   @Field("category_id") String category_id,
//                                                   @Field("offset") int offset);
//
//    @FormUrlEncoded
//    @POST("users/wishlist")
//    Call<DemoModel> shortListTul(@Field("access_token") String access_token,
//                                 @Field("tool_id") int tool_id);
//
//    @FormUrlEncoded
//    @POST("feedback/report")
//    Call<DemoModel> ReportTul(@Field("access_token") String access_token,
//                              @Field("tool_id") int tool_id);
//
//    @FormUrlEncoded
//    @POST("payment/view_cards")
//    Call<CardModel> viewCard(@Field("access_token") String access_token);
//
//    @FormUrlEncoded
//    @POST("payment/delete_card")
//    Call<DeleteCardModel> deleteCard(@Field("access_token") String access_token, @Field("card_id") Integer card_id);
//
//    @FormUrlEncoded
//    @POST("payment/save_card")
//    Call<CardLocalModel> saveCard(@Field("access_token") String access_token,
//                                  @Field("card_number") String card_number,
//                                  @Field("name_on_card") String name_on_card,
//                                  @Field("expiry_month") Integer expiry_month,
//                                  @Field("expiry_year") Integer expiry_year);
//
//    @FormUrlEncoded
//    @POST("payment/bank_accounts")
//    Call<AccountModel> getAccounts(@Field("access_token") String access_token);
//
//
//    @FormUrlEncoded
//    @POST("payment/delete_bank_account")
//    Call<DemoModel> deleteAccount(@Field("access_token") String access_token,
//                                  @Field("account_id") int account_id);
//
//
//    @FormUrlEncoded
//    @POST("payment/make_account_primary")
//    Call<DemoModel> makePrimaryAccount(@Field("access_token") String access_token,
//                                       @Field("account_id") int account_id);
//
}