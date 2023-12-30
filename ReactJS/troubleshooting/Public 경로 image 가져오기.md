
&nbsp;&nbsp;이전에 리액트 프로젝트를 하면서 `public` 경로에서 이미지를 가져올 때, `public/assets` 경로를 만들어 사용했었지만, 최근  토이 프로젝트를 하나 진행하면서 이 방법이 안되는 것을 확인할 수 있었습니다. 찾아보니 `CRA(Create React App) 4.0.0` 이후로는 이 방법을 사용할 수 없다고 합니다... 다른 방법을 찾기 위해 구글링을 하던 중, 저와 같은 고민을 하고 [블로그에 정리해두신 분](https://think0wise.tistory.com/21)이 계셔서 도움을 받았습니다.

<br>

### 환경변수

&nbsp;&nbsp;`public` 경로는 `PUBLIC_URL`이라는 환경변수를 통해 접근할 수 있었습니다. HTML에서 접근하느냐, Javascript 코드에서 접근하느냐에 따라 다음과 같은 변수를 사용할 수 있습니다.

**HTML**
```html
<img src="%PUBLIC_URL%/images/alpaca.png" alt="알파카 로고" />
```

**Javascript**
```javascript
<img src={`${process.env.PUBLIC_URL}/images/alpaca.png`} />
```

<br>

**References**
- [CreativeLee님 블로그](https://think0wise.tistory.com/21)
- [Create React App 공식문서](https://create-react-app.dev/docs/using-the-public-folder/)