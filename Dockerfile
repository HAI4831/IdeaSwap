# Bước 1: Dùng OpenJDK đầy đủ để build ứng dụng
FROM openjdk:17-jdk-slim AS build

# Cấu hình múi giờ Việt Nam
RUN ln -snf /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime && echo Asia/Ho_Chi_Minh > /etc/timezone

LABEL author="NguyenVanHai"

WORKDIR /app

# Sao chép và cache dependencies để tăng tốc độ build
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle
RUN chmod +x ./gradlew

# ✅ Xóa build cũ (nếu có) trước khi build lại
RUN rm -rf build

# ✅ Sửa lỗi: Dùng `build --refresh-dependencies`
RUN ./gradlew clean build --no-daemon --refresh-dependencies || true

# Sao chép toàn bộ mã nguồn vào container
COPY . .
RUN ./gradlew bootJar --no-daemon

# Bước 2: Dùng JRE nhẹ để chạy ứng dụng
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Sao chép file JAR từ image build vào image chạy
COPY --from=build /app/build/libs/*.jar app.jar

# ✅ Định nghĩa biến môi trường đúng cách
ENV SPRING_PROFILES_ACTIVE=production \
    CLOUDINARY_URL="cloudinary://345593341212817:ip50kMhMixVv-F6qOjwyKoN5rLk@divcnthxr" \
    EMAIL="nvhai227@gmail.com" \
    EMAIL_PASSWORD="cwahltnnqmzmbulm" \
    MONGO_URI="mongodb+srv://nvhai227:abCD%401234@cluster0.kfltces.mongodb.net/?retryWrites=true&w=majority&appName=crud"

# Mở cổng 8080
EXPOSE 8080

# ✅ Chạy ứng dụng với profile production
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app/app.jar"]

## Bước 1: Dùng OpenJDK đầy đủ để build ứng dụng
#FROM openjdk:17-jdk-slim AS build
#
## Cấu hình múi giờ Việt Nam
#RUN ln -snf /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime && echo Asia/Ho_Chi_Minh > /etc/timezone
#
#LABEL author="NguyenVanHai"
#
#WORKDIR /app
#
## Sao chép và cache dependencies để tăng tốc độ build
#COPY build.gradle settings.gradle gradlew /app/
#COPY gradle /app/gradle
#RUN chmod +x ./gradlew
#
## ✅ Sửa lỗi: Dùng `build --refresh-dependencies`
#RUN ./gradlew build --no-daemon --refresh-dependencies || true
#
## Sao chép toàn bộ mã nguồn vào container
#COPY . .
#RUN ./gradlew bootJar --no-daemon
#
## Bước 2: Dùng JRE nhẹ để chạy ứng dụng
#FROM eclipse-temurin:17-jre-alpine
#
#WORKDIR /app
#
## Sao chép file JAR từ image build vào image chạy
#COPY --from=build /app/build/libs/*.jar app.jar
#
## ✅ Định nghĩa biến môi trường đúng cách
#ENV SPRING_PROFILES_ACTIVE=production \
#    CLOUDINARY_URL="cloudinary://345593341212817:ip50kMhMixVv-F6qOjwyKoN5rLk@divcnthxr" \
#    EMAIL="ideaswapsp@gmail.com" \
#    EMAIL_PASSWORD="vejtkoejstatxska" \
#    MONGO_URI="mongodb+srv://nvhai227:abCD%401234@cluster0.kfltces.mongodb.net/?retryWrites=true&w=majority&appName=crud"
#
## Mở cổng 8080
#EXPOSE 8080
#
## ✅ Chạy ứng dụng với profile production
#ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app/app.jar"]
