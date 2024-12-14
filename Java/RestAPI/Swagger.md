## **Swagger**

&nbsp;&nbsp;프로젝트 개발 시 일반적으로 Front-end 개발자와 Back-end 개발자가 분리가 되어있는데, Front-end 개발자의 경우 화면에 집중하면서 Back-end 개발자가 만든 문서 API를 보면서 데이터 처리를 하게 된다. 개발 상황의 변화에 따른 API의 추가 또는 변경시 마다 문서를 수정해야하는 불편함이 발생하는데 이러한 문제를 해결하기 위해 Swagger가 사용된다.
<br/>

### **Swagger 특징**
- 기존에 문서로 관리하던 API를 Swagger로 사용
- 간단한 설정으로 프로젝트의 API 목록을 웹에서 확인 및 테스트 할 수 있는 라이브러리를 지원
- Controller에 정의되어 있는 모든 URL을 바로 확인 가능
- API 목록 외에도 API의 명세 및 설명을 볼 수 있으며 API 테스트도 가능

<br/><br/>

## **Swagger 실습**

### **Swagger 라이브러리 pom.xml에 추가**

```xml
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>3.0.0</version>
</dependency>

<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>3.0.0</version>
</dependency>
```

<br/>

### \*\*\*\*
