package com.mtapizza.sympoll.mediaservice.model.image;

import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageDetailsResponse;
import com.mtapizza.sympoll.mediaservice.model.owner.type.OwnerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="owner_id")
    private String ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name="owner_type")
    private OwnerType ownerType; // types are: USER / GROUP

    @Column(name = "type")
    private String type;

    @Column(name = "time_created")
    private final LocalDateTime timeUploaded = LocalDateTime.now(); // Initialize to the current time.

    @Column(name = "data", columnDefinition="bytea")
    private byte[] data;

    /**
     * Fetch the details of the image.
     * @return All details of the image.
     */
    public ImageDetailsResponse toImageDetailsResponse() {
        return new ImageDetailsResponse(
                this.ownerId,
                this.id,
                this.name,
                this.type,
                this.timeUploaded
        );
    }
}
