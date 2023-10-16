# 📄 **Spring FrameWork**

<p align="center">
    <img style="width: 80%" src="../images/springLogo.png" alt="springLogo">
</p></br>

## **Framework 특징**

### **POJO(Plain Old Java Object) 방식의 프레임워크**

&nbsp;&nbsp;EJB가 기능 작성을 위해 인터페이스를 구현하거나 상속하는 것과 달리 일반적인 자바 객체를 이용하여 그대로 사용할 수 있음
<br/><br/>

### **의존성 주입(Dependency Injection, DI)**

&nbsp;&nbsp;프레임워크 내부에서 사용되는 객체간 의존성이 존재할 경우, 개발자는 의존성에 관련한 설정만 해주면 실제 의존성 생성은 프레임워크가 담당
<br/><br/>

### **관점지향 프로그래밍(Aspect Oriented Programming, AOP) 지원**

&nbsp;&nbsp;트랜잭션, 로깅 등 여러 모듈에서 공통적으로 사용하는 기능들을 별도로 분리하여 작성, 관리할 수 있는 기능 제공
<br/><br/>

### **제어 역전(Inversion of Control, IoC)**

&nbsp;&nbsp;제어 역전을 통해 객체 및 프로세스의 제어를 프레임워크가 담당
<br/><br/>

### **높은 확장성, 다양한 라이브러리 지원**

&nbsp;&nbsp;기존의 라이브러리를 스프링에서 사용할 수 있도록 지원하며, MyBatis나 Hibernate 등의 데이터베이스 라이브러리와 연결 가능한 인터페이스 제공
<br/><br/>

## **Spring Framework 프로젝트 생성 과정**

### **Maven Project(빌드 도구) 생성**

&nbsp;&nbsp;Maven 프로젝트 사용시 문제가 발생했을 경우..(라이브러리 연동이 안되거나...)

- alt + F5 : Update Project (프로젝트 우클릭 > Maven > Update Project)
- 상단 메뉴 project > clean
- 이클립스를 종료한 상태에서 사용자 > .m2 폴더 삭제 후 이클립스 재시작
  <br/><br/>

### **Maven 라이브러리 추가**

1. **pom.xml에 build 태그 종료 이후, dependencies 태그 추가**
   </br></br>

2. **dependencies 태그 내부에 Spring을 포함한 추가할 라이브러리 작성**

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.18</version>
</dependency>
```

</br>

3. **Spring 설정파일을 작성하고 객체(Bean) 등록**

- 프로젝트에 리소스들을 관리할 경로(resources)를 만든 뒤 java bean configuration 파일 생성
  <br/><br/>

**방법 1 : 설정 파일에서 설정**

- Bean을 호출할 id를 지정해주어야 한다.

- 만약 해당 Bean 객체를 생성하는 시점이 컨테이너 생성 시가 아닌 Bean 객체 호출시로 변경하고 싶다면 lazy-init="true" 속성을 지정해주면 되고, 매 Bean 객체 호출 시마다 새로운 객체를 생성하고 싶다면 scope="prototype" 속성을 지정해주면 된다.

- Bean 내부에 property 태그에 값을 지정하면 set 메서드로 정의된 값을 객체 생성시에 디폴트로 지정해주고 객체를 생성해준다. value일 경우에는 원시 타입을, ref로 지정해주었을 경우에는 객체와 같은 참조형 데이터 타입을 지정해 <u>의존관계를 설정</u>할 수 있다. 이는 직접 해당 클래스의 코드에 접근해 타 클래스와의 의존관계를 설정해주는 것이 아닌 <u>**설정파일에서 setter 메서드를 이용해 의존성을 주입하는 setter injection으로 IoC(Inversion of Control)이 발생**</u>한 예시이다.

- Setter Injection의 경우, setter 메서드에 의해 자바코드로 수정될 수 있지만 Constructor Injection의 경우 setter 메서드가 구현되어 있지 않다면 이미 생성된 Bean 객체의 property는 수정할 수 없다.

<details>
<summary>코드보기</summary>
<div markdown="1">

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean class="com.ssafy.spring.Person" id="person">
        <!-- Setter Injection -->
		<property name="age" value="20"></property>
        <property name="job" ref="worker"></property>

        <!-- Constructor Injection -->
        <constructor-arg name=
        "job" ref="worker"></constructor-arg>
	</bean>
</beans>

```

