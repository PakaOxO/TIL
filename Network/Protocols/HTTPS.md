
&nbsp;&nbsp;`HTTP(Hypertext Transfer Protocol)`은 웹 상에서 클라이언트와 서버 간 통신을 위해 사용되는 가장 기본적인 프로토콜입니다. 하지만 HTTP는 여러가지 보안 취약점을 가지고 있는데, `HTTPS`는 이러한 문제점을 해결하기 위해 등장했습니다. 이번 포스트에서는 HTTPS의 등장배경과 특징에 대해 살펴보도록 하겠습니다.

<br>

## HTTPS

### HTTP 보안 취약점

&nbsp;&nbsp;`HTTP`에 담긴 데이터는 일련의 텍스트입니다. 그렇기 때문에 사용자가 웹 사이트에 계좌번호, 카드 정보, 집 주소 등 민감한 정보를 보낸다면 이는 제 3자에 의해 탈취되거나 읽혀질 수 있습니다. 아래는 클라이언트가 서버에 보내는 `GET` 요청의 한 예시로 서버에게 `hello.txt` 파일을 요청하고 있음을 알 수 있습니다.

```cmd
GET /hello.txt HTTP/1.1
User-Agent: curl/7.63.0 libcurl/7.63.0 OpenSSL/1.1.l zlib/1.2.11
Host: www.example.com
Accept-Language: en
```

&nbsp;&nbsp;`HTTPS`는 `HTTP`에 `CA(Certification Authority)`라는 별도의 인증 기관을 두어 클라이언트로 하여금 


<br>

**References**
- [Cloudflare, Why is HTTP not secure](https://www.cloudflare.com/ko-kr/learning/ssl/why-is-http-not-secure/)
- [Cloudflare, What is SSL?](https://www.cloudflare.com/ko-kr/learning/ssl/what-is-ssl/)
- 