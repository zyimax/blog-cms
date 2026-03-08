import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const articleApi = {
  getAll: (page = 0, size = 10) => api.get(`/articles?page=${page}&size=${size}`),
  getPublished: (page = 0, size = 10) => api.get(`/articles/published?page=${page}&size=${size}`),
  getLatest: () => api.get('/articles/latest'),
  getById: (id) => api.get(`/articles/${id}`),
  getByCategory: (categoryId, page = 0, size = 10) => 
    api.get(`/articles/category/${categoryId}?page=${page}&size=${size}`),
  getByTag: (tagId, page = 0, size = 10) => 
    api.get(`/articles/tag/${tagId}?page=${page}&size=${size}`),
  search: (keyword, page = 0, size = 10) => 
    api.get(`/articles/search?keyword=${keyword}&page=${page}&size=${size}`),
  create: (data) => api.post('/articles', data),
  update: (id, data) => api.put(`/articles/${id}`, data),
  delete: (id) => api.delete(`/articles/${id}`),
};

export const categoryApi = {
  getAll: () => api.get('/categories'),
  getById: (id) => api.get(`/categories/${id}`),
  create: (data) => api.post('/categories', data),
  update: (id, data) => api.put(`/categories/${id}`, data),
  delete: (id) => api.delete(`/categories/${id}`),
};

export const tagApi = {
  getAll: () => api.get('/tags'),
  getById: (id) => api.get(`/tags/${id}`),
  create: (data) => api.post('/tags', data),
  update: (id, data) => api.put(`/tags/${id}`, data),
  delete: (id) => api.delete(`/tags/${id}`),
};

export const userApi = {
  login: (data) => api.post('/users/login', data),
  register: (data) => api.post('/users/register', data),
  getById: (id) => api.get(`/users/${id}`),
};

export default api;