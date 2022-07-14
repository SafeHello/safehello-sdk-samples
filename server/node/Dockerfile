FROM node:14-alpine
ENV API_TOKEN=<<API_TOKEN>>
RUN apk add --no-cache tini
WORKDIR /app

COPY package*.json ./

RUN npm install
# If you are building your code for production
# RUN npm ci --only=production

# Bundle app source
COPY . .

EXPOSE 3000
ENTRYPOINT [ "/sbin/tini", "--", "node", "index.js" ]
