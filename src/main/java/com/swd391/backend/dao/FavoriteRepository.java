package com.swd391.backend.dao;

import com.swd391.backend.entity.Favorite;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Tag(name = "Favorite", description = "Favorite management APIs")
@RepositoryRestResource(path = "favorite")
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
}
