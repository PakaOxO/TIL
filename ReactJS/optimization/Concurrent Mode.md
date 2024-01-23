
### Concurrency?

&nbsp;&nbsp;React의 `Concurrent Mode`에 대해 이해하기 위해서는 먼저 `동시성(Concurrency)`이 무엇인지 알아야 합니다. 결론부터 이야기하자면 `동시성(Concurrency)`은 사용자 경험을 위한 성질입니다. 기존의 React 렌더링은 `uninterrupted`한 특성을 가지고 있었습니다. 한번 렌더링이 시작되면 메인 스레드는 블록되고 렌더링이 완료되기 전까지 다른 작업은 실행될 수 없었죠. 이를 React 17에서는 [재조정(Reconciliation)](https://ko.legacy.reactjs.org/docs/reconciliation.html)이라 표현했습니다. React 18에서는 본격적으로 `Concurrent Mode`를 통해 `동시성(Concurrency)`을 지원합니다.

<br>

### React v18 : Concurrent Mode

&nbsp;&nbsp;`Concurrent Mode`는 React 17에서 실험적으로 도입되었고 React 18에 이르러 본격적으로 지원되는 사양입니다. `Concurrent Mode`의 가장 큰 특징은 `interrupted`입니다. 이제 `Concurrent Mode`에서 렌더링은 우선순위가 높은 작업이 끼어들면 중간에 일시중지 되었다 이후 다시 시작될 수도 있고, 아예 중단될 수도 있습니다. React는 전체 Render Tree의 평가가 끝나고 DOM이 변경이 완료된 후에야 트리거되는 방식으로 동작합니다. 때문에 렌더링이 중단되어도 일관성있는 UI를 보장할 수 있습니다.

&nbsp;&nbsp;기존의 React 프로젝트에서는 종종 데이터 fetch가 완료되기 전까지 Spinner를 확인할 수 있었습니다. Spinner는 사용자가 화면 로딩을 기다린다는 느낌을 주어 사용자 경험을 향상시킬 수 있지만, 반대로 과한 Spinner는 오히려 사용자 경험에 악영향을 끼칠 수 있습니다. 빠르게 fetch가 완료되거나 한번에 여러 정보를 가져올 때에는 불필요한 Spinner를 자주 보게 된다는 느낌을 지울 수 없습니다.

&nbsp;&nbsp;`Concurrent Mode`에서는 처음 화면 렌더링 시에는 Spinner를 보여주지만 Spinner가 원치 않는 상황(탭 전환)에서는 기존의 UI를 보여주는 것이 가능합니다. 이제부터 `Concurrent Mode`를 위해 React에서 제공하는 기능들에 대해 살펴보겠습니다.

<br>

### useTransition

&nbsp;&nbsp;`useTransition`은 `isPending`과 `startTransition`을 담은 배열을 반환하는 React hook으로 `Suspense`와 함께 사용해 불필

<br>

**References**
- [React Docs, useTransition](https://react.dev/reference/react/useTransition)
- [React 18 둘러보기](https://yrnana.dev/post/2022-04-12-react-18/)
- [What is React Concurrent Mode?](https://velog.io/@cadenzah/react-concurrent-mode)