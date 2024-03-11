package com.swd391.backend.dao;

import com.swd391.backend.entity.Rate;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Tag(name = "Rate", description = "Rate management APIs")
@RepositoryRestResource(path = "rate")
public interface RateRepository extends JpaRepository<Rate, Integer> {

}
