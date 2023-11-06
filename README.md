# po-server
 Resource for KNU Security class

## Quick start
```
(Docker 설치, 실행한 상태)

(Intel, AMD)
$ docker pull asia-northeast3-docker.pkg.dev/sandboxcolli/for-knu/po-server-amd64
$ docker tag asia-northeast3-docker.pkg.dev/sandboxcolli/for-knu/po-server-amd64 po-server

(Apple chip)
$ docker pull asia-northeast3-docker.pkg.dev/sandboxcolli/for-knu/po-server-arm64
$ docker tag asia-northeast3-docker.pkg.dev/sandboxcolli/for-knu/po-server-arm64 po-server

(Run; default port: 8080)
$ docker run po-server
```

### 플래그 사용하기
```
응답 확인을 위한 로그
$ docker run -e LOG=true po-server

포트 번호 바꾸기 (50051로 연결하기)
$ docker run -p 50051:8080 po-server

백그라운드에서 실행
$ doker run -d -p 8080:8080 po-server
```

## HTTP API
브라우저로 확인할 수 있습니다.
### Ping
```
URL: http://localhost:<PORT>/ping
Response body: pong
```
### Query
```
URL: http://localhost:<PORT>/query?str1=<STR1>&str2=<STR2>
Response body: true or false
```

True 예시
```
http://localhost:<PORT>/query?str1=0x1234567890abcd93&str2=0x1234567890abcdb9
Response body: true
```
