# 📄 **MyBatis**

<p align="center">
    <img style="width: 80%" src="../images/mybatis.png" alt="mybatis">
</p></br>

&nbsp;&nbsp;SQL 매핑 프레임워크로 SQL문과 저장 프로시저 등의 매핑을 지원하는 퍼시스턴스 프레임워크(persistence framework)이다. JDBC로 처리하는 삳당부분의 코드와 파라미터 설정 및 결과 처리를 대신해주며, Map 인터페이스와 자바 POJO를 설정 데이터베이스와 매핑해서 사용할 수 있다. XML과 Annotation 설정을 통해 사용 가능하다.
</br></br>

### **MyBatis 구성**

- **환경설정 파일** : MyBatis 전반에 걸친 세팅(DB 접속정보, 모델 클래스 정보, 매핑정보)들을 담고 있는 파일

- **Mapper** : 사용할 SQL문을 정의

- **Mapped Statement** : SqlSession 빌드 및 사용, SQL문 실행을 담당

- **Input/Output** : SQL 실행 시에 필요한 데이터 및 결과

- **SqlSessionFactory** : 모든 MyBatis 어플리케이션은 SqlSessionFactory 인스턴스를 사용하며, SqlSessionFactory는 SqlSession을 생성.

- **SqlSession** : 데이터베이스에 대해 SQL 명령어를 실행하기 위한 메서드를 가지고 있음
  </br></br>

## **MyBatis 설정**

### **1. MyBatis 라이브러리 가져오기 (pom.xml)**

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.9</version>
</dependency>
```

</br>

### **2. XML에서 SqlSessionFactory 빌드**

- **environments** : DB 접속정보, transaction 관리방법 등을 정의
- **transactionManager** : Transaction 관리방법 설정 (JDBC : 수동, MANAGED : 자동)
- **dataSource** : JDBC Connection 객체의 소스 설정 (UNPOOLED : 매 요청마다 커넥션을 열고 닫음)
  </br>

```xml
<!-- mybatis-config.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 환경설정 파일 지정 -->
	<properties resource="config/db.properties" />

    <!-- 클래스 이름을 줄여 사용하기 위한 typeAliases 지정 -->
    <typeAliases>
		<typeAlias type="com.ssafy.board.model.dto.Board" alias="Board"/>
	</typeAliases>

	<!-- 개발용, 운영용 분리해서 설정 가능 -->
	<environments default="development">
		<environment id="development">
			<transactionManager type="JDBC" />
			<dataSource type="POOLED">
				<property name="driver" value="${driver}" />
				<property name="url" value="${url}" />
				<property name="username" value="${username}" />
				<property name="password" value="${password}" />
			</dataSource>
		</environment>
	</environments>

	<!-- mapper 파일 지정 -->
	<mappers>
		<mapper resource="mapper/board.xml" />
	</mappers>
</configuration>
```

</br>

- **DB 설정파일 작성 (db.properties) : DB 설정정보**

```properties
url=jdbc:mysql://localhost:3306/ssafy_board?serverTimezone=UTC
driver=com.mysql.cj.jdbc.Driver
username=root
password=ssafy01
```

</br>

- **Mapper 파일 작성 (board.xml) : 사용할 SQL 정의**

&nbsp;&nbsp;Mapper에서 가져온 변수를 사용하는 방법은 ${ }와 #{ }를 사용하는 두 가지 방법이 있는데 전자는 가져온 변수값 그대로를 쿼리문에 넣는 반면 후자의 경우에는 앞 뒤로 ''를 붙여준다는 특징이 있다.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ssafy.board.model.dao.BoardDao">
	<resultMap type="Board" id="boardMap">
		<!-- id라는 컬럼에 id라는 값을 넣겠다는 뜻 -->
		<result column="id" property="id" />
		<result column="writer" property="writer" />
		<result column="title" property="title" />
		<result column="content" property="content" />
		<result column="reg_date" property="regDate" />
		<result column="view_cnt" property="viewCnt" />
	</resultMap>

	<select id="selectAll" resultMap="boardMap">
		SELECT * FROM BOARD
	</select>

	<select id="selectOne" parameterType="int" resultMap="boardMap">
		SELECT * FROM BOARD WHERE id = #{ id }
	</select>

    <!-- keyProperty="id" useGeneratedKeys="true"는 쿼리가 종료 후 데이터의 id값 board에 넣음 -->
	<insert id="insertBoard" parameterType="Board" keyProperty="id" useGeneratedKeys="true">
		INSERT INTO BOARD (title, writer, content) VALUES (#{ title }, #{ writer }, #{ content })
	</insert>

	<delete id="deleteBoard" parameterType="int">
		DELETE FROM BOARD WHERE id = #{ id }
	</delete>

	<update id="updateBoard" parameterType="Board" keyProperty="id" useGeneratedKeys="true">
		UPDATE BOARD SET title = #{ title }, content = #{ content }, reg_date = now() WHERE id = #{ id }
	</update>

	<update id="updateViewCnt" parameterType="int">
		UPDATE BOARD SET view_cnt = #{ viewCnt  } + 1 WHERE id = #{ id }
	</update>

    <!-- 조회조건에 따라 검색 -->
	<select id="search" resultMap="boardMap" parameterType="SearchCondition">
		SELECT * FROM BOARD
		<!-- 동적 쿼리 사용 가능 -->
		<if test="key != 'none'">
			WHERE ${ key } LIKE CONCAT('%', #{ word }, '%')
		</if>
		<if test="orderBy != 'none'">
			ORDER BY ${ orderBy } ${ orderByDir }
		</if>
	</select>
</mapper>
```

</br>

- **SqlSessionFactory 빌드 (MyAppSqlConfig.java)**

```java
package com.ssafy.board.config;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyAppSqlConfig {
	private static SqlSession session;

	/* 처음 자바 파일이 메모리에 올라갔을 때 작업 수행을 위해 static에 작성 */
	static {
		try {
			String resource = "config/mybatis-config.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

			/* 세션 팩토리를 이용해 세션을 얻어 옴 */
			session = sqlSessionFactory.openSession(true);// true 옵션을 주면 자동커밋


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SqlSession getSession() {
		return session;
	}
}

```

