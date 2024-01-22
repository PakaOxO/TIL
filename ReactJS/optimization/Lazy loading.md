
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

&nbsp;&nbsp;`fallback`을 사용하면 컴포넌트가 준비되기 전까지 `fallback content`를 보여줄 수 있다는 점은 장점이면서도 때로는 원치 않은 동작이 될 수 있습니다. 다음 예시를 살펴보겠습니다.

&nbsp;&nbsp;현재 우리 앱은 `Tabs` 컴포넌트를 통해 `Photos`와 `Comments` 컴포넌트간 전환이 가능합니다. 두 컴포넌트는 `Suspense` 컴포넌트에 의해 감싸져 있으며 준비가 완료되기 전엔 `fallback content`인 `Glimmer` 컴포넌트가 보여집니다.

```javascript
import React, { Suspense } from 'react';
import Tabs from './Tabs';
import Glimmer from './Glimmer';

const Comments = React.lazy(() => import('./Comments'));
const Photos = React.lazy(() => import('./Photos'));

const MyComponent = () => {
  const [tab, setTab] = React.useState('photos');
  
  function handleTabSelect(tab) {
    setTab(tab);
  };

  return (
    <div>
      <Tabs onTabSelect={handleTabSelect} />
      <Suspense fallback={<Glimmer />}>
        {tab === 'photos' ? <Photos /> : <Comments />}
      </Suspense>
    </div>
  );
}
```

<br>

**startTransition**

&nbsp;&nbsp;하지만 무조건 `fallback`을 표시하기 보단 이전에 띄워져 있는 컴포넌트를 보여주는 편이 좋을 수 있습니다. `Photos` 컴포넌트가 이미 렌더링되어 있는 상황에서 `Comments` 컴포넌트로 전환이 이루어질 때 굳이 `Glimmer`를 표시하는 것보단 `Photos`를 보여주다 `Comments`로 넘어가는 것이 자연스러울 수도 있으니깐요. React에서 제공하는 `startTransition`을 사용하면 불필요한 `fallback`을 피할 수 있습니다.

&nbsp;&nbsp;`Photos` 컴포넌트는 초기 렌더링 시에는 `fallback content`인 `Glimmer` 컴포넌트를 보여주지만, 이후 탭을 클릭해 컴포넌트를 전환할 때는 `startTransition`를 통해 `state`를 변경하고 재렌더링이 이루어지므로 `fallback content`는 표시되지 않고 old component인 `Photos` 또는 `Comments`가 보여집니다.

```javascript
import React, { Suspense, useTransition } from 'react';
import Tabs from './Tabs';
import Glimmer from './Glimmer';

const Comments = React.lazy(() => import('./Comments'));
const Photos = React.lazy(() => import('./Photos'));

const MyComponent = () => {
	const [isPending, startTransition] = useTransition();
  const [tab, setTab] = React.useState('photos');
  
  function handleTabSelect(tab) {
	startTransition(() => {
	  setTab(tab);
	});
  };

  return (
    <div>
      <Tabs onTabSelect={handleTabSelect} />
      <Suspense fallback={<Glimmer />}>
        {tab === 'photos' ? <Photos /> : <Comments />}
      </Suspense>
    </div>
  );
}
```

<br>

**Concurrent Mode**

&nbsp;&nbsp;React는 `Concurrent Mode`를 지원하기 위해 `Suspense` 컴포넌트를 포함해,  `useTransition` hook과 `startTransition`을 제공합니다. `useTransition`은 `isPending`과 `startTranstion`이 담긴 배열을 반환하는데, 여기서 반환되는 `startTransition`은 `React.startTransition`과 매우 비슷하게 동작합니다. `startTransition`의 동작에 대한 자세한 설명은 [[Concurrent Mode]]에 대해 이야기하면서 다뤄보도록 하겠습니다.

>[!tip] `useTransition` vs `startTransition`
>
> &nbsp;&nbsp; `startTransition` is very similar to [`useTransition`](https://react.dev/reference/react/useTransition), except that it does not provide the `isPending` flag to track whether a transition is ongoing. You can call `startTransition` when `useTransition` is not available. For example, `startTransition` works outside components, such as from a data library. - React Docs.

<br>

### fallback content 디자인

&nbsp;&nbsp;`fallback content`는 상황에 따라 다양하게 보여줄 수 있습니다. 만약 `Video` 컴포넌트와 `Comments` 컴포넌트 모두가 준비되기 전까지 하나의 `fallback content`를 보여주고 싶다면 하나의 `Suspense`으로, 두 컴포넌트가 각자 준비되기 전까지 별개의 `fallback content`를 보여주고 싶다면 두 개의 각각의 컴포넌트를 `Suspense`로 감싸줍니다.

```javascript
import React, { Suspense } from 'react';
const Spinner from "./Spinner";
const SkeletonComments from "./SkeletonComments";
const Video = React.lazy(() => import("./Video"));
const Comments = React.lazy(() => import("./Comments"));

/* 하나의 fallback 사용 */
const MyComponent = () => {
  return (
    <div>
      <Suspense fallback={<Spinner />}>
	      <Video />
	      <Comments />
      </Suspense>
    </div>
  );
}

/* 별개의 fallback 사용 */
const MyComponent = () => {
	return (
		<div>
			<Suspense fallback={<Spinner />}>
				<Video />
			</Suspense>
			<Suspense fallback={<SkeletonComments />}>
				<Comments />
			</Suspense>
		</div>
	);
}
```

<br>

**References**
- [React Docs, Code Splitting & Lazy loading](https://legacy.reactjs.org/docs/code-splitting.html)
- [React Docs, startTransition](https://react.dev/reference/react/startTransition)
- [React-suspense와 lazy loading을 통한 성능 최적화](https://www.deviantceblog.com/react-suspense%EC%99%80-lazy-loading%EC%9D%84-%ED%86%B5%ED%95%9C-%EC%84%B1%EB%8A%A5-%EC%B5%9C%EC%A0%81%ED%99%94/)