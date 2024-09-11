package com.mtapizza.sympoll.mediaservice.client;

import com.mtapizza.sympoll.mediaservice.dto.request.group.upload.GroupUpdateBannerPictureUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.group.upload.GroupUpdateProfilePictureUrlRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface GroupClient {
    @PostExchange("/api/group/profile-picture-url")
    String groupUpdateProfilePictureUrl(@RequestBody GroupUpdateProfilePictureUrlRequest groupUpdateProfilePictureUrlRequest);

    @PostExchange("/api/group/profile-banner-url")
    String groupUpdateBannerPictureUrl(@RequestBody GroupUpdateBannerPictureUrlRequest groupUpdateBannerPictureUrlRequest);
}
