
&nbsp;&nbsp;웹 서비스의 성능 최적화를 위해서는 먼저 [브라우저 렌더링](../렌더링/브라우저%20렌더링%201.md) 과정을 이해하고 있어야 합니다. `파서(Parser)`에 의해 `DOM`을 생성하는 과정은 브라우저의 메인 쓰레드를 이용하는데, CSS나 Javascript와 같은 `블로킹 요소`를 만나게 되면 블로킹 요소를 로드하고 파싱하기 위해 DOM의 생성은 중단됩니다. 이는 렌더링 성능에 영향을 줄 수 있습니다. 이번 포스트에서는 `Preload Scanner`가 이러한 블로킹 요소에 의한 렌더링 지연을 어떻게 해소할 수 있는지 살펴보도록 하겠습니다.

<br>

## Preload Scanner란?

&nbsp;&nbsp;`Preload Scanner`는 사용자에 의해 발생된 요청에 대한 리소스를 사전에 메모리, 혹은 캐시에 가져오기 위한 도구입니다. `Preload Scanner`를 사용하면 메인 쓰레드에서 이루어지는 작업 뒤에서 리소스 요청 처리를 수행할 수 있습니다.



<br>

**References**
- [MDN Docs, 브라우저는 어떻게 동작하는가](https://developer.mozilla.org/ko/docs/Web/Performance/How_browsers_work)
- [브라우저의 프리로드 스캐너와 파싱 동작의 이해](https://yceffort.kr/2022/06/preload-scanner)