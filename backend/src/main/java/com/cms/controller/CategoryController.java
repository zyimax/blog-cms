package com.cms.controller;

import com.cms.dto.ApiResponse;
import com.cms.entity.Category;
import com.cms.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<List<Category>> findAll() {
        return ApiResponse.success(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Category> findById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category != null) {
            return ApiResponse.success(category);
        }
        return ApiResponse.error("分类不存在");
    }

    @PostMapping
    public ApiResponse<Category> create(@RequestBody Category category) {
        try {
            return ApiResponse.success("分类创建成功", categoryService.create(category));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Category> update(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updated = categoryService.update(id, category);
            if (updated != null) {
                return ApiResponse.success("分类更新成功", updated);
            }
            return ApiResponse.error("分类不存在");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ApiResponse.success("分类删除成功", null);
    }
}