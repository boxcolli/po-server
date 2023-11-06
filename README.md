# po-server
 Resource for KNU Security class

## Quick start
```
$ git clone https://github.com/boxcolli/po-server.git
$ cd po-server-java
$ docker build -t po-server .
$ docker run po-server
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
