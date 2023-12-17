
&nbsp;&nbsp;토이 프로젝트를 하나 만들던 중 사이트 메인 페이지 내 빈 공간을 활용하기 위해 소개 문구를 넣어보려고 했습니다. 문구를 디자인하다 보니 `개행문자(\\n)`가 제대로 적용되지 않고, 그대로 출력되는 것을 확인할 수 있습니다. 이는 `JSX`가 컴파일 단계에서 입력된 내용을 그대로 문자열로 `parsing`하기 때문입니다. 그렇다면 `JSX`내에서 문자열을 개행하기 위해서는 어떤 방법을 사용할 수 있을 지 알아보겠습니다.

<br>

### HTML: br Tag

&nbsp;&nbsp;첫 번째 방법은 문자열을 배열로 받아 배열 사이에 줄바꿈 태그인 `<br>` 을 추가하는 방법입니다. 만일 문자열을 `개행문자(\n)`가 포함된 하나의 문자열로 받았다면 `Array.prototype.map` 내부 메서드를 활용하여 배열로 변환합니다.

```javascript
const str = "동해물과 백두산이\n마르고 닳도록.";

return (
	<div>
		{str.split("\n").map((line, idx) => (
			<React.Framgment>
				{ line }
				<br />
			</React.Fragment>
		))}
	</div>
)
```

<br>

### CSS: whitespace property

&nbsp;&nbsp;또 다른 방법은 CSS의 `whitespace` 속성에 `pre-wrap`이나 `pre-line`값을 사용하는 것입니다. 두 속성은 모두 `개행문자(\n)` 인식해 줄 바꿈을 하며, 박스의 크기보다 많은 문자열을 출력할 경우 자동으로 줄 바꿈을 실시합니다. 차이점은 전자는 연속 공백을 유지하며, 후자는 연속 공백을 하나의 공백으로 인식합니다.

```javascript
const str = "동해물과 백두산이\n마르고 닳도록.";

return (
	<div style={{whitespace: "pre-wrap"}}>
		{str}
	</div>
)

```

<br>

**Reference**
- [MDN CSS-whitespace](https://developer.mozilla.org/ko/docs/Web/CSS/white-space)