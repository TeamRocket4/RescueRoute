FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y --no-install-recommends \
    wget unzip libgl1-mesa-dev

ENV ANDROID_SDK_ROOT=/sdk
RUN mkdir /sdk && wget -q https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip \
    && unzip commandlinetools-linux-8512546_latest.zip -d /sdk \
    && rm commandlinetools-linux-8512546_latest.zip

ENV PATH="$ANDROID_SDK_ROOT/cmdline-tools/bin:$ANDROID_SDK_ROOT/platform-tools:$PATH"

RUN yes | sdkmanager --licenses && sdkmanager \
    "platform-tools" \
    "build-tools;33.0.0" \
    "platforms;android-33"

WORKDIR /app
COPY . /app

RUN chmod +x ./gradlew

CMD ["./gradlew", "assembleDebug"]