</div>
</details>
<br/>

**방법 2 : 자바 코드의 Annotation을 읽어 사용**

- context 태그의 component-scan 속성에 패키지 주소를 입력하면 해당 패키지 내의 클래스에 Annotation으로 지정된 클래스들을 Bean으로 지정.

- 해당 클래스 위에 작성하는 Annotation은 @Component를 작성해 Bean으로 등록. (조금 더 디테일하게 @Controller, @Service, @Repository로 각각의 역할에 맞는 Bean을 등록하는 것도 가능)

- 호출되는 Bean의 id는 해당 클래스 명의 소문자 형태, 또는 @Annotation ("beanId") 로 직접 지정도 가능.

- 의존성 주입은 의존성을 주입할 객체의 setter 메서드 또는 생성자 위에 @Autowired Annotation을 작성

- 파라미터로 받는 타입에 맞는 Bean을 찾아 매핑을 해주지만 2개 이상의 Bean을 읽어오려 하는 상황에서는 에러 발생. (@Qualifier ('beanId')를 지정하여 특정 Bean을 읽어오게 하거나 setter의 변수명(setPerson이라면 person)과 이름이 같은 Bean이 있다면 해당 Bean을 우선적으로 읽어오게 됨)
  </br></br>

- @Qualifier Annotation은 setter injection의 경우엔 @Autowired Annotation 아래에, constructor injection의 경우엔 파라미터 앞에 작성

<details>
<summary>코드보기</summary>
<div markdown="1">

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<context:component-scan base-package="com.ssafy.spring" />
</beans>

```

</div>
</details>
<br/><br/>

**방법 3 : Configuration Java 파일을 사용한 설정**

- ApplicatoinConfig.java 파일 생성

- 해당 클래스 상단에 @Configuration Annotation 작성 및 등록할 Bean의 대상 클래스들이 있는 패키지 주소 입력 (이 주소 내부에 @Component Anntation이 작성된 클래스들은 자동으로 Bean 등록)

- ApplicationConfig 클래스 내부 @Bean Annotation으로 Bean 등록

- 반환 타입은 Bean으로 등록할 클래스를, 메서드 명은 BeanId를, 반환값은 빈 객체를 의미하며, setter를 사용해 의존성 주입이 가능하다.
  </br></br>

- Bean 객체를 받는 방법은 아래 코드와 같다.

```java
ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

Computer computer = context.getBean("computer", Computer.class);
```

<details>
<summary>코드보기</summary>
<div markdown="1">

```java
@Configuration
@ComponentScan(basePackages = { "com.ssafy.spring" })
public class ApplicationConfig {
	@Bean
	public Person person {
		return new Person();
	}

	@Bean
	public Computer computer {
		Computer computer = new Computer();
		computer.setPrice(200);
		return computer;
	}
}

```

</div>
</details>
<br/><br/>

4. **Spring Container 객체 빌드 및 사용**

```java
package com.ssafy.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SpringTest {

	public static void main(String[] args) {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

		// Bean을 Object 타입으로 가져온 뒤 casting
		Person p = (Person) context.getBean("person");

		// 가져올 Bean의 타입을 미리 지정
		Person p2 = context.getBean("person", Person.class);

		p.setAge(10);
		System.out.println(p.getAge()); // 10
		System.out.println(p2.getAge()); // 10, 기본적으로 Java Bean 객체는 Singleton 패턴
	}

}
```
