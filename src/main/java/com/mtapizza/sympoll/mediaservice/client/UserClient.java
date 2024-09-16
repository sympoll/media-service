package com.mtapizza.sympoll.mediaservice.client;

import com.mtapizza.sympoll.mediaservice.dto.request.user.upload.UserUpdateProfileBannerUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.upload.UserUpdateProfilePictureUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.response.user.update.UserUpdateProfileBannerUrlResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.user.update.UserUpdateProfilePictureUrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface UserClient {
    @PostExchange("/api/user/profile-picture-url")
    ResponseEntity<UserUpdateProfilePictureUrlResponse> userUpdateProfilePictureUrl(@RequestBody UserUpdateProfilePictureUrlRequest userUpdateProfilePictureUrlRequest);

    @PostExchange("/api/user/profile-banner-url")
    ResponseEntity<UserUpdateProfileBannerUrlResponse> userUpdateBannerPictureUrl(@RequestBody UserUpdateProfileBannerUrlRequest userUpdateProfileBannerUrlRequest);
}