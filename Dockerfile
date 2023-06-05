FROM openjdk:14-oraclelinux7
COPY . /backend
WORKDIR /backend
run yum -y install dos2unix
RUN dos2unix ./gradlew
CMD ./gradlew run
