package com.cms.controller;

import com.cms.dto.ApiResponse;
import com.cms.entity.Tag;
import com.cms.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ApiResponse<List<Tag>> findAll() {
        return ApiResponse.success(tagService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Tag> findById(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        if (tag != null) {
            return ApiResponse.success(tag);
        }
        return ApiResponse.error("标签不存在");
    }

    @PostMapping
    public ApiResponse<Tag> create(@RequestBody Tag tag) {
        try {
            return ApiResponse.success("标签创建成功", tagService.create(tag));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Tag> update(@PathVariable Long id, @RequestBody Tag tag) {
        try {
            Tag updated = tagService.update(id, tag);
            if (updated != null) {
                return ApiResponse.success("标签更新成功", updated);
            }
            return ApiResponse.error("标签不存在");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ApiResponse.success("标签删除成功", null);
    }
}