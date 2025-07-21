# ======== 1단계: ttyd 빌드 스테이지 ========
FROM ubuntu:22.04 AS builder

RUN apt update && apt install -y \
    cmake g++ git \
    libjson-c-dev libwebsockets-dev libuv1-dev \
    curl \
    && git clone https://github.com/tsl0922/ttyd.git \
    && cd ttyd && mkdir build && cd build \
    && cmake .. \
    && make

# ======== 2단계: 실행용 이미지 ========
FROM ubuntu:22.04

# 로케일 설정 및 폰트 설치
RUN apt update && apt install -y \
    openjdk-17-jdk \
    libjson-c5 libwebsockets16 libuv1 \
    curl \
    locales fonts-nanum \
    && locale-gen ko_KR.UTF-8 \
    && update-locale LANG=ko_KR.UTF-8 \
    && rm -rf /var/lib/apt/lists/*

ENV LANG=ko_KR.UTF-8
ENV LANGUAGE=ko_KR:ko
ENV LC_ALL=ko_KR.UTF-8

# ttyd 복사
COPY --from=builder /ttyd/build/ttyd /usr/local/bin/ttyd

# 작업 디렉토리 및 JAR 복사
WORKDIR /app
COPY target/MINICLONE-1.0-SNAPSHOT.jar app.jar

# 포트 오픈
EXPOSE 7681

# 실행 커맨드 (JVM 인코딩 명시)
CMD ["ttyd", "--writable", "java", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
