## /po-server-java
### 자바 프로그램
선행: [openjdk11](https://jdk.java.net/archive/) 설치

실행하기
```
$ git clone "github.com/boxcolli/po-server"
$ cd po-server-java
$ javac -d bin -cp "lib/*" src/*.java
$ java -cp "bin:lib/*" App
```

환경변수
```
응답 확인을 위한 로그
$ export LOG=true (Linux/MacOS)
$ SET LOG=true (Windows)

포트 번호
$ export PORT=8080 (Linux/MacOS)
$ SET PORT=8080 (Windows)
```

### 도커 이미지
빌드하기
```
$ docker build -t po-server -f Dockerfile .
```
실행하기
```
빠른 실행
$ docker run -p 8080:8080 po-server

응답 확인을 위한 로그
$ docker run -e LOG=true -p 8080:8080 po-server

포트 번호 (50051로 연결하기)
$ docker run -p 50051:8080 po-server

백그라운드에서 실행
$ doker run -d -p 8080:8080 po-server
```