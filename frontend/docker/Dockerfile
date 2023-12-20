FROM node:18.13.0 as build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install --save --legacy-peer-deps
RUN npm install -g @angular/cli
ARG ENV=production
COPY . .
RUN ng build --configuration=$ENV

FROM nginx:alpine
COPY --from=build /app/dist/frontend /usr/share/nginx/html