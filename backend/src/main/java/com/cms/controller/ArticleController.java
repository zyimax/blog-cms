package com.cms.controller;

import com.cms.dto.ApiResponse;
import com.cms.dto.ArticleRequest;
import com.cms.entity.Article;
import com.cms.entity.User;
import com.cms.service.ArticleService;
import com.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping
    public ApiResponse<Page<Article>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return ApiResponse.success(articleService.findAll(pageable));
    }

    @GetMapping("/published")
    public ApiResponse<Page<Article>> findPublished(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ApiResponse.success(articleService.findByStatus("PUBLISHED", pageable));
    }

    @GetMapping("/latest")
    public ApiResponse<java.util.List<Article>> findLatest() {
        return ApiResponse.success(articleService.findLatestArticles());
    }

    @GetMapping("/{id}")
    public ApiResponse<Article> findById(@PathVariable Long id) {
        Article article = articleService.findById(id);
        if (article != null) {
            articleService.incrementViews(id);
            return ApiResponse.success(article);
        }
        return ApiResponse.error("文章不存在");
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<Page<Article>> findByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ApiResponse.success(articleService.findByCategory(categoryId, pageable));
    }

    @GetMapping("/tag/{tagId}")
    public ApiResponse<Page<Article>> findByTag(
            @PathVariable Long tagId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ApiResponse.success(articleService.findByTag(tagId, pageable));
    }

    @GetMapping("/search")
    public ApiResponse<Page<Article>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ApiResponse.success(articleService.searchByKeyword(keyword, pageable));
    }

    @PostMapping
    public ApiResponse<Article> create(@RequestBody ArticleRequest request) {
        try {
            User author = userService.findById(1L);
            Article article = articleService.create(request, author);
            return ApiResponse.success("文章创建成功", article);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Article> update(@PathVariable Long id, @RequestBody ArticleRequest request) {
        try {
            User author = userService.findById(1L);
            Article article = articleService.update(id, request, author);
            if (article != null) {
                return ApiResponse.success("文章更新成功", article);
            }
            return ApiResponse.error("文章不存在");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ApiResponse.success("文章删除成功", null);
    }
}