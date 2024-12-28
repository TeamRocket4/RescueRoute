import axios from 'axios';

const apiClient = axios.create({
  baseURL: `http://${process.env.NEXT_PUBLIC_API_URL}:8080/`,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiClient;

