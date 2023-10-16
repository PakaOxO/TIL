# 📄 **Spring File**

<p align="center">
    <img style="width: 80%" src="../images/springLogo.png" alt="springLogo">
</p></br>

## **파일 업로드 & 다운로드**

### **pom.xml에 File Library 등록**

```xml
<!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.4</version>
</dependency>
```

<br/>

### **servlet-context.xml에 CommonMulipartResolver 빈으로 등록**

- 최대 업로드 크기와 인코딩 타입을 정의

```xml
<beans:bean class="org.springframework.web.multipart.commons.CommonsMultipartResolver" id="multipartResolver">
    <beans:property name="maxUploadSize" value="10485760" />
    <beans:property name="defaultEncoding" value="UTF-8" />
</beans:bean>
```

<br/>

### **업로드를 처리할 Controller 구현 및 업로드 페이지 작성**

- Controller

```java
@Autowired
private ServletContext servletContext;

/* 다중 파일 업로드를 하려면 MultipartFile을 배열 타입으로 받아야 함 */
@PostMapping("fileupload")
public ModelAndView fileUpload(@RequestParam("upload_file") MultipartFile uploadFile) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("result");

    String uploadPath = servletContext.getRealPath("/file");
    String fileName = uploadFile.getOriginalFilename();

    File target = new File(uploadPath, fileName);

    /* 업로드 경로가 없으면 경로 생성 */
    if (!new File(uploadPath).exists()) {
        new File(uploadPath).mkdir();
    }

    try {
        /* 업로드 파일을 바이트로 복사 */
        FileCopyUtils.copy(uploadFile.getBytes(), target);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    mav.addObject("fileName", fileName);
    return mav;
}
```

<br/>

- 업로드 폼

```html
<form action="fileupload" method="POST" enctype="multipart/form-data">
  <input type="file" name="upload_file" />
  <input type="submit" value="업로드" />
</form>
```

<br/>

### **파일 다운로드 구현**

- Bean 이름으로 뷰를 찾기 위해 BeanNameViewResolver 등록
- FileDownloadView를 빈으로 등록한 뒤 사용

```xml
<beans:bean class="org.springframework.web.servlet.view.BeanNameViewResolver" id="fileViewResolver">
    <beans:property name="order" value="0"></beans:property>
</beans:bean>
```

<br/>

