import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { articleApi } from '../services/api';
import './Home.css';

function Home() {
  const [latestArticles, setLatestArticles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchLatestArticles();
  }, []);

  const fetchLatestArticles = async () => {
    try {
      setLoading(true);
      const response = await articleApi.getLatest();
      setLatestArticles(response.data.data);
    } catch (err) {
      setError('获取文章失败');
      console.error('Error fetching articles:', err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">加载中...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="home">
      <div className="container">
        <section className="hero">
          <h1>欢迎来到 Blog CMS</h1>
          <p>一个简单而强大的内容管理系统</p>
        </section>

        <section className="latest-articles">
          <h2>最新文章</h2>
          {latestArticles.length === 0 ? (
            <p className="no-articles">暂无文章</p>
          ) : (
            <div className="articles-grid">
              {latestArticles.map((article) => (
                <div key={article.id} className="article-card">
                  {article.coverImage && (
                    <img 
                      src={article.coverImage} 
                      alt={article.title} 
                      className="article-cover"
                    />
                  )}
                  <div className="article-content">
                    <h3>
                      <Link to={`/articles/${article.id}`}>
                        {article.title}
                      </Link>
                    </h3>
                    <p className="article-summary">
                      {article.summary || article.content.substring(0, 100) + '...'}
                    </p>
                    <div className="article-meta">
                      <span className="article-author">
                        {article.author?.nickname || article.author?.username}
                      </span>
                      <span className="article-date">
                        {new Date(article.createdAt).toLocaleDateString('zh-CN')}
                      </span>
                      <span className="article-views">
                        {article.views} 阅读
                      </span>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </section>
      </div>
    </div>
  );
}

export default Home;