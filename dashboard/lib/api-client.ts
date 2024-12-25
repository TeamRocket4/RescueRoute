import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://host.docker.internal:8080/',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiClient;

