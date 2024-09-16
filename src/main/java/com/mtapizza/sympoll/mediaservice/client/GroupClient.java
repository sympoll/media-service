package com.mtapizza.sympoll.mediaservice.client;

import com.mtapizza.sympoll.mediaservice.dto.request.group.upload.GroupUpdateProfileBannerUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.group.upload.GroupUpdateProfilePictureUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.response.group.update.GroupUpdateProfileBannerUrlResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.group.update.GroupUpdateProfilePictureUrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface GroupClient {
    @PostExchange("/api/group/profile-picture-url")
    ResponseEntity<GroupUpdateProfilePictureUrlResponse> groupUpdateProfilePictureUrl(@RequestBody GroupUpdateProfilePictureUrlRequest groupUpdateProfilePictureUrlRequest);

    @PostExchange("/api/group/profile-banner-url")
    ResponseEntity<GroupUpdateProfileBannerUrlResponse> groupUpdateBannerPictureUrl(@RequestBody GroupUpdateProfileBannerUrlRequest groupUpdateProfileBannerUrlRequest);
}
