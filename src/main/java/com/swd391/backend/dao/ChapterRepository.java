package com.swd391.backend.dao;

import com.swd391.backend.entity.Chapter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Tag(name = "Chapter", description = "Chapter management APIs")
@RepositoryRestResource(path = "chapter")
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
}
