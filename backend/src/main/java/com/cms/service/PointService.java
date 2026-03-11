package com.cms.service;

import com.cms.entity.User;
import com.cms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointService {
    private final UserRepository userRepository;

    public PointService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 积分规则
    private static final int POINTS_PER_LOGIN = 1;
    private static final int POINTS_PER_ARTICLE = 10;
    private static final int POINTS_PER_COMMENT = 5;
    private static final int POINTS_PER_LIKE_RECEIVED = 2;
    private static final int POINTS_PER_ARTICLE_LIKED = 1;
    
    // 经验值规则
    private static final int EXP_PER_LOGIN = 5;
    private static final int EXP_PER_ARTICLE = 50;
    private static final int EXP_PER_COMMENT = 20;
    private static final int EXP_PER_LIKE_RECEIVED = 10;
    
    // 升级规则
    private static final int[] LEVEL_EXP_REQUIREMENTS = {
        0, 100, 300, 600, 1000, 1500, 2100, 2800, 3600, 4500,
        5500, 6600, 7800, 9100, 10500, 12000, 13600, 15300, 17100, 19000
    };

    @Transactional
    public User addLoginPoints(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        user.setPoints(user.getPoints() + POINTS_PER_LOGIN);
        addExperience(user, EXP_PER_LOGIN);
        
        return userRepository.save(user);
    }

    @Transactional
    public User addArticlePoints(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        user.setPoints(user.getPoints() + POINTS_PER_ARTICLE);
        user.setArticleCount(user.getArticleCount() + 1);
        addExperience(user, EXP_PER_ARTICLE);
        
        return userRepository.save(user);
    }

    @Transactional
    public User addCommentPoints(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        user.setPoints(user.getPoints() + POINTS_PER_COMMENT);
        user.setCommentCount(user.getCommentCount() + 1);
        addExperience(user, EXP_PER_COMMENT);
        
        return userRepository.save(user);
    }

    @Transactional
    public User addLikeReceivedPoints(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        user.setPoints(user.getPoints() + POINTS_PER_LIKE_RECEIVED);
        user.setLikeCount(user.getLikeCount() + 1);
        addExperience(user, EXP_PER_LIKE_RECEIVED);
        
        return userRepository.save(user);
    }

    @Transactional
    public User addArticleLikedPoints(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        user.setPoints(user.getPoints() + POINTS_PER_ARTICLE_LIKED);
        return userRepository.save(user);
    }

    @Transactional
    public User deductPoints(Long userId, int points) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        if (user.getPoints() < points) {
            throw new RuntimeException("积分不足");
        }
        
        user.setPoints(user.getPoints() - points);
        return userRepository.save(user);
    }

    @Transactional
    public User setPoints(Long userId, int points) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        user.setPoints(points);
        return userRepository.save(user);
    }

    private void addExperience(User user, int exp) {
        user.setExperience(user.getExperience() + exp);
        
        // 检查是否升级
        int currentLevel = user.getLevel();
        int nextLevelExp = getExpRequiredForLevel(currentLevel + 1);
        
        if (user.getExperience() >= nextLevelExp && nextLevelExp > 0) {
            user.setLevel(currentLevel + 1);
        }
    }

    private int getExpRequiredForLevel(int level) {
        if (level >= 0 && level < LEVEL_EXP_REQUIREMENTS.length) {
            return LEVEL_EXP_REQUIREMENTS[level];
        }
        return -1; // 最高等级
    }

    public int getNextLevelExpRequired(int currentLevel) {
        return getExpRequiredForLevel(currentLevel + 1);
    }

    public int getExpToNextLevel(int currentExp, int currentLevel) {
        int nextLevelExp = getExpRequiredForLevel(currentLevel + 1);
        if (nextLevelExp == -1) {
            return 0; // 已经是最高等级
        }
        return nextLevelExp - currentExp;
    }

    public double getLevelProgress(int currentExp, int currentLevel) {
        int currentLevelExp = getExpRequiredForLevel(currentLevel);
        int nextLevelExp = getExpRequiredForLevel(currentLevel + 1);
        
        if (nextLevelExp == -1) {
            return 1.0; // 最高等级，进度100%
        }
        
        int expRange = nextLevelExp - currentLevelExp;
        int expProgress = currentExp - currentLevelExp;
        
        return (double) expProgress / expRange;
    }
}