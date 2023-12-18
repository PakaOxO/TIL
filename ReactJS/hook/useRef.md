
&nbsp;&nbsp;Javascript로 HTML DOM에 접근하기 위해서는 id나 클래스 이름, 혹은 태그 이름을 사용합니다. 마찬가지로 React에서도 DOM에 접근하기 위한 Hook을 제공하는데 이것이 `useRef`입니다. 다음은 [리액트 공식문서](https://ko.legacy.reactjs.org/docs/hooks-reference.html#useref)에서 설명하고 있는 `useRef`입니다.

<br>

> 💡 useRef는 .current 프로퍼티로 전달된 인자(initialValue)로 초기화된 변경 가능한 ref 객체를 반환합니다. 반환된 객체는 컴포넌트의 전 생애주기를 통해 유지될 것입니다.

<br>

### useRef의 특징

- useRef는 `current` 프로퍼티에 해당 DOM을 가리키는 값을 가진 ref 객체를 반환합니다.

- useRef로 선언된 변수를 JSX 내부에서 `ref` 프로퍼티의 값으로 지정하면 ref 객체의 current 프로퍼티는 해당 DOM을 가리키게 됩니다.

- useRef의 값은 변경될 수 있지만 리렌더링을 유발하지는 않습니다. 만약 count를 표시하는 input의 값을 counterRef.current.value에 값을 직접 지정하여 수정했더라도 리렌더링이 발생하지 않습니다.

<br>

```javascript
/* 컴포넌트 내부 */
const nicknameInputRef = useRef();

return (
  <>
    <form>
      <label htmlFor="nickname">닉네임</label>
      <input id="nickname" type="text" ref={nicknameInputRef} />
    </form>
  </>
);
```

<br>
