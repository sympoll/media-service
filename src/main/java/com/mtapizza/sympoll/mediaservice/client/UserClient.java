package com.mtapizza.sympoll.mediaservice.client;

import com.mtapizza.sympoll.mediaservice.dto.request.user.UserUpdateBannerPictureUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.UserUpdateProfilePictureUrlRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.UUID;

public interface UserClient {
    @PostExchange("/api/user/profile-picture-url")
    UUID userUpdateProfilePictureUrl(@RequestBody UserUpdateProfilePictureUrlRequest userUpdateProfilePictureUrlRequest);

    @GetExchange("/api/user/profile-banner-url")
    UUID userUpdateBannerPictureUrl(@RequestBody UserUpdateBannerPictureUrlRequest userUpdateBannerPictureUrlRequest);
}