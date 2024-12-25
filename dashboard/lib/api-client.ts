import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://springboot:8080/',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiClient;

