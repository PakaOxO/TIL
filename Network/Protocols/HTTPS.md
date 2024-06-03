
&nbsp;&nbsp;`HTTP(Hypertext Transfer Protocol)`은 웹 상에서 클라이언트와 서버 간 통신을 위해 사용되는 가장 기본적인 프로토콜입니다. 하지만 HTTP는 여러가지 보안 취약점을 가지고 있는데, `HTTPS`는 이러한 문제점을 해결하기 위해 등장했습니다. 이번 포스트에서는 HTTPS의 등장배경과 특징에 대해 살펴보도록 하겠습니다.

<br>

## HTTPS

### HTTP의 한계

&nbsp;&nbsp;`HTTP`에 담긴 데이터는 일련의 텍스트로 이루어진 평문입니다. 그렇기 때문에 사용자가 웹 사이트에 계좌번호, 카드 정보, 집 주소 등 민감한 정보를 보낸다면 이는 제 3자에 의해 탈취되거나 읽혀질 수 있습니다. 이렇듯 HTTP를 통한 메시지 전송은 메시지에 대한 보안적인 측면에서 좋지 않습니다.

&nbsp;&nbsp;또한 `HTTP`를 사용한다면 사용자는 해당 사이트가 안전한지 알 수 없습니다. 현대의 브라우저는 모든 HTTP 웹 사이트에 대해 `안전하지 않음` 메시지를 보여줍니다. 별도의 인증기관을 가지는 `HTTPS`와 달리 HTTP 웹 사이트는 별도의 인증 과정이 없기 때문에 사용자의 관점에서 신뢰할 수 있는 사이트인지 알 수가 없다는 문제가 있습니다.

<br>

### SSL/TLS

&nbsp;&nbsp;그렇다면 `HTTPS`는 어떻게 `HTTP`가 가진 문제점을 해결할 수 있었을까요? 우선 HTTPS의 'S'는 보안을 뜻하는 'Secure'를 뜻하는 약자로 기존의 HTTP에 `SSL(Secure Socket Layer)`과 `TLS(Transport Layer Security)`라는 전송 기술이 합쳐져 안전한 계층을 웹 통신과정에 추가해 메시지를 안전하게 전달할 수 있습니다.

&nbsp;&nbsp;`SSL/TLS 인증서`는 웹 서버에 설치되어 있다가 클라이언트의 요청이 들어오면 서버는 응답에 인증서를 담아 전달하고, 클라이언트는 다시 웹 브라우저 내부에 관리되고 있는 인증서 정보를 통해 서버에게 받은 인증서가 신뢰할 만한 것인지 판단합니다.

<br>

>[!tip] SSL과 TLS
>
>&nbsp;&nbsp;`TLS`는 `SSL`의 개선된 버전입니다. `SSL`은 Netscape에 의해 처음 만들어져 관리되다가 이후 버전에서는 Netscape가 빠지게 되면서 이전 `SSL`과의 구분을 위해 `TLS`라는 다른 이름을 갖게 되었습니다. 그렇기 때문에 마지막 버전의 `SSL`과 초기 버전의 `TLS`는 크게 다르지 않습니다.

<br>

## 메시지 암호화

&nbsp;&nbsp;`HTTPS`에서 메시지를 암호화하는 데에는 다양한 기술들이 활용됩니다. 이제부터 통신과정에서 어떻게 메시지가 암호화 되는지 차근차근 살펴보도록 하겠습니다.

<br>

### 대칭키/비대칭키 암호화 방식

&nbsp;&nbsp;`SSL/TLS 인증서`를 통해 암호화된 메시지를 주고받는 과정을 살펴보기 위해서는 가장 대표적인 암호화 방식인 `대칭키 암호화`와 `비대칭키 암호화`를 이해하고 있어야 합니다.

<br>

**1. 대칭키 암호화**

- 이름에서 알 수 있듯 동일한 키를 암호화와 복호화에 쓰는 방식입니다.
- 하나의 키를 사용하기 때문에 관리가 쉽지만 키를 분실하거나 누군가에 의해 키를 탈취 당한다면 암호화된 메시지를 제 3자가 복호화해 볼 수 있다는 문제점이 있습니다.

**2. 비대칭키 암호화**

- 또 다른 이름으로 `공개키 암호화` 방식으로 불립니다.
- `공개키`와 `개인키`, 2개의 키를 한 쌍으로 가지며 각각 암호화와 복호화에 사용합니다.
- 먼저 만들어진 `개인키`를 토대로 `공개키`를 생성하기 때문에 `키페어(key pair)`라고도 부릅니다.
- 대칭키 방식에 비해 안전하지만 계산과정이 복잡하고, 연산 과정에서 컴퓨터 자원을 많이 사용해 실제 IT 시스템에서는 공개키 방식과 비대칭키 방식을 적절하게 혼합해 사용합니다.

<br>

### SSL/TLS Handshake

&nbsp;&nbsp;이제 실제로 어떻게 클라이언트와 서버 간에 전송되는 메시지가 암호화 되는지 살펴보겠습니다. 메시지의 암호화는 앞서 살펴보았던 `대칭키 암호화`와 `공개키 암호화` 방식인 `비대칭키 암호화`를 모두 사용해 이루어지는데 상호 메시지를 암호화하고 복호화하는데 사용될 키를 생성하기 위해 `인증서`를 교환하는 `SSL/TLS Handshake` 단계를 거칩니다.`SSL/TLS Handshake`는 먼저 클라이언트와 서버가 통신을 하기 위해 필요한 `TCP Handshake`가 종료된 이후에 발생합니다.

<br>

**SSL/TLS Handshake**

1. `TCP Handshake`
2. `Client Hello` : 클라이언트가 웹 서버에 접속하며 아래의 정보를 담아 메시지를 보냅니다.
    - 브라우저가 사용하는 SSL 또는 TLS의 버전 정보
    - 브라우저가 지원하는 암호화 방식(Cipher suite)
    - 브라우저가 생성한 임의의 난수
    - 이전에 SSL Handshake가 완료되었다면 그때 생성된 `Session ID`
    - 그 외 기타 정보
3. `Server Hello` : 웹 서버는 `Client Hello`에 응답하면서 아래의 정보를 담아 메시지를 보냅니다.
    - 브라우저 암호화 방식 정보 중에 서버가 지원하고 선택한 암호화 방식(Cipher suite)
    - 서버의 공개키가 담겨 있으며 `CA`의 `` `SSL 인증서`


<br>

**References**
- [Cloudflare, Why use HTTPS](https://www.cloudflare.com/ko-kr/learning/ssl/why-use-https/)
- [Cloudflare, Why is HTTP not secure](https://www.cloudflare.com/ko-kr/learning/ssl/why-is-http-not-secure/)
- [Cloudflare, What is SSL?](https://www.cloudflare.com/ko-kr/learning/ssl/what-is-ssl/)
- [컴퓨터 네트워킹 하향식 접근](https://product.kyobobook.co.kr/detail/S000061694627)
- [요즘 IT, 안전한 웹을 위해 HTTPS 이해하기](https://yozm.wishket.com/magazine/detail/1852/)