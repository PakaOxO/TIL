
&nbsp;&nbsp;`HTTP(Hypertext Transfer Protocol)`은 웹 상에서 클라이언트와 서버 간 통신을 위해 사용되는 가장 기본적인 프로토콜입니다. 하지만 HTTP는 여러가지 보안 취약점을 가지고 있는데, `HTTPS`는 이러한 문제점을 해결하기 위해 등장했습니다. 이번 포스트에서는 HTTPS의 등장배경과 특징에 대해 살펴보도록 하겠습니다.

<br>

## HTTPS

### HTTP의 한계

&nbsp;&nbsp;`HTTP`에 담긴 데이터는 일련의 텍스트로 이루어진 평문입니다. 그렇기 때문에 사용자가 웹 사이트에 계좌번호, 카드 정보, 집 주소 등 민감한 정보를 보낸다면 이는 제 3자에 의해 탈취되거나 읽혀질 수 있습니다. 아래는 클라이언트가 서버에 보내는 `GET` 요청의 한 예시로 서버에게 `hello.txt` 파일을 요청하고 있음을 알 수 있습니다. 이렇듯 HTTP를 통한 메시지 전송은 메시지에 대한 보안적인 측면에서 좋지 않습니다.

```cmd
GET /hello.txt HTTP/1.1
User-Agent: curl/7.63.0 libcurl/7.63.0 OpenSSL/1.1.l zlib/1.2.11
Host: www.example.com
Accept-Language: en
```

<br>

&nbsp;&nbsp;또한 `HTTP`를 사용한다면 사용자는 해당 사이트가 안전한지 알 수 없습니다. 현대의 브라우저는 모든 HTTP 웹 사이트에 대해 `안전하지 않음` 메시지를 보여줍니다. 별도의 인증기관을 가지는 `HTTPS`와 달리 HTTP 웹 사이트는 별도의 인증 과정이 없기 때문에 사용자의 관점에서 신뢰할 수 있는 사이트인지 알 수가 없다는 문제가 있습니다.

<br>

### SSL/TLS

&nbsp;&nbsp;그렇다면 `HTTPS`는 어떻게 `HTTP`가 가진 문제점을 해결할 수 있었을까요? 우선 HTTPS의 'S'는 보안을 뜻하는 'Secure'를 뜻하는 약자로 기존의 HTTP에 `SSL(Secure Socket Layer)`과 `TLS(Transport Layer Security)`라는 전송 기술이 합쳐져 안전한 계층을 웹 통신과정에 추가해 메시지를 안전하게 전달할 수 있습니다.

&nbsp;&nbsp;`SSL/TLS 인증서`는 웹 서버에 설치되어 있다가 클라이언트의 요청이 들어오면 서버는 응답에 인증서를 담아 전달하고, 클라이언트는 다시 웹 브라우저 내부에 관리되고 있는 인증서 정보를 통해 서버에게 받은 인증서가 신뢰할 만한 것인지 판단합니다.

<br>

>[!tip] SSL과 TLS
>
>&nbsp;&nbsp;`TLS`는 `SSL`의 개선된 버전입니다. `SSL`은 netscape에 의해 

<br>

## 메시지 암호화

&nbsp;&nbsp;`HTTPS`에서 메시지를 암호화하는 데에는 다양한 기술들이 활용됩니다. 이제부터 통신과정에서 어떻게 메시지가 암호화 되는지 차근차근 살펴보도록 하겠습니다.


<br>



<br>

**References**
- [Cloudflare, Why use HTTPS](https://www.cloudflare.com/ko-kr/learning/ssl/why-use-https/)
- [Cloudflare, Why is HTTP not secure](https://www.cloudflare.com/ko-kr/learning/ssl/why-is-http-not-secure/)
- [Cloudflare, What is SSL?](https://www.cloudflare.com/ko-kr/learning/ssl/what-is-ssl/)
- [안전한 웹을 위해 HTTPS 이해하기](https://yozm.wishket.com/magazine/detail/1852/)