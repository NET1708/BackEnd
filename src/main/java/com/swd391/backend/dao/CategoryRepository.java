package com.swd391.backend.dao;

import com.swd391.backend.entity.Category;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Tag(name = "Category", description = "Category management APIs")
@RepositoryRestResource(path = "category")
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
