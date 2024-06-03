
&nbsp;&nbsp;`HTTP(Hypertext Transfer Protocol)`은 웹 상에서 클라이언트와 서버 간 통신을 위해 사용되는 가장 기본적인 프로토콜입니다. 하지만 HTTP는 여러가지 보안 취약점을 가지고 있는데, `HTTPS`는 이러한 문제점을 해결하기 위해 등장했습니다. 이번 포스트에서는 HTTPS의 등장배경과 특징에 대해 살펴보도록 하겠습니다.

<br>

## HTTPS

### HTTP의 한계

&nbsp;&nbsp;`HTTP`에 담긴 데이터는 일련의 텍스트입니다. 그렇기 때문에 사용자가 웹 사이트에 계좌번호, 카드 정보, 집 주소 등 민감한 정보를 보낸다면 이는 제 3자에 의해 탈취되거나 읽혀질 수 있습니다. 아래는 클라이언트가 서버에 보내는 `GET` 요청의 한 예시로 서버에게 `hello.txt` 파일을 요청하고 있음을 알 수 있습니다. 이렇듯 HTTP를 통한 메시지 전송은 메시지에 대한 보안적인 측면에서 좋지 않습니다.

```cmd
GET /hello.txt HTTP/1.1
User-Agent: curl/7.63.0 libcurl/7.63.0 OpenSSL/1.1.l zlib/1.2.11
Host: www.example.com
Accept-Language: en
```

<br>

&nbsp;&nbsp;또한 `HTTP`를 사용한다면 사용자는 해당 사이트가 안전한지 알 수 없습니다. 현대의 브라우저는 모든 HTTP 웹 사이트에 대해 `안전하지 않음` 메시지를 보여줍니다. 별도의 인증기관을 가지는 `HTTPS`와 달리 HTTP 웹 사이트는 별도의 인증 과정이 없기 때문에 사용자의 관점에서 신뢰할 수 있는 사이트인지 알 수가 없다는 문제가 있습니다.

<br>

### HTTPS?

&nbsp;&nbsp;그렇다면 `HTTPS`는 어떻게 `HTTP`가 가진 문제점을 해결할 수 있었을 까요? 우선 HTTPS의 'S'는 보안을 뜻하는 'Secure'를 뜻하는 약자로 기존의 HTTP보다 보안적인 요소가 추가된 프로토콜이라 할 수 있습니다. 기존에 HTTP에서는 메시지에서 문자로 된 내용을 제 3자가 확인할 수 있었다면 HTTPS에서의 메시지는 암호화를 거친 뒤 전송되기 때문에 중간에 탈취가 발생하더라도 메시지에 담긴 내용을 확인하기 어렵습니다.

```cmd
t8Fw6T8UV81pQfyhDkhebbz7+oiwldr1j2gHBB3L3RFTRsQCpaSnSBZ78Vme+DpDVJPvZdZUZHpzbbcqmSW1+3xXGsERHg9YDmpYk0VVDiRvw1H5miNieJeJ/FNUjgH0BmVRWII6+T4MnDwmCMZUI/orxP3HGwYCSIvyzS3MpmmSe4iaWKCOHQ==
```

<br>
### 메시지 암호화

&nbsp;&nbsp;


<br>

**References**
- [Cloudflare, Why use HTTPS](https://www.cloudflare.com/ko-kr/learning/ssl/why-use-https/)
- [Cloudflare, Why is HTTP not secure](https://www.cloudflare.com/ko-kr/learning/ssl/why-is-http-not-secure/)
- [Cloudflare, What is SSL?](https://www.cloudflare.com/ko-kr/learning/ssl/what-is-ssl/)