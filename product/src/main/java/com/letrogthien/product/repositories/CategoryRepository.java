package com.letrogthien.product.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letrogthien.product.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByParentId(UUID parentId);
    // Additional query methods can be defined here if needed

    List<Category> findByName(String name);

    List<Category> findByAttributeNameAndAttributeValue(String attributeName, String attributeValue);
}
