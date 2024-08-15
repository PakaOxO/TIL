
### OSI 7 layer

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;네트워크 통신의 과정을 단계별로 구분하고, 구분을 통해 문제 발생시 수정이 필요한 단계를 명확히 파악하기 용이합니다.
  <br>
  &nbsp;&nbsp;7계층은 Application layer로 통신의 최종 목적지입니다. 응용 프로그램의 서비스를 이행하며, HTTP, FTP, DNS 등이 포함됩니다. 데이터 단위는 Message입니다.
  <br>
  &nbsp;&nbsp;6계층은 Presentation layer로 데이터의 압축 및 변환을 담당하고, 데이터의 포맷을 정의합니다. JPEG, MPEG 등이 포함됩니다.
  <br>
  &nbsp;&nbsp;5계층은 Session layer로 데이터 통신을 위한 논리적 연결을 담당하며, 세션을 생성해 통신합니다. API, Socket 등이 포함됩니다.
  <br>
  &nbsp;&nbsp;4계층은 Transport layer로 사용자간 통신의 endpoint입니다. 데이터 단위는 Segment이고, TCP와 UDP 등이 포함됩니다.
  <br>
  &nbsp;&nbsp;3계층은 Network layer로 주소인 IP를 기반으로 라우터를 통해 경로를 생성해 네트워크 통신을 담당합니다. 데이터의 단위는 Packet이며 관련 기기로는 Router가 있습니다.
  <br>
  &nbsp;&nbsp;2계층은 Datalink layer로 오류 검출 및 흐름제어로 데이터의 물리적 전송에 대한 신뢰성을 보장합니다. 데이터 단위는 Frame이며 관련 기기로는 Ethernet이 있습니다.
  <br>
  &nbsp;&nbsp;1계층은 Pysical layer로 데이터를 전기 신호로 변환합니다. 데이터 단위는 bit입니다.
</details>

<br>

---
### HTTP

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;HTTP는 HyperText Transfer Protocol의 약어로 비연결형 통신 방식입니다. 데이터로 메시지를 사용하며 메시지는 요청(Request)와 응답(Response) 2가지 타입으로 구분됩니다. HTTP는 신뢰성있는 통신을 보장하기 위해 TCP를 사용합니다.
  <br>
  &nbsp;&nbsp;HTTP는 Stateless한데, 이는 두 사용자간 Req-Res 통신이 이루어진 후, TCP 연결이 끊어진 뒤 이후에는 상대방의 상태를 알 수 없다는 특징을 나타냅니다.
</details>

<br>

### Cookie

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;HTTP는 Stateless하기 때문에 서버는 사용자의 정보를 알 수 없습니다. Cookie는 서버가 사용자를 식별하고, 필요한 최소한의 정보를 담기 위한 데이터로 서버는 필요하다면 Cookie가 없는 사용자에게 사용자의 정보를 담은 Cookie를 응답으로 반환하고, 사용자는 이후 Cookie를 포함한 요청을 전송합니다. 서버는 Cookie의 정보를 확인해 사용자에게 적절한 서비스를 제공할 수 있습니다.
</details>

<br>

### Cookie vs Session

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;Cookie와 Session은 모두 HTTP의 Connectionless, Stateless 특성으로 인해 서비스를 이용 중인 사용자를 식별하지 못하는 문제를 해결하기 위해 활용되지만 다음과 같은 차이점이 있습니다.
  <br>
  &nbsp;&nbsp;먼저 Cookie는 사용자 컴퓨터의 드라이브, Session은 서버에 저장됩니다. Cookie는 사용자 컴퓨터에 저장되는 만큼 Session에 비해 보안에 취약할 수 있습니다.
  <br>
  &nbsp;&nbsp;Cookie는 서버에서 생성되었을 때 만료기간을 지정받으며, 만료될 경우 사용이 불가능합니다. Session은 브라우저의 종료 시에 소멸하지만, 별도로 만료기간을 가질 수 있습니다.
  <br>
  &nbsp;&nbsp;세션은 쿠키에 비해 서버의 자원을 많이 활용하는 만큼 속도 측면에서 불리할 수 있습니다.
</details>

<br>

