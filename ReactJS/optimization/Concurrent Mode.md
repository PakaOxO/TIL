
### Concurrency?

&nbsp;&nbsp;React의 `Concurrent Mode`에 대해 이해하기 위해서는 먼저 `동시성(Concurrency)`이 무엇인지 알아야 합니다. 결론부터 이야기하자면 `동시성(Concurrency)`은 사용자 경험을 위한 성질입니다. 기존의 React 렌더링은 `uninterrupted`한 특성을 가지고 있었습니다. 한번 렌더링이 시작되면 메인 스레드는 블록되고 렌더링이 완료되기 전까지 다른 작업은 실행될 수 없었죠. 이를 React 17에서는 [재조정(Reconciliation)](https://ko.legacy.reactjs.org/docs/reconciliation.html)이라 표현했습니다. React 18에서는 본격적으로 `Concurrent Mode`를 통해 `동시성(Concurrency)`을 지원합니다.

<br>

### React v18 : Concurrent Mode

**Interruptable Render**

&nbsp;&nbsp;`Concurrent Mode`는 React 17에서 실험적으로 도입되었고 React 18에 이르러 본격적으로 지원되는 사양입니다. `Concurrent Mode`의 가장 큰 특징은 `interrupted`입니다. 이제 `Concurrent Mode`에서 렌더링은 우선순위가 높은 작업이 끼어들면 중간에 일시중지 되었다 이후 다시 시작될 수도 있고, 아예 중단될 수도 있습니다. React는 전체 Render Tree의 평가가 끝나고 DOM이 변경이 완료된 후에야 트리거되는 방식으로 동작합니다. 때문에 렌더링이 중단되어도 일관성있는 UI를 보장할 수 있습니다.

<br>

**Avoid Unwanted Spinner**

&nbsp;&nbsp;기존의 React 프로젝트에서는 종종 데이터 fetch가 완료되기 전까지 Spinner를 확인할 수 있었습니다. Spinner는 사용자가 화면 로딩을 기다린다는 느낌을 주어 사용자 경험을 향상시킬 수 있지만, 반대로 과한 Spinner는 오히려 사용자 경험에 악영향을 끼칠 수 있습니다. 빠르게 fetch가 완료되거나 한번에 여러 정보를 가져올 때에는 불필요한 Spinner를 자주 보게 된다는 느낌을 지울 수 없습니다.

&nbsp;&nbsp;`Concurrent Mode`에서는 처음 화면 렌더링 시에는 Spinner를 보여주지만 Spinner가 원치 않는 상황(탭 전환)에서는 기존의 UI를 보여주는 것이 가능합니다. 이제부터 `Concurrent Mode`를 위해 React에서 제공하는 기능들에 대해 살펴보겠습니다.

<br>

### startTransition

**Perspective 01: Avoid Unwanted Spinner**

&nbsp;&nbsp;`startTransition`은 React가 제공하는 함수로 `Concurrent Mode`를 지원합니다. `startTransition`은 낮은 우선순위로 실행할 콜백함수를 받는데, 상태 변경을 위한 `useState` hook의 `setState`가 `startTransition` 콜백함수 내에서 실행되면 상태 변경의 우선순위가 현재 렌더링이 아닌 다음 렌더링으로 밀려 업데이트 됩니다.

&nbsp;&nbsp;또한 `startTransition`을 사용하면 DOM의 변동사항이 실제 화면에 적용되는 것은 필요한 모든 데이터가 준비될 때까지 기다리게 됩니다. 때문에 사용자는 변경된 화면을 보기 전까진 이전의 UI를 바라보게 되죠. 이를 활용하면 앞서 이야기했듯 원치 않는 Spinner를 방지할 수 있습니다. 아래 코드에서는 `startTransition` 내부에서 tab 상태를 변경합니다. 때문에 `setTab`으로 호출되어 탭이 이동할 때에는 상태 변경이 지연되어 다음 탭 컴포넌트 데이터가 모두 준비될 때까지 Spinner가 아닌 이전의 탭(`Photos` 또는 `Comments`)을 보게되는 것이지요.

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
      <Suspense fallback={<Spinner />}>
        {tab === 'photos' ? <Photos /> : <Comments />}
      </Suspense>
    </div>
  );
}
```

<br>

**Perspective 02: Interruptable Render**

&nbsp;&nbsp;`startTransition`을 통해 우선순위가 밀린 동작은 입력, 클릭과 같은 직접적인 상호작용을 포함한 긴급한 업데이트에 의해 `interrupt`될 수 있습니다. [React 공식문서](https://react.dev/reference/react/useTransition)의 `TabButton` 예제를 보면 `startTransition`없이 `setTab` 상태변경이 이루어지면 렌더링 대기시간이 긴 `Posts` 탭을 클릭한 직후, 다른 탭의 클릭이 블록되는 것을 확인할 수 있습니다. 하지만 `startTransition` 내부에 `setTab`으로 상태변경을 했을 경우, `Posts` 컴포넌트의 렌더링은 클릭 이벤트에 의해 `interrupt`되어 중다

```javascript
import { useState, useTransition } from 'react';
import TabButton from './TabButton.js';
import AboutTab from './AboutTab.js';
import PostsTab from './PostsTab.js';
import ContactTab from './ContactTab.js';

export default function TabContainer() {
  const [isPending, startTransition] = useTransition();
  const [tab, setTab] = useState('about');

  function selectTab(nextTab) {
    startTransition(() => {
      setTab(nextTab);
    });
  }

  return (
    <>
      <TabButton
        isActive={tab === 'about'}
        onClick={() => selectTab('about')}
      >
        About
      </TabButton>
      <TabButton
        isActive={tab === 'posts'}
        onClick={() => selectTab('posts')}
      >
        Posts (slow)
      </TabButton>
      <TabButton
        isActive={tab === 'contact'}
        onClick={() => selectTab('contact')}
      >
        Contact
      </TabButton>
      <hr />
      {tab === 'about' && <AboutTab />}
      {tab === 'posts' && <PostsTab />}
      {tab === 'contact' && <ContactTab />}
    </>
  );
}
```

<br>

> [!tip] **useTransition**
>
> &nbsp;&nbsp;`useTransition` hook은 지연상태인지 여부를 표시하는 불리언 값인 `isPending`과 `startTransition`을 배열에 담아 반환합니다. `startTransition`에 의해 우선순위가 밀린 작업이 완료되기 전까지 `isPending`은 `true`값을 가지므로 이를 활용해 대기시간동안 표시할 `JSX`를 반환할 수 있습니다.

<br>

**References**
- [React Docs, useTransition](https://react.dev/reference/react/useTransition)
- [React 18 둘러보기](https://yrnana.dev/post/2022-04-12-react-18/)
- [What is React Concurrent Mode?](https://velog.io/@cadenzah/react-concurrent-mode)