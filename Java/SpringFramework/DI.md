📄 **DI**
===================
## **DI (Dependency Injection)**
### **의존성, Dependency**
&nbsp;&nbsp;class A의 객체가 어떤 작업을 처리하기 위해 class B의 프로퍼티나 메서드를 사용해야 한다면 class A는 class B에 의존하는 클래스이다.
<br/>

&nbsp;&nbsp;의존성 주입은 위와 같은 관계에서 class A가 class B의 객체를 생성하여 해당 클래스의 요소를 사용하는 것이 아니라 외부에서 class A의 setter나 생성자를 호출하여 class B의 객체를 생성해 넘겨주는 것으로 전자를 setter injection, 후자를 constructor injection이라고 부른다.
<br/><br/>