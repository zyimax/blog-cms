package com.cms.service;

import com.cms.entity.Category;
import com.cms.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAllByOrderByNameAsc();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional
    public Category create(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("分类名称已存在");
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long id, Category category) {
        Category existing = categoryRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return categoryRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}