package com.app.fixy.network;

import com.app.fixy.models.GooglePlaceModal;
import com.app.fixy.models.LoginModel;
import com.app.fixy.models.NearbyPlaceModel;
import com.app.fixy.models.ProfileModel;

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
    @POST("phone_auth")
    Call<LoginModel> userSignup(@Field("country_code") String country_code,
                                @Field("phone") String phone);

    @FormUrlEncoded
    @POST("verify_otp")
    Call<LoginModel> verify_otp(@Field("auth_token") String auth_token,@Field("otp") String otp);


    @Multipart
    @POST("create_profile")
    Call<ProfileModel> create_profile(@Part("auth_token") String access_token,
                                      @Part("name") String Name,
                                      @Part("email") String email,
                                      @Part("gender") String gender,
                                      @Part("referral_code") String referral_code,
                                      @Part MultipartBody.Part image);


//
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