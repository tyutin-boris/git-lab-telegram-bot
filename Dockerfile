FROM amazoncorretto:17.0.9-alpine

ENV BOT_NAME=TestNNLigaGitLabBot
ENV BOT_TOKEN=6525258392:AAHjB2fEs8Fdi7JtSDom4acCHV-VCbbowF8

WORKDIR ./app
COPY ./target/git-lab-telegram-bot-1.1.4-SNAPSHOT.jar .

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "git-lab-telegram-bot-1.1.4-SNAPSHOT.jar"]
