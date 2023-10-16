# 📄 **Servlet**

<p align="center">
    <img style="width: 60%" src="images/servlet_jsp_logo.png" alt="servlet_jsp_logo">
</p></br>

## **Servlet이란**

&nbsp;&nbsp;<u>자바를 사용하여 웹 페이지를 동적으로 생성하는 서버 측 프로그램</u> 또는 그 사양을 의미한다. 웹 서버의 성능을 향상하기 위해 사용되는 자바 클래스의 일종으로 웹 기반 응용 프로그램을 구축하기 위한 구성 요소 기반의 플랫폼에 독립적인 방법을 제공한다.
<br/><br/>

&nbsp;&nbsp;서블릿은 대화형 웹 응용 프로그램을 구축하는데 널리 사용되며 JSP와 비슷한 점이 있지만 JSP가 HTML문서 안에 Java 코드를 포함하는 반면 서블릿은 자바 코드안에 HTML을 포함한다.
<br/><br/>

## **Servlet API**

&nbsp;&nbsp;Servlet Interface는 모든 서블릿이 구현해야 하는 메서드를 정의한다. 일반적으로 HTTP 관련 서비스를 처리하기 위해서는 <u>**HttpServlet**</u> 클래스를 상속하여 작성한다.
<br/><br/>

## **Servlet 생명주기**

&nbsp;&nbsp;서블릿 인스턴스는 서블릿이 포함된 웹 컨테이너에 의해 제어된다. 웹 컨테이너는 Tomcat과 같은 WebServer가 담당한다. 만약 서블릿 인스턴스가 존재하지 않으면 아래와 같은 작업이 수행된다.
<br/>

1. 서블릿 클래스 로드
2. 서블릿 클래스 인스턴스 생성
3. 서블릿 인스턴스 초기화
4. 웹 컨테이너에 의한 서비스 메서드 호출
   (서비스 메서드는 요청이 들어올 때 마다 호출된다)
5. destroy 메서드를 호출하여 서블릿 종료
   <br/><br/>

### **생명주기 메소드**

&nbsp;&nbsp;HttpServlet 클래스는 아래와 같은 생명주기 메서드를 가지고 있다. HttpServelt 클래스를 상속받는 클래스는 아래의 메서드 중 적어도 하나를 오버라이딩하여 사용하여야 한다.
<br/>

- **doGet()** : HTTP GET 요청 처리
- **doPost()** : HTTP POST 요청 처리
- **doPut()** : HTTP PUT 요청 처리
- **doDelete()** : HTTP DELETE 요청 처리
- **init(), destroy()** : 서블릿 생명주기 동안 가지고 있는 리소스 관리
  <br/><br/>

## **Servlet 작성**

&nbsp;&nbsp;Servlet은 예시와 같이 작성할 수 있다. 예시에서 서블릿은 "/main" 주소로 요청이 들어왔을 때 호출이 되는데 @Annotation에 정의된 주소에 해당하는 서블릿이 호출되게 된다.

```java
/* Annotation을 사용한 명시 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {}
```

<br/>

&nbsp;&nbsp; 이전에는 web.xml의 \<web-app> 태그 내부에 다음와 같이 사용할 서블릿에 대한 정보를 입력하는 방식을 사용했다.

```xml
<!-- web.xml에 명시 -->
<servlet>
  	<servlet-name>MainServlet</servlet-name>
  	<servlet-class>com.package.servlet.MainServlet</servlet-class>
  </servlet>
  <servlet-mapping>
  	<servlet-name>MainServlet</servlet-name>
  	<url-pattern>/main</url-pattern>
  </servlet-mapping>
```

<br/>

&nbsp;&nbsp;아래 코드는 클라이언트의 form 태그에 의해 서버에서 사용자 정보 등록을 처리하기 전 /main으로 호출되는 서블릿의 예시이다.
<br/>
&nbsp;&nbsp;GET/POST로 들어온 사용자 요청에 의해 doGet, doPost 메서드가 호출되는데 이 메서드들은 다시 Controller를 호출하여 들어온 요청을 처리할 또 다른 Controller를 호출한다. 이는 Spring의 MVC에서도 사용되는 <u>**Front-controller 패턴**</u>으로 각각의 <u>요청을 Front-Controller가 받아 다시 작업 별로 또 다른 Controller를 호출해 처리</u>하는 방식이다.

```java
@WebServlet("/main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MainServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
            응답으로 내보낼 타입을 정의
            (직접 화면에 보여주려는 경우에만 필요, JSP에게 던져줄때는 필요 X)
        */
		response.setContentType("text/html; charset=UTF-8");
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
            받은 요청 데이터에 대한 인코딩 타입 정의
        */
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

	/*
        Front-controller pattern
        1. process와 관련된 메서드를 두어 요청을 받는 부분과 메인 프로세스를 분리
        2. 각 클라이언트는 Front-controller에 요청을 보내고, Front-controller는 요청이 들어온 작업을 처리하기 위한 Controller를 찾아 호출)
    */
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("action");
		if (type.equals("regist")) doRegist(request, response);
        else if (type.equals("remove")) doRemove(request, response);
	}

	protected void doRegist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		int age = request.getParameter("age") == null ? 0 : Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		String[] hobbies = request.getParameterValues("hobby");

		/*
            Regist 작업을 처리
        */
	}
}
```

<br/>
