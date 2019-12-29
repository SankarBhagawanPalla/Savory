package com.springworks.savory.repositories;

import com.springworks.savory.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
