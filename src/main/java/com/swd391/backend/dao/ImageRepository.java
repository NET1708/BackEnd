package com.swd391.backend.dao;

import com.swd391.backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "image")
public interface ImageRepository extends JpaRepository<Image, Integer> {
}
