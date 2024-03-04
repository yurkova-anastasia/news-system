#FROM eclipse-temurin:17-jdk-alpine AS build
#WORKDIR /workspace/app
#COPY . /workspace/app
#RUN cd build/libs; java -jar -Djarmode=layertools config-server.jar  extract
#
#FROM eclipse-temurin:17.0.7_7-jre-alpine
#RUN addgroup -S app && adduser -S app -G app
#USER app
#ARG PATH_LAYER=/workspace/app
#WORKDIR /app
#COPY --from=build $PATH_LAYER/build/libs/application/ ./
#COPY --from=build $PATH_LAYER/build/libs/dependencies/ ./
#COPY --from=build $PATH_LAYER/build/libs/snapshot-dependencies/ ./
#COPY --from=build $PATH_LAYER/build/libs/spring-boot-loader/ ./
#ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]

FROM eclipse-temurin:17-jdk-alpine AS build
ARG NAME_MODULE
ARG NAME_SERVICE
ARG FOLDER
WORKDIR /workspace/app
COPY . /workspace/app
RUN --mount=type=cache,target=/root/.gradle ./gradlew ${NAME_MODULE}:clean ${NAME_MODULE}:bootJar
RUN cd ${FOLDER}/build/libs; java -jar -Djarmode=layertools ${NAME_SERVICE}.jar  extract

FROM eclipse-temurin:17.0.7_7-jre-alpine AS image_builder
RUN addgroup -S app && adduser -S app -G app
USER app
ARG FOLDER
ARG PATH_LAYER=/workspace/app/${FOLDER}
WORKDIR /app
COPY --from=build $PATH_LAYER/build/libs/application/ ./
COPY --from=build $PATH_LAYER/build/libs/dependencies/ ./
COPY --from=build $PATH_LAYER/build/libs/snapshot-dependencies/ ./
COPY --from=build $PATH_LAYER/build/libs/spring-boot-loader/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]
