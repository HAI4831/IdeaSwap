FROM eclipse-temurin:17-jre-alpine
LABEL author="NguyenVanHai"
COPY ./target/*.jar ./app.jar
ENV TZ=Asia/Ho_Chi_Minh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["java", "-jar", "./app.jar"]
