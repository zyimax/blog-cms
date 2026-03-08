package com.cms.dto;

import lombok.Data;
import java.util.List;

@Data
public class ArticleRequest {
    private String title;
    private String content;
    private String summary;
    private String coverImage;
    private Long categoryId;
    private String status;
    private List<Long> tagIds;
}