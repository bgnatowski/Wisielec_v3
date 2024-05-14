# Stage 1: Build the Angular app
FROM node:16-alpine AS build
WORKDIR /app

COPY . .
RUN npm install
RUN npm run build
# Serve Application using Nginx Server
FROM nginx:alpine
COPY --from=build /app/dist/frontend/ /usr/share/nginx/html
EXPOSE 80


## Stage 1
# Używanie obrazu node specyficznego dla architektury ARM
#FROM --platform=linux/arm64/v8 node:latest as node
#WORKDIR /app
#COPY . .
#RUN npm install
#RUN npm run build
#
## Stage 2
## Używanie obrazu nginx specyficznego dla architektury ARM
#FROM --platform=linux/arm64/v8 nginx:alpine
#COPY --from=node /app/dist/frontend /usr/share/nginx/html
