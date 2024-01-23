
### Concurrency?

&nbsp;&nbsp;React의 `Concurrent Mode`에 대해 이해하기 위해서는 먼저 `동시성(Concurrency)`이 무엇인지 알아야 합니다. 결론부터 이야기하자면 `동시성(Concurrency)`은 사용자 경험을 위한 성질입니다. 기존의 React 렌더링은 `uninterrupted`한 특성을 가지고 있었습니다. 한번 렌더링이 시작되면 메인 스레드는 블록되고 렌더링이 완료되기 전까지 다른 작업은 실행될 수 없었죠. 이를 React 17에서는 [재조정(Reconciliation)](https://ko.legacy.reactjs.org/docs/reconciliation.html)이라 표현했습니다. React 18에서는 본격적으로 `Concurrent Mode`를 통해 `동시성(Concurrency)`을 지원합니다.

<br>

### React v18 : Concurrent Mode

&nbsp;&nbsp;`Concurrent Mode`는 React 17에서 실험적으로 도입되었고 React 18에 이르러 본격적으로 지원되는 사양입니다. `Concurrent Mode`의 가장 큰 특징은 `interrupted`입니다. 이제 `Concurrent Mode`에서 렌더링은 우선순위가 높은 작업이 끼어들면 중간에 일시중지 되었다 이후 다시 시작될 수도 있고, 아예 중단될 수도 있습니다. React는 전체 Render Tree의 평가가 끝나고 DOM이 변경이 완료된 후에야 트리거되는 방식으로 동작합니다. 때문에 렌더링이 중단되어도 일관성있는 UI를 보장할 수 있습니다.

React의 `Concurrent Mode`는 다음을 지원합니다.
- 메인 스레드를 블록하지 않습니다.
- 동시에 여러 작업들을 처리하고, 우선 순위에 따라 각 작업들 간 전환이 가능ㅎ

<br>

**References**
- [React Docs, useTransition](https://react.dev/reference/react/useTransition)
- [React 18 둘러보기](https://yrnana.dev/post/2022-04-12-react-18/)
- [What is React Concurrent Mode?](https://velog.io/@cadenzah/react-concurrent-mode)