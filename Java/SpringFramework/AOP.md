# 📄 **AOP**

<p align="center">
    <img style="width: 80%" src="../images/springLogo.png" alt="springLogo">
</p></br>

## **관점 지향 프로그래밍(Aspect Orieted Programming, AOP)**

&nbsp;&nbsp;객체지향 프로그래밍(OOP)에서 모듈화의 핵심 단위는 클래스인 반면에 AOP에서는 Aspect(관점)가 핵심 단위가 된다. 관심사항은 크게 메인 기능을 담당하는 핵심 관심사항(Core Concern)과 여러 기능들에서 두루 사용되는 공통 관심사항(Cross Cutting)으로 나뉘는데 <u>Aspect는 공통 관심사항을 모듈화</u> 한 것이다.

&nbsp;&nbsp;AOP는 Spring FrameWork에서 필수로 사용되는 요소는 아니지만, AOP는 Spring의 IoC를 보완할 수 있다.
<br/><br/>

### **AOP 용어**

- **Aspect** : 여러 클래스에서 공통적으로 구현되는 관심사(Concern)가 모듈화 된 것

- **Join point** : 메서드의 실행이나 예외처리 프로그램이 실행되는 중의 특정 시점을 가리키며 Spring에서는 메서드 실행을 의미. Aspect는 이러한 Join Point 중 특정한 지점에 결합

- **Pointcut** : Join Point에 Aspect를 결합하기 위한 조건. Aspect는 Joint Point 중에서 Pointcut에 부합하는 모든 시점에서 실행.

- **Advice** : Pointcut에 의해 Join Point에 결합된 Aspect에 타입(before, after, around)이 적용된 것.

- **Target 객체** : 하나 이상의 Advice가 적용될 객체로 Spring Aop에서는 Runtime Proxy를 사용하여 구현되므로 Target 객체는 항상 Proxy 객체가 됨.
  </br></br>

- **AOP Proxy** : AOP를 구현하기 위해 AOP 프레임워크에 의해 생성된 객체. Spring FramWork에서 AOP Proxy는 JDK dynamic proxy 또는 CGLIB proxy.

- **Weaving** : Aspect를 다른 객체와 연결하여 Advice 객체를 생성하는 것. 런타임 또는 로딩 시에 수행될 수 있지만 Spring proxy는 Runtime Proxy이므로 런타임에 Weaving이 수행 됨.
  <br/></br>

## **Spring AOP**

### **메인 프로세스**

1. 핵심 관심사항 코드 작성
2. Aspect(공통 관심사항) 코드 작성
3. AspectJ 라이브러리를 사용해 Pointcut를 지정하고, weaving

### **1. @AspectJ 라이브러리 등록**

- Maven Repository에서 AspectJ Weaver 및 AspectJ Runtime을 xml에 등록
- Spring Configuration 파일의 NameSpace에서 aop 태그들을 사용하도록 체크

<details>
<summary>코드보기</summary>
<div markdown="1">

```xml

<dependencies>
  <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-context</artifactId>
	    <version>5.3.18</version>
	</dependency>


  	<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
	<dependency>
	    <groupId>org.aspectj</groupId>
	    <artifactId>aspectjweaver</artifactId>
	    <version>1.9.9.1</version>
	    <scope>runtime</scope>
	</dependency>

	<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjrt -->
	<dependency>
	    <groupId>org.aspectj</groupId>
	    <artifactId>aspectjrt</artifactId>
	    <version>1.9.9.1</version>
	    <scope>runtime</scope>
	</dependency>


  </dependencies>

```

</div>
</details>
</br>

### **2. Pointcut에 따라 Aspect를 핵심 관심사항(Target)과 Weaving**

**방법 1: Bean 설정 파일에서 작성**

- execution 안에 어떤 조건(Pointcut)에서 Weaving을 발생시킬지 지정.
- \*(asterisk)는 모든을 의미

```xml
<!-- public(접근제한자) void(반환값) com.ssafy.aop.*.doSomething()(패키지명.클래스명.메서드명) -->
<aop:pointcut expression="execution( public void com.ssafy.aop.*.doSomething() )" id="mPt"/>
```

<details>
<summary>코드보기</summary>
<div markdown="1">

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd">

	<bean class="com.ssafy.aop.SSAFY" id="ssafy"></bean>
	<bean class="com.ssafy.aop.Worker" id="worker"></bean>
	<bean class="com.ssafy.aop.MyAspect" id="myAspect"></bean>

<!-- proxy-target-class="true"로 지정하면 CGLIB proxy 모드 사용 (default: false)
	<aop:aspectj-autoproxy proxy-target-class="true"></aop:aspectj-autoproxy>
-->
	<aop:config>
		<aop:pointcut expression="execution( public void com.ssafy.aop.*.doSomething() )" id="mPt"/>
		<aop:aspect ref="myAspect">
			<aop:before method="beforeMethod" pointcut-ref="mPt"/>
			<aop:after-returning method="afterReturningMethod" pointcut-ref="mPt" />
			<aop:after-throwing method="afterThrowingMethod" pointcut-ref="mPt" />
			<aop:after method="afterMethod" pointcut-ref="mPt"/>
		</aop:aspect>
	</aop:config>

</beans>

```

</div>
</details>
<br/><br/>

**방법 2: Annotation을 사용한 작성**

1. **Bean 설정 파일 작성**

<details>
<summary>코드보기</summary>
<div markdown="1">

```xml

```

</div>
</details>
<br/>

2. **Aspect에 Annotation으로 Pointcut 및 Advice 지정**

- 방법 1에서는 언급하지 않았지만 Around로 지정하면 before, after...로 구분해서 advice를 지정하는 것이 아니라 조금 더 유연하게 코드로 핵심 관심사항과 공통 관심사항의 작업 순서를 지정할 수 있다.

<details>
<summary>코드보기</summary>
<div markdown="1">

```java
package com.ssafy.aop;

import org.aspectj.lang.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {

	@Pointcut("execution ( puiblic void com.ssafy.aop.*.doSomething() )")
	public void mPt() { // 포인트컷 지정

	}

	@Before("mPt()") // 뒤에는 포인트컷
	public void beforeMethod() {
		System.out.println("입실 체크");
	}

	@After("mPt()")
	public void afterMethod() {
		System.out.println("집에서 쉽니다.");
	}

	@AfterReturning("mPt()")
	public void afterReturningMethod() {
		System.out.println("퇴실 체크.");
	}

	@AfterThrowing("mPt()")
	public void afterThrowingMethod() {
		System.out.println("중간 퇴실.");
	}

	@Around("mPt()")
	public void aroundMethod(ProceedingJoinPoint pjt) {
		this.beforeMethod();
		try {
			pjt.proceed();
			this.afterReturningMethod();
		} catch (Throwable e) {
			this.afterThrowingMethod();
		} finally {
			this.afterMethod();
		}
	}
}

```

</div>
</details>
<br/><br/>
