
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

### DNS

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;DNS는 Domain Name System의 약어로 IP를 사람이 이해하기 쉬운 이름으로 변경해주는 시스템입니다. 사용자의 입장에서는 서비스의 IP가 변경되었더라도, 동일한 도메인을 사용한다면 이를 인지할 수 없습니다.
</details>

<br>

---
### 