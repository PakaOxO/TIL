
&nbsp;&nbsp;웹 서비스의 성능 최적화를 위해서는 먼저 [브라우저 렌더링](../렌더링/브라우저%20렌더링%201.md) 과정을 이해하고 있어야 합니다. `파서(Parser)`에 의해 `DOM`을 생성하는 과정은 브라우저의 메인 쓰레드를 이용하는데, CSS나 Javascript와 같은 `블로킹 요소`를 만나게 되면 블로킹 요소를 로드하고 파싱하기 위해 DOM의 생성은 중단됩니다. 이는 렌더링 성능에 영향을 줄 수 있습니다. 이번 포스트에서는 `Preload Scanner`가 이러한 블로킹 요소에 의한 렌더링 지연을 어떻게 해소할 수 있는지 살펴보도록 하겠습니다.

<br>

## Preload Scanner란?

&nbsp;&nbsp;`Preload Scanner`는 사용자에 의해 발생된 요청에 대한 리소스를 사전에 메모리, 혹은 캐시에 가져오기 위한 도구입니다. `Preload Scanner`를 사용하면 메인 쓰레드에서 이루어지는 작업 뒤에서 리소스 요청을 처리할 수 있습니다. 기존에 `블로킹 요소`는 다음과 같은 과정을 거쳐 적용됩니다.

<br>

### 블로킹 요소와 파서의 동작

1. `파서(parser)`는 블로킹 요소를 만나면 DOM 생성을 중단되고, 브라우저는 해당 리소스의 다운로드합니다.
2. 리소스의 다운로드가 완료되면 적절한 파서를 사용해 해당 리소스를 파싱하고 실행합니다.
3. DOM 파서는 다시 블로킹 요소 이후의 요소에 대해 파싱과 DOM 생성을 진행합니다.

<br>

&nbsp;&nbsp;이렇듯 블로킹 요소를 만나면 '로드'와 '파싱 및 실행'이 완료되기까지 DOM의 생성이 중단되며 이는 페이지 렌더링의 지연을 야기합니다. `Preload Scanner`는 블로킹 요소의 '로드'를 메인 쓰레드 뒤에서 미리 진행해 블로킹 요소에 의한 렌더링 지연을 줄일 수 있습니다.

<br>

### Preload Scanner 사용

&nbsp;&nbsp; `link` 요소의 `rel` 어트리뷰트에 `preload`를 사용하면 `Preload Scanner`의 대상이 됩니다. 가져올 정적 파일은 `href`에 파일의 유형은 `as` 어트리뷰트에 지정해줍니다. 이렇게 가져온 파일들은 메모리에 가지고 있다 필요할 때 즉시 사용할 수 있습니다. 다음의 코드는 `Preload Scanner`를 사용하기 위한 하나의 예시입니다.


```html
<!DOCTYPE html>  
<html lang="en">  
  <head>  
  <meta charset="UTF-8">  
  <meta name="viewport" content="width=device-width, initial-scale=1.0">  
  <title>Preload Example</title>  
  <!-- Preloading CSS file -->  
  <link rel="preload" href="styles.css" as="style">  
  <!-- Preloading JavaScript file -->  
  <link rel="preload" href="script.js" as="script">  
  <!-- Preloading Image -->  
  <link rel="preload" href="image.jpg" as="image">  
  <!-- Preloading Font -->
  <link rel="preload" href="fonts/Roboto-regular.woff2" as="font">
  <!-- Actual CSS file -->  
  <link rel="stylesheet" href="styles.css">  
  </head>  
  <body>  
    <h1>Hello, World!</h1>  
    <p>
      This is an example HTML file demonstrating preloading of resources.
    </p>  
    <!-- Actual JavaScript file -->  
    <script src="script.js"></script>  
    <!-- Actual Image -->  
    <img src="image.jpg" alt="Preloaded Image">  
  </body>  
</html>
```

<br>

>[!question] 로드가 완료되지 않은 정적 파일
>
>&nbsp;&nbsp;만약 `preload`로 파일을 읽어오려 했지만 로드가 완료되기 전에  `파서(parser)`가 이 파일을 사용하는 구문을 만나면 어떻게 될까요? `preload`를 사용하지 않았다면 블로킹 요소인 css나 javascript를 만났을 때 파서는 DOM 생성을 중단하고 이를 로드하고 파싱합니다. 마찬가지로 `preload`를 사용한 리소스에 대해서도 파서가 블로킹 요소를 만났을 때에는 DOM 생성이 중단되고 `Preload Scanner`에 의해 리소스가 로드되길 기다렸다 로드가 완료되면 파싱하고 적용합니다.

<br>

## Preload Scanner 리소스 힌트

&nbsp;&nbsp;`Preload Scanner`에 작업을 지시하는 구문에는 크게 3가지 힌트를 사용해 리소스에 대해 구체적인 지시를 내릴 수 있습니다.

<br>

### 1. Preload

&nbsp;&nbsp;`preload`는 서비스에서 해당 리소스의 우선 순위가 높아 리소스가 활용되기 이전에 우선적으로 가져와야 할 때 사용됩니다. 초기 페이지 렌더링 시에 필요한 크리티컬한 CSS나 JS, 이미지 등을 로드하기 위해 사용되며 `fetchpriority` 어트리뷰트를 통해 로드 우선순위를 지정할 수 있습니다.

&nbsp;&nbsp;`fetchpriority`는 크게 `auto(default)`, `low`, `high`를 값으로 가질 수 있습니다. 주로 이미지의 우선순위를 지정하기 위해 사용되며 사용자에게 우선적으로 보여져야 하는 사이트 배너, 주요 컨텐츠 등의 이미지에 활용될 수 있습니다. 반면 스크롤에 가려 한눈에 보이지 않는 이미지는 `fetchpriority="low"`로 상대적인 우선순위를 낮춰 사용자 경험에 기여할 수 있습니다.

```html
<link rel="preload" href="style.css" as="style">
<link rel="preload" href="main.js" as="script">
<link rel="preload" href="banner.webp" as="image" fetchpriority="high">
<link rel="preload" href="product.png" as="image" fetchpriority="low">
```

<br>

### 2. Prefetch

<br>

### 3. Preconnect

<br>

**References**
- [MDN Docs, 브라우저는 어떻게 동작하는가](https://developer.mozilla.org/ko/docs/Web/Performance/How_browsers_work)
- [브라우저의 프리로드 스캐너와 파싱 동작의 이해](https://yceffort.kr/2022/06/preload-scanner)