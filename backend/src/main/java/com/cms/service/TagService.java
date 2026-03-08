package com.cms.service;

import com.cms.entity.Tag;
import com.cms.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAllByOrderByNameAsc();
    }

    public Tag findById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Transactional
    public Tag create(Tag tag) {
        if (tagRepository.existsByName(tag.getName())) {
            throw new RuntimeException("标签名称已存在");
        }
        return tagRepository.save(tag);
    }

    @Transactional
    public Tag update(Long id, Tag tag) {
        Tag existing = tagRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setName(tag.getName());
        return tagRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}