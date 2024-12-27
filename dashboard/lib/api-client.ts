import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://65.20.105.247:8080/',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiClient;

