
>[!tip] Recoil을 사용하면 _atoms_ (공유 상태)에서 _selectors_ (순수 함수)를 거쳐 React 컴포넌트로 내려가는 data-flow graph를 만들 수 있다. Atoms는 컴포넌트가 구독할 수 있는 상태의 단위다. Selectors는 atoms 상태 값을 동기 또는 비동기 방식을 통해 변환한다.

&nbsp;&nbsp; `Recoil`은 React에서 전역상태관리를 사용되는 라이브러리 중 하나입니다. `Redux`에 비해 초기 설정이 간단하고, 지원하는 hook도 복잡하지 않아 개인적으로 전역상태관리 라이브러리 중에서는 사용하기 가장 편하다고 느끼고 있습니다. 이번 포스트에서는 `Recoil`의 특징과 사용할 수 있는 hook에 대해 가볍게 정리해보도록 하겠습니다.


<br>

### Recoil 구성요소

&nbsp;&nbsp;`Recoil`은 크게 전역상태인 `Atoms`과 `Atoms`나 다른 `Selectors`의 변경된 값을 상속받아 새로운 전역상태를 반환하는 순수함수인 `Selectors`로 구성되어있습니다.

<br>

**Atoms**

>[!tip] Atoms는 상태의 단위이며, 업데이트와 구독이 가능하다. atom이 업데이트되면 각각 구독된 컴포넌트는 새로운 값을 반영하여 다시 렌더링 된다.

&nbsp;&nbsp;React의 상태처럼 `Recoil`에서 제공하는 상태의 단위입니다. `Recoil`에서 제공하는 `atom`함수를 사용해 생성합니다. `atom`은 객체를 인자로 받는데 이 객체는 `atom`을 식별하기 위한 `key`와 이 `atom`의 기본 초기화 값인 `default`를 프로퍼티로 갖습니다.

&nbsp;&nbsp;`atoms`를 구독하기 위해서는 필요한 컴포넌트 내부에서 `useRecoilState` hook을 통해 사용하고자 하는 `atom`의 이름을 가지고 구독해 값을 참조하거나 업데이트할 수 있습니다.

```javascript
/* emailState.tsx */
export const emailState = atom<string>({
	key: "emailState",
	default: "",
});

/* myComponent */
const MyComponent = () => {
	const [email, setEmail] = useRecoilState<string>(emailState);

	return (
		<form>
			<label htmlFor="email">Email</label>
			<input id="email" type="text" value={text} />
		</form>
	);
}
```

<br>

**Selectors**

>[!tip] **Selector**는 atoms나 다른 selectors를 입력으로 받아들이는 순수 함수(pure function)다.

&nbsp;&nbsp;`selectors`는 순수 함수로 `atoms`나 또 다른 `selectors`의 변경이 이루어졌을 때 이를 기반으로 파생된 데이터(`derived state`)를 생성하기 위해 사용됩니다. `selectors` 역시 `atoms`와 마찬가지로 컴포넌트에 의해 구독될 수 있습니다. 함수형 개발 관점에서 `selectors`는 자신이 의존하는 상태를 추적하여 계산하고 이를 추상화 함으로써 코드의 관리를 용이하게 할 수 있습니다.

&nbsp;&nbsp;`selctor`는 `atom`과 유사하게 객체를 인자로 넘겨받으며, `selector`를 식별하기 위한 `key`와 `get`을 통해 의존할 상태값을 호출하고 반환합니다.

```javascript
/* emailState.tsx */
export const emailValidationState = atom<boolean>({
	key: "emailValidationState",
	get: ({ get }) => {
		const email = get(emailState);
		const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		
		return regex.match(email);
	}
});

/* myComponent */
const MyComponent = () => {
	const [email, setEmail] = useRecoilState<string>(emailState);
	const isEmailValid = useRecoilValue<boolean>(emailValidationState);

	return (
		<form>
			<label htmlFor="email">Email</label>
			<input id="email" type="text" value={text} />
			{ isEmailValid && <span>이메일 형식을 확인해주세요</span> }
		</form>
	);
}
```


<br>






&nbsp;&nbsp;지난 포스팅에서는 props drilling을 방지하고, 변수를 전역에서 관리하기 위해 리액트에서 내장으로 제공하는 `Context API`에 대해 다루었습니다. `Context API`의 경우에는 `Provider`에게 전역상태를 받아 사용하는 컴포넌트의 경우, 다른 컴포넌트에 의해 `Context`가 변경되었더라도 재렌더링되는 이슈가 있었습니다. 그렇

<br>

**References**
- [Recoil Docs](https://recoiljs.org/ko/docs/introduction/core-concepts)