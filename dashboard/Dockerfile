# Use Node.js base image
FROM node:18-alpine

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Use --legacy-peer-deps to resolve dependency conflicts
RUN npm install --legacy-peer-deps

# Copy the rest of the application
COPY . .

# Expose the development server port
EXPOSE 3000

# Start the application in development mode
CMD ["npm", "run", "dev"]