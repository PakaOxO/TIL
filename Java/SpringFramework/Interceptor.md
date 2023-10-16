# 📄 **Spring Interceptor**

<p align="center">
    <img style="width: 80%" src="../images/springLogo.png" alt="springLogo">
</p></br>

## **Interceptor**

- HandlerInterceptor를 구현 또는 HandlerInterceptorAdaptor를 상속(HandlerInterceptorAdaptor 상속은 권장되는 방식은 X)
- 요청을 처리하는 과정에서 요청을 가로채서 처리
- 접근제어(auth), 로그(log) 등 비즈니스 로직과 구분되는 반복적이고 부수적인 요청 처리
  <br/><br/>

### **Interceptor 주요 메서드**

- preHandle() : Handler가 실행되기 이전에 호출
- postHandle() : Handler가 실행되고 나서 호출
- afterCompletion : 요청이 처리되고 뷰 생성이 완료된 후에 호출
  <br/><br/><br/>

## **Interceptor 구현 예시 : 로그인 체크**

### **구현 개요**

- 로그인이 되어있다면 session에서 해당 정보를 체크
- 특정 페이지에 로그인이 안된채 접근하면 로그인 페이지로 redirect
  <br/><br/>

### **root-context.xml에 service를 Bean으로 등록하기 위해 패키지 경로 지정**

```xml
<context:component-scan base-package="com.ssafy.mvc.model.service"></context:component-scan>
```

<br/>

### **Service에서 구현한 로직을 바탕으로 Controller에서 결과 반환**

```java
@PostMapping("login")
public String login(HttpSession session, Model model, String id, String pw) {
    boolean result = loginService.login(id, pw);

    if (result) {
        session.setAttribute("username", id);
        return "redirect:/";
    } else {
        return "redirect:/login";
    }
}

@PostMapping("logout")
public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
}
```

<br/>

### **로그인을 하지 않았을 때 처리할 interceptor 구현**

```java
public class LoginCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();

        // 세션에 로그인 정보가 없다면 login 화면으로 redirect
        // return 값이 false면 이후의 로직은 처리하지 않고 stop
		if (session.getAttribute("loginInfo") == null) {
			response.sendRedirect("login");
			return false;
		} else {
			return true;
		}
	}
}

```

<br/>

### **구현한 interceptor Bean에 등록 및 interceptor 등록**

- 인증(Auth), 로그(Log) 등의 웹과 관련된 설정이므로 servlet-context.xml에 작성
  <br/>

```xml
<beans:bean class="com.ssafy.mvc.interceptor.LoginCheckInterceptor" id="confirm"></beans:bean>
	<interceptors>
		<!-- 모든 경로에 interceptor를 받고 예외를 처리하는 방식 -->
		<interceptor>
			<mapping path="/*"></mapping>
            <!-- contextPath거나 로그인 외의 페이지는 로그인 체크 -->
			<exclude-mapping path="/"/>
			<exclude-mapping path="/login"/>
			<beans:ref bean="confirm"></beans:ref>
		</interceptor>
		<!--  경로 하나하나에 interceptor를 지정하려면 하나씩 mapping path 지정하면 됨 -->
	</interceptors>
```

<br/><br/>
