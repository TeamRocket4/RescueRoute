# Use Node.js base image
FROM node:18-alpine AS builder

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application
COPY . .

# Build the Next.js application
RUN npm run build

# Use a lightweight web server to serve the built app
FROM node:18-alpine AS runner

# Set working directory
WORKDIR /app

# Copy the built application from the builder stage
COPY --from=builder /app/.next /app/.next
COPY --from=builder /app/public /app/public
COPY --from=builder /app/package.json /app/package.json

# Install only production dependencies
RUN npm install --only=production

# Expose the port
EXPOSE 3000

# Start the application
CMD ["npm", "run", "start"]
