package com.mtapizza.sympoll.mediaservice.client;

import com.mtapizza.sympoll.mediaservice.dto.request.user.upload.UserUpdateBannerPictureUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.upload.UserUpdateProfilePictureUrlRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import java.util.UUID;

public interface UserClient {
    @PostExchange("/api/user/profile-picture-url")
    UUID userUpdateProfilePictureUrl(@RequestBody UserUpdateProfilePictureUrlRequest userUpdateProfilePictureUrlRequest);

    @PostExchange("/api/user/profile-banner-url")
    UUID userUpdateBannerPictureUrl(@RequestBody UserUpdateBannerPictureUrlRequest userUpdateBannerPictureUrlRequest);
}