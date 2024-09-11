package com.mtapizza.sympoll.mediaservice.repository.image;

import com.mtapizza.sympoll.mediaservice.model.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
    void deleteByOwnerId(String ownerId);
}
