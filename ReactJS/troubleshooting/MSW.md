
&nbsp;&nbsp;개발 프로젝트를 진행하면서 `Mocking`의 필요성을 느끼게 되어 대표적인 `Mock` 라이브러리 중 하나인 `MSW`의 도입을 프로젝트 팀에 권유해보려고 합니다. 이에 앞서 관련한 내용을 이번 포스팅을 통해 간단하게 다뤄보도록 하겠습니다.

<br>

### 도입 계기

&nbsp;&nbsp;몇 번의 서비스 개발을 진행하면서 느낀 점은 프론트엔드와 백엔드의 개발 진척상황 차이와 서비스의 특성상 불가피하게 프론트엔드의 개발 프로세스가 백엔드에 의존하게 된다는 점입니다. 프론트엔드는 서버로부터 받은 데이터를 토대로 화면을 그려야 하는데 이전에 프로젝트를 진행했을 때에는 만약 해당 API가 아직 개발이 완료되지 않았다면, 비즈니스 로직 사이에 더미 데이터(Mock)를 만들어 화면에 표시하곤 했습니다.

**이상적인 개발 프로세스**

&nbsp;&nbsp;아래 이미지는 카카오 기술 블로그에 한 개발자 분이 `Mocking`과 관련한 포스팅에서 참조했습니다. 그림과 같이 이상적인 개발은 백엔드의 개발이 완료되고, 프론트엔드가 서버 사이드로부터 API를 통해 데이터를 받아 렌더링하는 것이 좋습니다. 하지만 실제 개발은 백엔드와 프론트엔드가 병렬적으로 진행되는 경우가 많죠.

![ideal dev process | 600](../images/ideal_devProcess.png)

<br>

&nbsp;&nbsp;더미 데이터를 만들어 `Mocking`하는 방식은 빠르게 개발을 진행하는데에는 사실 크게 무리가 없습니다. 다만, 테스트를 위한 코드가 비즈니스 로직 여기저기에 포함되게 되고, 코드 관리가 용이하지 않다는 단점이 있습니다. 또 서버의 데이터에 액세스했을 때, 요청에 대한 성공, 실패, 오류 등 다양한 상황에 대한 로직을 구현하기 어렵습니다. 때문에 이번에는 본격적으로 `Mock` 라이브러리인 `MSW`를 사용해 API를 호출하고, 네트워크 수준의 `Mocking`을 진행해보고자 했습니다.

<br>

### MSW

>[!tip] Mock Service Worker (MSW) is an API mocking library for browser and Node.js. With MSW, you can intercept outgoing requests, observe them, and respond to them using mocked responses.

&nbsp;&nbsp;`MSW.js`는 `Mock Service Worker`의 약자로 `Service Worker`를 통해 `Request`를 가로채 MSW적절한 `Response`를 반환하게 끔 해주는 라이브러리입니다. 사실 네트워크 수준의 `Mocking`을 제공하기 위해서는 별도의 `Mock` 서버를 구축해 여러 요청에 대해 적절한 `Mock` 객체를 반환해주는 방법이 있습니다. 다만 이 방법은 서버를 구축해야한다는 관점에서 원치 않는 개발비용이 추가로 발생할 수 있습니다.

<br>

**MSW 구조, Feat. Service Worker**

![MSW Architecture | ](../images/msw_architecture.webp)

&nbsp;&nbsp;`Service Worker`는 `MSW`에서 핵심 역할을 수행하는 기술입니다. `Service Worker`는 메인 스레드에서 벗어나 백그라운드 상에서 특정한 동작을 수행하는 프로그램입니다. 이전에 `Web Push`를 구현하면서 서버가 `Push`한 정보를 받아 `포어그라운드` 서비스로 전달하기 위해 `Service Worker`를 사용했었습니다. `MSW`에서도 메인 애플리케이션과 서버 사이에서 클라이언트의 요청을 가로채 `MSW` 프로그램에 전달하는 역할을 수행합니다.

<br>

### MSW 프로젝트 적용

**1. MSW 추가**

```bash
npm install msw --save-dev
or
yarn add msw --dev
```

&nbsp;&nbsp;먼저 `MSW` 라이브러리를 프로젝트에 추가해줍니다.

<br>

**2. `Service Worker`를 Public 경로에 추가

```bash
npx msw init <PUBLIC_DIR> --save
```

&nbsp;&nbsp;위 명령어를 실행하면 `PUBLIC` 경로에 `mockServiceWorker.js`가 추가됩니다.

<br>

**3. 서비스 워커 등록**

