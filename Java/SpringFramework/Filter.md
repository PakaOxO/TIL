# 📄 **Spring Filter**

<p align="center">
    <img style="width: 80%" src="../images/springLogo.png" alt="springLogo">
</p></br>

## **Filter**

- 요청과 응답 데이터를 필터링하여 제어, 변경하는 역할
- 사용자의 요청이 Servlet에 전달되어지기 전에 Filter를 거치게 됨
- Servlet으로 부터 Response가 사용자에게 전달되기 전에 Filter를 거치게 됨
- Spring에서는 **CharacterEncodingFilter**를 구현
- 사용 예시 : 사용자 인증, 로그 처리
  <br/><br/>

### **Servlet 생성 방법(복습)**

- Servlet이 되기 위한 클래스 파일 생성
- HttpServlet 구현
- 서블릿을 컨테이너에 등록
- 'WebServlet' Annotation을 사용하거나 Web.xml에서 등록

```xml
<!-- 서블릿 이름과 클래스명을 풀패키지 명으로 입력 -->
<servlet>
    <servlet-name>MyServlet</servlet-name>
    <servlet-class>com.ssafy.web.MyServlet</servlet-class>
</servlet>
<!-- 해당 이름을 가진 서블릿이 어떤 URL 요청을 처리할 지 입력 -->
<servlet-mapping>
    <servlet-name>MyServlet</servlet-name>
    <url-pattern>/MyServlet</url-pattern>
</servlet-mapping>
```

<br/>

## **Filter 구현**

- Filter가 되기 위한 클래스 파일 생성
- Filter 인터페이스 구현
- Filter를 컨테이너에 등록
- 'WebFilter' Annotation을 사용하거나 Web.xml에서 등록

```xml
<!-- 직접 구현한 필터 -->
<filter>
    <filter-name>MyFilter</filter-name>
    <filter-class>com.ssafy.web.MyFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>utf-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>MyFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>

<!-- Spring에서 구현되어 있는 필터 -->
<filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>utf-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

<details>
<summary>Filter 코드보기</summary>
<div markdown="1">

```java
/**
 * Servlet Filter implementation class MyFilter
 */
//@WebFilter("/MyFilter")
public class MyFilter implements Filter {
	private FilterConfig filterConfig;

    public MyFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/* web.xml에 Filter 등록 시에 설정한 encoding 값을 가져옴 */
		String encoding = this.filterConfig.getInitParameter("encoding");
		request.setCharacterEncoding(encoding);

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig = fConfig;
	}

}

```

</div>
</details>

