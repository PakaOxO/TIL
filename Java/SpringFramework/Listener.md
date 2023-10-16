# 📄 **Spring Listener**

<p align="center">
    <img style="width: 80%" src="../images/springLogo.png" alt="springLogo">
</p></br>

## **Listener**

- 특정 이벤트가 발생하기를 기다리다가 이벤트가 발생하면 실행되는 객체
- ServletContextListener는 생명주기와 관련된 리스너
  <br/><br/>

## **Listener 구현**

- Listener 되기 위한 클래스 파일 생성
- ServletContextListener 인터페이스 구현
- Listener 컨테이너에 등록
- '@WebListener' Annotation을 사용하거나 Web.xml에서 등록

```xml
<listener>
  	<listener-class>com.ssafy.web.MyListener</listener-class>
</listener>
<context-param>
    <param-name>contextPath</param-name>
    <param-value>helloHome</param-value>
</context-param>
```

<details>
<summary>Listener 코드보기</summary>
<div markdown="1">

```java
package com.ssafy.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class MyListener
 *
 */
@WebListener
public class MyListener implements ServletContextListener {

    public MyListener() {
        // TODO Auto-generated constructor stub
    }

    public void contextDestroyed(ServletContextEvent sce)  {
         // TODO Auto-generated method stub
    }

    public void contextInitialized(ServletContextEvent sce)  {
         // TODO Auto-generated method stub
    }

}
```

</div>
</details>