&nbsp;&nbsp;`src/mock/`  경로를 별도로 만들어 다음 파일들을 추가해주었습니다. `browser.js`는 `handler.js`가 가진 요청처리 로직이 담긴 배열을 받아 `Service Worker` 에 넘겨줍니다. `http`가 갖는 `RestAPI` 메서드는 첫 번째 인자로 요청을 가로챌 API 주소를, 두 번째 인자는 클라이언트 비즈니스의 요청에 해당하는 `Request` 객체와 요청에 담긴 `params`와 `cookie` 객체를 받습니다.

```javascript
/* src/mock/browser.js */
const { setupWorker } from "msw";
const handlers from "./handlers";
export const worker = setupWorker(...handlers);

/* src/mock/handlers.js */
import { HttpResponse, http } from "msw";
// 처리할 요청들을 배열로 가지고 있습니다
const handlers = [
	http.get("/api/characters", ({ request, params, cookies }) => {
		const url = new URL(request.url); // queryString 받는 방법
		const name = url.getParams.get("name");
		if (code) {
			return HttpResponse.json({ error: "Bad Request" }, { status: 400 });
		}
		
		return HttpResponse.json({
			/* 반환할 객체 정의 */
		});
	}),
	http.post("/api/characters"), async ({ request, params, cookies }) => {
		const postData = await request.json(); // 클라이언트가 보낸 데이터
		
		/* Post 요청 처리 */
		return HttpResponse.json({
			message: "POST 요청이 성공적으로 처리되었습니다.",
		});
	}),
];

export default handlers;
```

<br>

>[!caution] Post시 404 Not Found
>
>&nbsp;&nbsp;아! 삽질을 한번 했습니다. http.post로 GET과  동일한 URI로 들어오는 POST 요청을 인터셉트까진 성공했지만 브라우저에서 `404 Not Found` 오류가 발생하는 것을 확인해 원인을 찾았습니다만... http.post 구문에서 인터셉트를 한뒤 별도의 응답(`HttpResponse`)을 반환하지 않아 발생하는 현상이었습니다. 위 코드를 보시면 post 인터셉터의 마지막에 `HttpResponse .json()`을 반환해주었습니다.

<br>

**4. 서비스 워커 실행**

&nbsp;&nbsp;이제 다왔습니다. `index.tsx` 파일로 이동해 설정이 끝난 `Service Worker`를 웹 서비스 실행시에 `start`하는 코드를 추가해줍니다. 환경변수의 값을 확인해 테스트 환경일 경우에만 `Service Worker`가 실행될 수 있도록 해주었습니다.

```javascript
/* index.tsx */
const enableMocking = async () => {
	// NODE_ENV 환경변수의 값을 확인해 개발환경이 아니면 서비스워커를 실행하지 않습니다.
	if (process.env.NODE_ENV !== "development") return;
	const { worker } = await import("./mocks/browser");
	
	return worker.start();
};

enableMocking().then(() => {
	const root = ReactDOM.createRoot(document.getElementById("root") as HTMLElement);
	root.render(<App />);
});
```

<br>

### 실행 결과

![mocking enabled | 300](../images/mocking_enabled.png)

&nbsp;&nbsp;프로젝트를 실행한 뒤, 브라우저 콘솔을 확인해 성공적으로 `Service Worker`가 실행되었다면 위와 같은 메시지를 확인할 수 있습니다. 아래는 GET 요청을 보냈을 때 `MSW`에서 보내주는 응답 결과 예시입니다. 위에 작성된 `Handler` 코드를 살펴보면 `querystring`으로 `code`값이 있을 경우 `400` 응답을 반환하도록 했기 때문에 `400 (Bad Reqeust)`를 받았습니다.

```javascript
fetch("/api/characters?code=400")
	.then((res) => {
		return res.json();
	})
	.then((data) => {
		console.log("Response data: ", data);
	})
	.catch((error) => {
		console.error("Request error: ", error);
	});

// 응답 결과: GET http://localhost:3000/api/characters?code=400 400 (Bad Request)
```

<br>

### 후기

&nbsp;&nbsp;이번 프로젝트는 `Mock`을 본격적으로 도입해 사용한 첫 번째 프로젝트가 될 것 같습니다. 한동안은 `MSW`를 테스트하기 위해 만든 토이 프로젝트에서 `Service Worker`에 여러가지 상황에 맞춰 적절한 응답을 반환해보면서 연습을 좀 해보려고 합니다. 아직 `MSW`에 의문이 몇 가지 남아있는데 `Handler`의 두 번째 파라미터인 함수가 가지는 인자 중 `params`에 대해 공식문서에서는 `Request`의 `querystring`와 `cookies`에 대해서는 테스트 코드를 돌려보면서 기능을 확인해보아야 할 것 같습니다.

<br>

**References**
- [MSW Docs](https://mswjs.io/docs/)
- [Mocking으로 생산성까지 챙기는 FE 개발](https://tech.kakao.com/2021/09/29/mocking-fe/)