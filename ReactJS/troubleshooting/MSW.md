
&nbsp;&nbsp;개발 프로젝트를 진행하면서 `Mocking`의 필요성을 느끼게 되어 대표적인 `Mock` 라이브러리 중 하나인 `MSW`의 도입을 프로젝트 팀에 권유해보려고 합니다. 이에 앞서 관련한 내용을 이번 포스팅을 통해 간단하게 다뤄보도록 하겠습니다.

<br>

### 도입 계기

&nbsp;&nbsp;몇 번의 서비스 개발을 진행하면서 느낀 점은 프론트엔드와 백엔드의 개발 진척상황 차이와 서비스의 특성상 불가피하게 프론트엔드가 백엔드에 의존하게 되는 경우가 발생한다는 점입니다. 프론트엔드는 서버로부터 받은 데이터를 토대로 화면을 그려야 하는데 만약 해당 API가 아직 개발이 완료되지 않았다면 그동안은 더미 데이터를 만들어 화면에 표시하곤 했습니다.

**이상적인 개발 프로세스**

&nbsp;&nbsp;아래 이미지는 카카오 기술 블로그에 한 개발자 분이 `Mocking`과 관련한 포스팅에서 참조했습니다. 그림과 같이 이상적인 개발은 백엔드의 개발이 완료되고, 프론트엔드가 서버 사이드로부터 API를 통해 데이터를 받아 렌더링하는 것이 좋습니다. 하지만 실제 개발은 백엔드와 프론트엔드가 동시에 진행되는 경우가 많죠.

![ideal dev process | 600](../images/ideal_devProcess.png)

<br>

&nbsp;&nbsp;더미 데이터를 만들어 `Mocking`하는 방식은 빠르게 개발을 진행하는데에는 사실 크게 무리가 없습니다. 다만, 테스트를 위한 코드가 비즈니스 로직 여기저기에 포함되게 되고, 코드 관리가 용이하지 않다는 단점이 있습니다. 또 서버의 데이터에 액세스했을 때, 요청에 대한 성공, 실패, 오류 등 다양한 상황에 대한 로직을 구현하기 어렵습니다. 때문에 이번에는 본격적으로 `Mock` 라이브러리인 `MSW`를 사용해 API를 호출하고, 네트워크 수준의 `Mocking`을 진행해보고자 했습니다.

<br>

### MSW

>[!tip] Mock Service Worker (MSW) is an API mocking library for browser and Node.js. With MSW, you can intercept outgoing requests, observe them, and respond to them using mocked responses.

&nbsp;&nbsp;`MSW.js`는 `Mock Service Worker`의 약자로 `Service Worker`를 통해 `Request`를 가로채 MSW적절한 `Response`를 반환하게 끔 해주는 라이브러리입니다. 사실 네트워크 수준의 `Mocking`을 제공하기 위해서는 별도의 `Mock` 서버를 구축해 여러 요청에 대해 적절한 `Mock` 객체를 반환해주는 방법이 있습니다. 다만 이 방법은 서버를 구축해야한다는 관점에서 원치 않는 개발비용이 추가로 발생할 수 있습니다.

<br>

**MSW 구조**




<br>

**References**
- [MSW Docs](https://mswjs.io/docs/)
- [Mocking으로 생산성까지 챙기는 FE 개발](https://tech.kakao.com/2021/09/29/mocking-fe/)