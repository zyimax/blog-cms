# Blog CMS - 内容管理系统

## 项目简介

Blog CMS 是一个基于前后端分离架构的内容管理系统，支持文章发布、分类管理、标签管理、用户注册登录等功能。系统采用现代化的技术栈，界面简洁美观，操作便捷，适合个人博客、新闻网站、知识库等多种场景使用。

**核心功能：**
- 文章管理：发布、编辑、删除文章，支持富文本内容
- 分类管理：创建和管理文章分类
- 标签管理：为文章添加多个标签，便于检索
- 用户系统：用户注册、登录、个人信息管理
- 文章浏览：文章列表、文章详情、分类浏览、标签筛选
- 搜索功能：支持关键词搜索文章
- 阅读统计：记录文章阅读次数

**目标用户：**
- 个人博主
- 小型团队
- 内容创作者
- 需要快速搭建博客系统的开发者

**创新点：**
- 前后端完全分离，便于维护和扩展
- RESTful API 设计，接口规范统一
- 响应式设计，支持多端访问
- 模块化架构，代码结构清晰
- 支持文章分类和标签双重分类体系

## 技术栈

### 前端
- **框架：** React 18.2.0
- **路由：** React Router DOM 6.20.0
- **HTTP客户端：** Axios 1.6.2
- **构建工具：** React Scripts 5.0.1
- **样式：** CSS3

### 后端
- **框架：** Spring Boot 3.2.0
- **语言：** Java 17
- **构建工具：** Maven
- **ORM框架：** Spring Data JPA
- **数据库：** MySQL 8.0

### 数据库
- **数据库：** MySQL 8.0
- **连接驱动：** MySQL Connector Java 8.0.33

### API
- RESTful API
- JSON 数据格式
- CORS 跨域支持

## 项目结构

```
ai_zuoye1/
├── backend/                 # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/cms/
│   │   │   │   ├── BlogCmsApplication.java    # 启动类
│   │   │   │   ├── config/                     # 配置类
│   │   │   │   │   └── CorsConfig.java
│   │   │   │   ├── controller/                 # 控制器
│   │   │   │   │   ├── ArticleController.java
│   │   │   │   │   ├── CategoryController.java
│   │   │   │   │   ├── TagController.java
│   │   │   │   │   └── UserController.java
│   │   │   │   ├── dto/                        # 数据传输对象
│   │   │   │   │   ├── ApiResponse.java
│   │   │   │   │   ├── ArticleRequest.java
│   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   └── RegisterRequest.java
│   │   │   │   ├── entity/                     # 实体类
│   │   │   │   │   ├── Article.java
│   │   │   │   │   ├── Category.java
│   │   │   │   │   ├── Tag.java
│   │   │   │   │   └── User.java
│   │   │   │   ├── repository/                 # 数据访问层
│   │   │   │   │   ├── ArticleRepository.java
│   │   │   │   │   ├── CategoryRepository.java
│   │   │   │   │   ├── TagRepository.java
│   │   │   │   │   └── UserRepository.java
│   │   │   │   └── service/                    # 业务逻辑层
│   │   │   │       ├── ArticleService.java
│   │   │   │       ├── CategoryService.java
│   │   │   │       ├── TagService.java
│   │   │   │       └── UserService.java
│   │   │   └── resources/
│   │   │       └── application.yml              # 配置文件
│   │   └── test/
│   └── pom.xml                                  # Maven配置
├── frontend/                # 前端项目
│   ├── public/
│   │   └── index.html
│   ├── src/
│   │   ├── components/      # 组件
│   │   │   ├── Header.js
│   │   │   └── Header.css
│   │   ├── pages/           # 页面
│   │   │   ├── Home.js
│   │   │   ├── Home.css
│   │   │   ├── ArticleList.js
│   │   │   ├── ArticleList.css
│   │   │   ├── ArticleDetail.js
│   │   │   ├── ArticleDetail.css
│   │   │   ├── Login.js
│   │   │   └── Login.css
│   │   ├── services/        # API服务
│   │   │   └── api.js
│   │   ├── App.js
│   │   ├── App.css
│   │   ├── index.js
│   │   └── index.css
│   └── package.json
├── database/                # 数据库脚本
│   └── init.sql
└── README.md
```

## 快速开始

### 环境要求
- JDK 17+
- Node.js 16+
- MySQL 8.0+
- Maven 3.6+

### 1. 数据库配置

创建数据库并执行初始化脚本：

```bash
mysql -u root -p < database/init.sql
```

修改后端配置文件 `backend/src/main/resources/application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/blog_cms
    username: root
    password: your_password
```

### 2. 启动后端

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080` 启动

### 3. 启动前端

```bash
cd frontend
npm install
npm start
```

前端服务将在 `http://localhost:3000` 启动

## API 文档

### 用户相关

- `POST /api/users/login` - 用户登录
- `POST /api/users/register` - 用户注册
- `GET /api/users/{id}` - 获取用户信息

### 文章相关

- `GET /api/articles` - 获取所有文章（分页）
- `GET /api/articles/published` - 获取已发布文章（分页）
- `GET /api/articles/latest` - 获取最新文章
- `GET /api/articles/{id}` - 获取文章详情
- `GET /api/articles/category/{categoryId}` - 按分类获取文章
- `GET /api/articles/tag/{tagId}` - 按标签获取文章
- `GET /api/articles/search?keyword=xxx` - 搜索文章
- `POST /api/articles` - 创建文章
- `PUT /api/articles/{id}` - 更新文章
- `DELETE /api/articles/{id}` - 删除文章

### 分类相关

- `GET /api/categories` - 获取所有分类
- `GET /api/categories/{id}` - 获取分类详情
- `POST /api/categories` - 创建分类
- `PUT /api/categories/{id}` - 更新分类
- `DELETE /api/categories/{id}` - 删除分类

### 标签相关

- `GET /api/tags` - 获取所有标签
- `GET /api/tags/{id}` - 获取标签详情
- `POST /api/tags` - 创建标签
- `PUT /api/tags/{id}` - 更新标签
- `DELETE /api/tags/{id}` - 删除标签

## 默认账号

系统初始化时会创建一个管理员账号：

- 用户名：`admin`
- 密码：`admin`
- 邮箱：`admin@example.com`

## 功能特性

### 文章管理
- 支持富文本内容编辑
- 文章封面图片上传
- 文章摘要自动生成
- 文章状态管理（草稿/已发布）
- 文章阅读量统计

### 分类与标签
- 文章可归属一个分类
- 文章可关联多个标签
- 支持按分类和标签筛选文章
- 分类和标签独立管理

### 用户系统
- 用户注册和登录
- 用户信息管理
- 个人资料编辑

### 浏览体验
- 响应式设计，适配各种设备
- 文章列表分页显示
- 文章详情页优化阅读体验
- 搜索功能快速定位文章

## 开发说明

### 后端开发

后端采用标准的 Spring Boot 三层架构：
- Controller 层：处理 HTTP 请求
- Service 层：业务逻辑处理
- Repository 层：数据访问

### 前端开发

前端采用 React 函数组件和 Hooks：
- 使用 React Router 进行路由管理
- 使用 Axios 进行 HTTP 请求
- 组件化开发，代码复用性高

## 部署说明

### 后端部署

1. 打包项目：`mvn clean package`
2. 运行 jar 包：`java -jar backend/target/blog-cms-1.0.0.jar`

### 前端部署

1. 构建项目：`npm run build`
2. 将 `build` 目录部署到 Web 服务器

## 许可证

MIT License

## 联系方式

如有问题或建议，欢迎提交 Issue。