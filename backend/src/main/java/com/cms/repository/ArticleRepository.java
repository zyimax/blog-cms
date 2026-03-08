package com.cms.repository;

import com.cms.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByStatus(String status, Pageable pageable);
    Page<Article> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Article> findByAuthorId(Long authorId, Pageable pageable);
    
    @Query("SELECT a FROM Article a JOIN a.tags t WHERE t.id = :tagId")
    Page<Article> findByTagId(@Param("tagId") Long tagId, Pageable pageable);
    
    @Query("SELECT a FROM Article a WHERE a.title LIKE %:keyword% OR a.content LIKE %:keyword%")
    Page<Article> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    List<Article> findTop10ByStatusOrderByCreatedAtDesc(String status);
}