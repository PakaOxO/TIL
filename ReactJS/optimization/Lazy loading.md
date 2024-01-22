
&nbsp;&nbsp;웹 서비스를 개발하면서 서비스의 성능은 서버의 부하를 줄여줄 뿐만 아니라 서비스를 이용하는 사용자 경험과 직결되어 있는 요소입니다. 개발하는 서비스의 규모가 증가함에 따라 최적화를 위해 `Lazy loading`을 사용하게 되었고, 사용하기에 앞서 간단하게 내용을 정리해보고자 합니다.

<br>

### Lazy loading이란?

&nbsp;&nbsp;`Lazy loading` 웹 페이지 로딩 시에 불필요한 자원(이미지, 스크립트 등)들을 로드하지 않음으로 서비스의 성능을 향상시키기 위한 기술입니다. 로드되지 않은 자원들은 이후 필요한 상황, 이벤트에 따라 로드되므로 초기 페이지의 로딩 시간을 줄이고, 네트워크 요청을 최소화할 수 있습니다.

&nbsp;&nbsp;`Lazy loading`은 주로 이미지와 같이 상대적으로 용량이 큰 파일을 부분적으로 렌더링하기 위해 사용되었습니다. 상품 이미지를 100개 보여주는 쇼핑몰 페이지가 있다면 스크롤을 내리기 전까진 화면에 바로 보여지는 상품 이미지만 로드하여 화면에 그리기 때문에 초기 렌더링 성능을 향상시킬 수 있었죠.

<br>

**Code splitting**

&nbsp;`Lazy loading`을 위해서는 `Code splitting`을 알아야 합니다. `Code splitting`은 크기가 큰 번들을 쪼개 `청크(Chunk)`로 나누고, 각 청크를 필요로 하는 상황  로드함으로 초기 렌더링 성능을 향상시킵니다.

&nbsp;&nbsp;아래 버튼을 클릭했을 때 상품 정보를 서버에 요청하는 간단한 React JSX 반환문입니다. 만약 컴포넌트 상단에서 `./apis/callData` 모듈을 import 했다면 버튼을 클릭하기 전에도 해당 모듈 리소스를 내려받아야 합니다. 하지만 아래 코드는 버튼을 클릭했을 때에 비로소 모듈을 로드하고, 요청을 보내기 시작합니다.

```javascript
return <>
	<h2>클릭하면 데이터를 조회합니다.</h2>
	<button onClick={() => {
		import("./apis/callData.js")
			.then((module) => {
				module.callItems();
			})
	}}>조회</button>
</>
```

<br>

### React.lazy

&nbsp;&nbsp;React는 컴포넌트의 성능 개선을 위해 `React.lazy` 메서드를 제공하며, 이를 통해 `Lazy loading`을 구현할 수 있습니다. `lazy()` 함수는 동적으로 `import`를 호출하는 함수를 콜백으로 받으며, `Promise`를 반환합니다. 이렇게 불러온 컴포넌트는 무조건 `Suspense`로 감싸야 합니다.

&nbsp;&nbsp;`Suspense`는 리액트에서 제공하는 컴포넌트로 `Lazy loading`할 컴포넌트의 준비가 완료되기 전까지 `fallback content`를 화면에 표시합니다. `Suspense`는 `fallback props`를 통해 React 컴포넌트를 값으로 넘겨받을 수 있습니다. `fallback content`로  [[SkeletonUI]] 컴포넌트 또는 로딩 컴포넌트를 넘겨주면 유저에게 보다 좋은 사용자 경험을 제공할 수 있습니다.

```javascript
import React, { Suspense } from 'react';
import SkeletonUI from "./SkeletonUI";

const LazyComponent = React.lazy(() => import("./OtherComponent"));

const MyComponent = () => {
	return (
		<div>
			<Suspense fallback={<SkeletonUI />}>
				<LazyComponent />
			</Suspense>
		</div>
	);
}
```

<br>

### Avoiding fallbacks

&nbsp;&nbsp;`fallback`을 사용하면 컴포넌트가 준비되기 전까지 `fallback content`를 보여줄 수 있지만 때로는 우


<br>

**References**
- [React Docs, Code Splitting & Lazy loading](https://legacy.reactjs.org/docs/code-splitting.html)