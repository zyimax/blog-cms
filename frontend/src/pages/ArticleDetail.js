import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { articleApi } from '../services/api';
import './ArticleDetail.css';

function ArticleDetail() {
  const { id } = useParams();
  const [article, setArticle] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchArticle();
  }, [id]);

  const fetchArticle = async () => {
    try {
      setLoading(true);
      const response = await articleApi.getById(id);
      setArticle(response.data.data);
    } catch (err) {
      setError('文章不存在或已被删除');
      console.error('Error fetching article:', err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">加载中...</div>;
  }

  if (error || !article) {
    return (
      <div className="error">
        {error || '文章不存在'}
        <Link to="/articles" className="back-link">返回文章列表</Link>
      </div>
    );
  }

  return (
    <div className="article-detail">
      <div className="container">
        <Link to="/articles" className="back-link">
          &larr; 返回文章列表
        </Link>

        <article className="article">
          <header className="article-header">
            <h1>{article.title}</h1>
            <div className="article-meta">
              <span className="article-author">
                作者: {article.author?.nickname || article.author?.username}
              </span>
              <span className="article-date">
                发布于: {new Date(article.createdAt).toLocaleString('zh-CN')}
              </span>
              <span className="article-views">
                {article.views} 阅读
              </span>
              {article.category && (
                <span className="article-category">
                  分类: {article.category.name}
                </span>
              )}
            </div>
            {article.tags && article.tags.length > 0 && (
              <div className="article-tags">
                {article.tags.map((tag) => (
                  <span key={tag.id} className="tag">
                    {tag.name}
                  </span>
                ))}
              </div>
            )}
          </header>

          {article.coverImage && (
            <img
              src={article.coverImage}
              alt={article.title}
              className="article-cover"
            />
          )}

          <div 
            className="article-content"
            dangerouslySetInnerHTML={{ __html: article.content }}
          />
        </article>
      </div>
    </div>
  );
}

export default ArticleDetail;