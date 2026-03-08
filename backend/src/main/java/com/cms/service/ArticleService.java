package com.cms.service;

import com.cms.dto.ArticleRequest;
import com.cms.entity.Article;
import com.cms.entity.Category;
import com.cms.entity.Tag;
import com.cms.entity.User;
import com.cms.repository.ArticleRepository;
import com.cms.repository.CategoryRepository;
import com.cms.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Page<Article> findByStatus(String status, Pageable pageable) {
        return articleRepository.findByStatus(status, pageable);
    }

    public Article findById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Page<Article> findByCategory(Long categoryId, Pageable pageable) {
        return articleRepository.findByCategoryId(categoryId, pageable);
    }

    public Page<Article> findByTag(Long tagId, Pageable pageable) {
        return articleRepository.findByTagId(tagId, pageable);
    }

    public Page<Article> searchByKeyword(String keyword, Pageable pageable) {
        return articleRepository.searchByKeyword(keyword, pageable);
    }

    public List<Article> findLatestArticles() {
        return articleRepository.findTop10ByStatusOrderByCreatedAtDesc("PUBLISHED");
    }

    @Transactional
    public Article create(ArticleRequest request, User author) {
        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setSummary(request.getSummary());
        article.setCoverImage(request.getCoverImage());
        article.setAuthor(author);
        article.setStatus(request.getStatus() != null ? request.getStatus() : "DRAFT");

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
            article.setCategory(category);
        }

        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            Set<Tag> tags = request.getTagIds().stream()
                    .map(tagRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            article.setTags(tags);
        }

        return articleRepository.save(article);
    }

    @Transactional
    public Article update(Long id, ArticleRequest request, User author) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return null;
        }

        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setSummary(request.getSummary());
        article.setCoverImage(request.getCoverImage());
        article.setStatus(request.getStatus() != null ? request.getStatus() : article.getStatus());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
            article.setCategory(category);
        }

        if (request.getTagIds() != null) {
            Set<Tag> tags = request.getTagIds().stream()
                    .map(tagRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            article.setTags(tags);
        }

        return articleRepository.save(article);
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    @Transactional
    public Article incrementViews(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article != null) {
            article.setViews(article.getViews() + 1);
            return articleRepository.save(article);
        }
        return null;
    }
}