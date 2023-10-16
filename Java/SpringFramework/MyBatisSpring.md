# 📄 **MyBatis**

<p align="center">
    <img style="width: 80%" src="../images/mybatis.png" alt="mybatis">
</p></br>

&nbsp;&nbsp;SQL 매핑 프레임워크로 SQL문과 저장 프로시저 등의 매핑을 지원하는 퍼시스턴스 프레임워크(persistence framework)이다. JDBC로 처리하는 삳당부분의 코드와 파라미터 설정 및 결과 처리를 대신해주며, Map 인터페이스와 자바 POJO를 설정 데이터베이스와 매핑해서 사용할 수 있다. XML과 Annotation 설정을 통해 사용 가능하다.
</br></br>

## **Spring에서 MyBatis 사용하기**

### **1. Web.xml에 라이브러리 등록**

&nbsp;&nbsp;트랜잭션은 데이터베이스 시스템에서 처리되는 작업의 논리적인 최소 단위로 각각의 트랜잭션은 데이터베이스에 반영 또는 반영이 되지 않는 2가지 결과가 존재하며 반영시에는 트랜잭션 내의 모든 명령은 반드시 완벽하게 수행되어야 한다. 만약 오류가 발생한다면 트랜잭션 내 모든 명령은 취소된다. 이러한 트랜잭션을 관리하기 위해 Spring에서 지원하는 라이브러리는 Spring-tx이다.
</br></br>

```xml
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<!-- mysql JDBC -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.28</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.apache.commons/commons-dbcp2 -->
<!-- Conntection pool 관리를 위한 라이브러리 (필수는 아님) -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-dbcp2</artifactId>
    <version>2.9.0</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<!-- MyBatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.9</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
<!-- MyBatis Spring -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.6</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
<!-- Spring JDBC -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.23</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.springframework/spring-tx -->
<!-- Spring-tx -->
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-tx</artifactId>
	<version>${org.springframework-version}</version>
</dependency>
```

</br>

### **2. MyBatis 실행에 필요한 객체롤 Bean으로 등록 (root-context.xml)**

- dataSource, sqlSessionFactory 등

```xml
<!-- DB 정보를 가진 BasicDataSource 객체를 빈으로 등록 -->
<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver" />
    <property name="url" value="jdbc:mysql://localhost:3306/ssafy_board?serverTimezone=UTC" />
    <property name="username" value="root" />
    <property name="password" value="ssafy01" />
</bean>

<!-- MyBatis를 사용하기 위해 sqlSessionFactory 객체를 빈으로 등록 -->
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource" />
    <property name="mapperLocations" value="classpath*:mapper/**/*.xml" />
    <!-- dto에 등록된 객체를 바로 파라미터로 사용 가능하게 지정 -->
    <property name="typeAliasesPackage" value="com.ssafy.board.model.dto" />
</bean>

<!-- MyBatis에서 스캔할 Dao의 패키지 경로 지정 -->
<mybatis-spring:scan base-package="com.ssafy.board.model.dao"/>
```

</br>

### **3. 게시판과 관련된 Service 클래스 구현**

- Service는 Controller에 의해 적절하게 호출
  </br>

```java
@Service
public class BoardServiceImpl implements BoardService {
	private BoardDao boardDao;

	/* setter injection */
	@Autowired
	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Override
	public List<Board> getBoardList() {
		return boardDao.selectAll();
	}

	@Override
	public Board readBoard(int id) {
		boardDao.updateViewCnt(id);
		return boardDao.selectOne(id);
	}

	@Override
	public void writeBoard(Board board) {
		boardDao.updateBoard(board);
	}

	@Override
	public void modifyBoard(Board board) {
		boardDao.updateBoard(board);
	}

	@Override
	public void removeBoard(int id) {
		boardDao.deleteBoard(id);
	}
}
```

</br>

### **4.BoardController 작성**

```java
package com.ssafy.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("/")
	public ModelAndView showIndex() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:list");
		return mav;
	}

	@RequestMapping("list")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		List<Board> list = boardService.getBoardList();

		mav.addObject("list", list);
		mav.setViewName("/board/list");
		return mav;
	}
}

```

