# 📄 **Web Architecture**

## **Web의 동작방식**

&nbsp;&nbsp;Web은 클라이언트가 요청(Request)을 보내면 서버가 응답(Response)하는 방식으로 동작한다.
<br/><br/>

### **요청(Request)**

1. 클라이언트가 브라우저에 입력한 URL은 DNS(Domain Name System) 서버를 통해 도메인이 IP 주소로 변환.
2. 브라우저는 IP 주소로 서버에 요청(Request), HTTP(HyperText Transfer Protocol)를 통해 데이터가 전송.
3. HTTP 요청 메시지는 요청 방식, 송신자/수신자에 대한 데이터 등을 담아 TCP 프로토콜을 사용해 서버(수신자)로 전송.
   <br/><br/>

### **응답(Response)**

1. 서버는 HTTP 메시지를 받고 요청 URL에 대한 데이터를 찾음.
2. 서버가 브라우저에게 응답할 때에도 HTTP 메시지가 전송.
3. HTTP 응답 메시지에는 응답 상태 코드, 데이터 유형, 페이지를 화면에 렌더링하는데 필요한 코드 등을 포함하여 TCP 프로토콜을 통해 브라우저로 전송.
4. 서버에게 받은 데이터를 브라우저가 받아 파싱 및 렌더링을 거쳐 최종적으로 화면에 표시.
   <br/><br/>

<p align="center">
    <img src="images/webArchitecture.jpg" alt="webArchitecture">
</p></br>

### **관련 용어**

- **URL(Uniform Resource Locator)** : 웹 상의 자원을 참조하기 위한 웹 주소
- **웹 페이지(Web page)** : 웹 브라우저를 통해서 보여지는 화면
- **웹 서버(Web Server)** : 클라이언트 요청에 맞는 응답(웹 페이지)를 제공
- **웹 어플리케이션(Web Application)** : 웹 서버를 기반으로 실행되는 응용 소프트웨어
- **웹 어플리케이션 서버(Web Application Server, WAS)** : 요청에 따라 알맞은 프로그램을 실행하여 응답을 제공하는 서버
  <br/><br/>

## **Web Server & Web Application Server(WAS)**

## **SSR & CSR**

### **SSR**

&nbsp;&nbsp;과거에 많은 웹 브라우저는 사용자가 서버에 웹 페이지(HTML, CSS, JS)에 대한 정보를 요청하는 방식을 채택했다. 이러한 방식을 SSR(Server-Side Rendering)이라 부른다. SSR 방식은 최근 많은 동영상, 이미지를 담고 있는 무거운 웹 페이지를 불러오는 데 많은 시간을 소요하게 되며, 매 요청마다 서버에서 스크립트 파일이 로드될 때까지 기다려야 하기 때문에 사용자 경험이 좋지 않다.
<br/><br/>

&nbsp;&nbsp;반면 CSR은 브라우저가 서버에 HTML과 JS파일을 요청한 뒤 로드되면 그 뒤에는 사용자의 요청에 따라 JS를 이용해 동적으로 렌더링하는 방식이다. 웹 페이지에 대한 기본적인 틀은 처음 접속 시에 서버로부터 받아 사용자 브라우저에 저장하고, 이후에는 데이터만 받아 처리하는 방식으로 처음 로드만 기다리면 이후에는 빠른 응답을 받을 수 있어 사용자 경험이 좋다.
<br/><br/>

