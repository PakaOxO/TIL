
&nbsp;&nbsp;`Next.js`와 `Typescript`로 토이 프로젝트를 진행하다 한가지 문제에 직면했습니다. 이 프로젝트는 스타일링을 `tailwindcss`를 사용하고 있는데 커스텀 스타일을 `taliwind.config.ts`에 지정하는 과정에서 `plugin` 모듈을 가져와 사용하는데 다음과 같은 오류 문구가 발생했습니다.

```cmd
바인딩 요소 'addBase'에 암시적으로 'any' 형식이 있습니다.
```

&nbsp;&nbsp;타입에 문제가 있다니 일단 타입스크립트 문제라 생각하고, 한참을 삽질하다 스택오버플로우에서 [다음](https://stackoverflow.com/questions/76502241/require-return-is-not-typed-properly-is-any) 게시글을 보게 되었습니다.

<br>

### Transcompiling

```javascript
// the modern ESModule import syntax:
import plugin from 'tailwindcss/plugin'
```

&nbsp;&nbsp;프로젝트를 진행하면서 기계적으로 모듈을 가져다 쓰다보니 간과한 사실이 있었습니다. `React`든 `Typescript`든 브라우저에서 동작하기 위해서는 `JS Module`로 변환하는 과정을 거쳐야 하고 그 과정이 `Transcompiling`에