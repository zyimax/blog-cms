import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { articleApi, categoryApi, tagApi } from '../services/api';
import './ArticleList.css';

function ArticleList() {
  const [articles, setArticles] = useState([]);
  const [categories, setCategories] = useState([]);
  const [tags, setTags] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');
  const [selectedTag, setSelectedTag] = useState('');
  const [keyword, setKeyword] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);

  const fetchCategories = async () => {
    try {
      const response = await categoryApi.getAll();
      setCategories(response.data.data);
    } catch (err) {
      console.error('Error fetching categories:', err);
    }
  };

  const fetchTags = async () => {
    try {
      const response = await tagApi.getAll();
      setTags(response.data.data);
    } catch (err) {
      console.error('Error fetching tags:', err);
    }
  };

  const fetchArticles = async () => {
    try {
      setLoading(true);
      let response;
      if (keyword) {
        response = await articleApi.search(keyword, page);
      } else if (selectedCategory) {
        response = await articleApi.getByCategory(selectedCategory, page);
      } else if (selectedTag) {
        response = await articleApi.getByTag(selectedTag, page);
      } else {
        response = await articleApi.getPublished(page);
      }
      setArticles(response.data.data.content);
      setTotalPages(response.data.data.totalPages);
    } catch (err) {
      console.error('Error fetching articles:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCategories();
    fetchTags();
    fetchArticles();
  }, [page, selectedCategory, selectedTag, keyword]);

  const handleSearch = (e) => {
    e.preventDefault();
    setPage(0);
    fetchArticles();
  };

  const handlePageChange = (newPage) => {
    setPage(newPage);
    window.scrollTo(0, 0);
  };

  return (
    <div className="article-list">
      <div className="container">
        <div className="sidebar">
          <div className="filter-section">
            <h3>搜索文章</h3>
            <form onSubmit={handleSearch} className="search-form">
              <input
                type="text"
                placeholder="输入关键词..."
                value={keyword}
                onChange={(e) => setKeyword(e.target.value)}
                className="search-input"
              />
              <button type="submit" className="btn btn-primary">
                搜索
              </button>
            </form>
          </div>

          <div className="filter-section">
            <h3>分类</h3>
            <ul className="category-list">
              <li>
                <button
                  className={selectedCategory === '' ? 'active' : ''}
                  onClick={() => {
                    setSelectedCategory('');
                    setPage(0);
                  }}
                >
                  全部
                </button>
              </li>
              {categories.map((category) => (
                <li key={category.id}>
                  <button
                    className={selectedCategory === category.id ? 'active' : ''}
                    onClick={() => {
                      setSelectedCategory(category.id);
                      setSelectedTag('');
                      setPage(0);
                    }}
                  >
                    {category.name}
                  </button>
                </li>
              ))}
            </ul>
          </div>

          <div className="filter-section">
            <h3>标签</h3>
            <div className="tag-list">
              {tags.map((tag) => (
                <button
                  key={tag.id}
                  className={selectedTag === tag.id ? 'tag-btn active' : 'tag-btn'}
                  onClick={() => {
                    setSelectedTag(tag.id);
                    setSelectedCategory('');
                    setPage(0);
                  }}
                >
                  {tag.name}
                </button>
              ))}
            </div>
          </div>
        </div>

        <div className="content">
          <h2>文章列表</h2>
          {loading ? (
            <div className="loading">加载中...</div>
          ) : articles.length === 0 ? (
            <div className="no-articles">暂无文章</div>
          ) : (
            <>
              <div className="articles">
                {articles.map((article) => (
                  <article key={article.id} className="article-item">
                    {article.coverImage && (
                      <img
                        src={article.coverImage}
                        alt={article.title}
                        className="article-item-cover"
                      />
                    )}
                    <div className="article-item-content">
                      <h3>
                        <Link to={`/articles/${article.id}`}>
                          {article.title}
                        </Link>
                      </h3>
                      <p className="article-item-summary">
                        {article.summary || article.content.substring(0, 150) + '...'}
                      </p>
                      <div className="article-item-meta">
                        <span className="article-item-author">
                          {article.author?.nickname || article.author?.username}
                        </span>
                        <span className="article-item-date">
                          {new Date(article.createdAt).toLocaleDateString('zh-CN')}
                        </span>
                        <span className="article-item-views">
                          {article.views} 阅读
                        </span>
                        {article.category && (
                          <span className="article-item-category">
                            {article.category.name}
                          </span>
                        )}
                      </div>
                    </div>
                  </article>
                ))}
              </div>

              {totalPages > 1 && (
                <div className="pagination">
                  <button
                    className="btn btn-secondary"
                    onClick={() => handlePageChange(page - 1)}
                    disabled={page === 0}
                  >
                    上一页
                  </button>
                  <span className="page-info">
                    第 {page + 1} / {totalPages} 页
                  </span>
                  <button
                    className="btn btn-secondary"
                    onClick={() => handlePageChange(page + 1)}
                    disabled={page === totalPages - 1}
                  >
                    下一页
                  </button>
                </div>
              )}
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default ArticleList;
