
>[!tip] Recoil을 사용하면 _atoms_ (공유 상태)에서 _selectors_ (순수 함수)를 거쳐 React 컴포넌트로 내려가는 data-flow graph를 만들 수 있다. Atoms는 컴포넌트가 구독할 수 있는 상태의 단위다. Selectors는 atoms 상태 값을 동기 또는 비동기 방식을 통해 변환한다.

&nbsp;&nbsp; `Recoil`은 React에서 전역상태관리를 사용되는 라이브러리 중 하나입니다. 


<br>

### Recoil 구성요소

&nbsp;&nbsp;`Recoil`은 크게 전역상태인 `Atoms`과 `Atoms`나 다른 `Selectors`의 변경된 값을 상속받아 새로운 전역상태를 반환하는 순수함수인 `Selectors`로 구성되어있습니다.

**Atoms**

>[!tip] Atoms는 상태의 단위이며, 업데이트와 구독이 가능하다. atom이 업데이트되면 각각 구독된 컴포넌트는 새로운 값을 반영하여 다시 렌더링 된다.


```javascript
/* counterState.tsx */
export const counterState = atom<number>({
	key: "counterState",
	default: 0,
});
```

**Selectors**

>[!tip] **Selector**는 atoms나 다른 selectors를 입력으로 받아들이는 순수 함수(pure function)다.

<br>






&nbsp;&nbsp;지난 포스팅에서는 props drilling을 방지하고, 변수를 전역에서 관리하기 위해 리액트에서 내장으로 제공하는 `Context API`에 대해 다루었습니다. `Context API`의 경우에는 `Provider`에게 전역상태를 받아 사용하는 컴포넌트의 경우, 다른 컴포넌트에 의해 `Context`가 변경되었더라도 재렌더링되는 이슈가 있었습니다. 그렇