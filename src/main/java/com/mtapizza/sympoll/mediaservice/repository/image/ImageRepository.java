package com.mtapizza.sympoll.mediaservice.repository.image;

import com.mtapizza.sympoll.mediaservice.model.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
}