### DNS

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;DNS는 Domain Name System의 약어로 IP를 사람이 이해하기 쉬운 이름으로 변경해주는 시스템입니다. 사용자의 입장에서는 서비스의 IP가 변경되었더라도, 동일한 도메인을 사용한다면 이를 인지할 수 없습니다.
</details>

<br>

### HTTP Method

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;'GET'은 필요한 데이터의 조회
  <br>
  &nbsp;&nbsp;'POST'는 데이터의 추가
  <br>
  &nbsp;&nbsp;'PUT'은 이미 존재하는 자원이 있다면 해당 자원을 전체 갱신, 없다면 생성
  <br>
  &nbsp;&nbsp;'PATCH'는 존재하는 자원에 대해 일부분만 수정
  <br>
  &nbsp;&nbsp;'DELETE'는 요청 자원을 삭제
</details>

<br>

### REST API

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;REST는 HTTP URI를 통해 자원을 명시하고 Method로 자원을 처리하도록 설계된 아키텍처입니다. 이러한 REST 아키텍처를 기반으로 만들어진 API가 REST API입니다. REST의 구성요소로는 자원(Resource), 행위(Verb), 표현(Representations)이 있습니다. API Method는 다음과 같습니다.
  <br>
  &nbsp;&nbsp;'GET'은 필요한 데이터의 요청을 위해 활용됩니다.
  <br>
  &nbsp;&nbsp;'POST'는 데이터의 추가 및 수정, 삭제를 위해 활용됩니다.
  <br>
  &nbsp;&nbsp;'PUT'은 이미 존재하는 자원을 수정하기 위해 활용됩니다.
  <br>
  &nbsp;&nbsp;'DELETE'는 존재하는 자원을 삭제하기 위해 활용됩니다.
</details>

<br>

### RESTful

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;RESTful하다는 것은 REST 원리를 따르는 시스템을 의미합니다. Rest API 개발 원칙에는 다음과 같은 규칙이 있습니다.
  <br>
  &nbsp;&nbsp;첫째로, URI를 통해 자원을 명확하게 식별할 수 있어야 합니다. URI는 자원의 주소 및 종류, 내용을 유추할 수 있는 내용을 담고 있어야 합니다.
  <br>
  &nbsp;&nbsp;둘째로, 행위는 명시적으로 활용되어야 합니다. 자원에 대한 행위는 적절한 REST API Method를 통해 처리되어야 함을 의미합니다.
  <br>
  &nbsp;셋째로, 자기 서술적(Self-descriptive)이어야 합니다. 자원의 메타 데이터만을 통해 어떤 종류의 데이터인지, 데이터 처리를 위해 어떤 어플리케이션을 활용해야 하는지 유추할 수 있어야합니다.
</details>

<br>

### GET vs POST

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;GET은 주로 자원의 조회를 위해, POST는 추가, 수정, 삭제를 위해 활용됩니다. 그렇기 때문에 메소드 실행 전후로 결과가 바뀌지 않는 GET은 멱등성을 가집니다.
  <br>
  &nbsp;&nbsp;GET은 캐싱이 가능한 반면 POST는 캐싱이 불가능합니다. 또, GET은 URI에 타겟 자원을 명시하기 때문에 메시지의 Header에 담기는 반면, POST는 메시지의 body에 담기므로 상대적으로 자원이 직접 노출되지 않은 POST가 안전합니다.
</details>

<br>

### 멱등성

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;멱등성이란 동일한 메소드가 여러 번 실행되더라도 항상 같은 응답을 받을 경우, 멱등성을 가진다고 말합니다. REST API에서 GET, PUT, DELETE은 멱등성인 반면 POST는 매 요청마다 새로운 데이터가 추가되기 때문에 멱등성을 가지지 않습니다.
</details>

<br>

### HTTPS

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;HTTPS는 HTTP가 가진 보안적 문제를 해결하기 위해 등장한 프로토콜입니다. HTTP는 메시지 내용이 Text로 이루어져 있기 때문에 메시지가 노출되거나 탈취되었을 때 정보가 유출될 수 있는 보안 문제가 있습니다.
  <br>
  &nbsp;&nbsp;
</details>

---
### 