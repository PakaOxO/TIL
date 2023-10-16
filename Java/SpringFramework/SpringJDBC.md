# 📄 **Spring JDBC 설정**

<p align="center">
    <img style="width: 80%" src="../images/springLogo.png" alt="springLogo">
</p></br>

## **설정 과정**

### **1. 관련 라이브러리 pom.xml에 등록**

&nbsp;&nbsp;샘플 코드에서는 MySql을 사용했므로 MySQL JDBC 라이브러리와 SpringContext, 그리고 스프링에서 구현된 connectionPool을 사용하기 위한 springJDBC 라이브러리를 pom.xml에 등록.
<br/>

&nbsp;&nbsp;아래 코드에서 주석되어 있는 dependency는 Apache에서 지원하는 connection Pool로 스프링의 기본 Connection Pool보다 많은 기능을 지원.
<br/>

<details>
<summary>코드보기</summary>
<div markdown="1">

```xml
<dependencies>
  	<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
	<dependency>
	    <groupId>mysql</groupId>
	    <artifactId>mysql-connector-java</artifactId>
	    <version>8.0.28</version>
	</dependency>
  	<!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-context</artifactId>
	    <version>5.3.18</version>
	</dependency>

	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-jdbc</artifactId>
	    <version>5.3.18</version>
	</dependency>

<!-- Apache에서 지원하는 commons jdcp2 라이브러리
    <dependency>
	    <groupId>org.apache.commons</groupId>
	    <artifactId>commons-dbcp2</artifactId>
	    <version>2.9.0</version>
	</dependency>
-->

  </dependencies>
```

</div>
</details>
<br/><br/>

### **2. Spring Connection Pool 클래스를 Bean에 등록**

&nbsp;&nbsp;아래 코드에서는 DriverManagerDataSource 클래스를 사용해 DB와 연결. 이 Bean 객체는 생성될 때 DB 연결정보가 필요하므로 <u>DB 주소, 드라이버 이름, DB 사용자 명과 패스워드</u> 등의 Property 값을 설정해야 한다.
<br/>

&nbsp;&nbsp;DAO 구현 클래스는 DriverManagerDataSource 클래스를 의존하므로 DAO 구현 클래스의 객체가 생성될 때 DriverManagerDataSource 클래스를 파라미터로 받도록 의존 관계 등록(생성자 주입)
<br/>

&nbsp;&nbsp;DriverManagerDataSource 밑에 주석처리 되어있는 Bean은 위에서 언급했던 Apache Connection Pool로 initialSize의 경우에는 처음 커넥션 풀이 생성될 때 풀의 사이즈를 5개로 생성되게 하도록 지정하는 속성이다. 이와 같이 Spring 기본 Connection Pool보다 많은 기능들을 지원한다.
<br/>

<details>
<summary>코드보기</summary>
<div markdown="1">

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Connection Pool 구현 클래스 -->
	<bean class="org.springframework.jdbc.datasource.DriverManagerDataSource" id="dataSource">
		<property name="url" value="jdbc:mysql://localhost:3306/ssafy_board?serverTimezone=UTC"></property>
		<property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
		<property name="username" value="root"></property>
		<property name="password" value="1234"></property>
	</bean>

<!-- Apache Connection Pool Lib
    <bean class="org.apache.commons.dbcp2.BasicDataSource" id="dataSource">
		<property name="url" value="jdbc:mysql://localhost:3306/ssafy_board?serverTimezone=UTC"></property>
		<property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
		<property name="username" value="root"></property>
		<property name="password" value="ssafy01"></property>
		<property name="initialSize" value="5"></property>
	</bean>
-->

    <!-- dataSource 의존 -->
	<bean class="com.ssafy.board.model.dao.BoardDaoImpl" id="boardDao">
		<constructor-arg name="ds" ref="dataSource"></constructor-arg>
	</bean>

	<bean class="com.ssafy.board.model.service.BoardServiceImpl" id="boardService">
		<constructor-arg name="boardDao" ref="boardDao"></constructor-arg>
	</bean>
</beans>
```

</div>
</details>
<br/><br/>

### **3. Connection Pool을 사용할 DAO 구현 클래스 수정**

&nbsp;&nbsp;DAO 구현 클래스에서는 DB에 접속하기 위한 클래스가 필요하므로 앞서 Bean으로 등록한 Connection Pool 클래스를 의존하도록 생성자에 등록.
<br/>

<details>
<summary>코드보기</summary>
<div markdown="1">

```java
public class BoardDaoImpl implements BoardDao {
	private DataSource ds;

	private BoardDaoImpl(DataSource ds) {
		this.ds = ds;
	}

    /* ... 다른 코드 생략 ...*/
}
```

</div>
</details>
<br/><br/>
