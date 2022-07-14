FROM node:14-alpine
ENV TOKEN=<<API_TOKEN>>
RUN apk add --no-cache tini
WORKDIR /app

COPY package*.json ./

RUN npm install
# If you are building your code for production
# RUN npm ci --only=production

# Bundle app source
COPY . .

EXPOSE 8080
ENTRYPOINT [ "/sbin/tini", "--", "node", "index.js" ]
