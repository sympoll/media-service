package com.mtapizza.sympoll.mediaservice.model.image;

import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageDetailsResponse;
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

    @Column(name = "type")
    private String type;

    @Column(name = "time_created")
    private final LocalDateTime timeCreated = LocalDateTime.now(); // Initialize to the current time.

    @Column(name = "data", columnDefinition="bytea")
    private byte[] data;

    /**
     * Fetch the details of the image.
     * @return All details of the image.
     */
    public ImageDetailsResponse toImageDetailsResponse() {
        return new ImageDetailsResponse(
            this.id,
            this.name,
            this.type,
            this.timeCreated
        );
    }
}
