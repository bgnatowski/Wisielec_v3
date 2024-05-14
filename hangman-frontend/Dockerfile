# Użyj obrazu Node.js
FROM node:16-alpine AS build

# Ustaw katalog roboczy
WORKDIR /app

# Skopiuj package.json i package-lock.json (jeśli istnieje)
COPY package*.json ./

# Zainstaluj zależności
RUN npm install

# Skopiuj pozostałe pliki źródłowe aplikacji
COPY . .

# Expose port, na którym aplikacja będzie dostępna
EXPOSE 4200

# Uruchomienie aplikacji Angular
CMD ["npm", "start"]


## Stage 1: Build the Angular app
##FROM node:16-alpine AS build
##WORKDIR /app
##
##COPY . .
##RUN npm install
##RUN npm run build
### Serve Application using Nginx Server
##FROM nginx:alpine
##COPY --from=build /app/dist/frontend/ /usr/share/nginx/html
##EXPOSE 80
#
#
## Etap 1: Budowanie aplikacji Angular
#FROM node:16-alpine as build
#WORKDIR /app
#COPY package.json ./
#COPY package-lock.json ./
#RUN npm install
#COPY . .
#RUN npm run build --prod
#
## Etap 2: Serwowanie aplikacji za pomocą NGINX
#FROM nginx:alpine
#COPY --from=build /app/dist/frontend /usr/share/nginx/html
