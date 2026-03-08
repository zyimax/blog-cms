import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

function Header() {
  return (
    <header className="header">
      <div className="container">
        <nav className="nav">
          <Link to="/" className="logo">
            Blog CMS
          </Link>
          <ul className="nav-menu">
            <li>
              <Link to="/">首页</Link>
            </li>
            <li>
              <Link to="/articles">文章列表</Link>
            </li>
            <li>
              <Link to="/login">登录</Link>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  );
}

export default Header;