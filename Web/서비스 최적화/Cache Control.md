
&nbsp;&nbsp;개발자들은 사용자들이 서비스를 이용하는데 있어서 속도 등의 성능 이슈로 인한 불편함을 최소화하기 위해 다양한 노력들을 하는데 이번 포스팅에서는 `Web Cache`를 통한 성능 최적화 방식에 대해 살펴보겠습니다.

&nbsp;&nbsp;클라이언트가 요청하는 자원(Resource)는 보통 HTML, CSS, JS, Images 등의 정적자원입니다. `Web Cache`를 활용하면 최초 요청시에 서버로부터 받은 자원의 복사본을 저장하고, 이후 동일한 자원(URL)에 대한 요청이 발생했을 경우, 저장된 자원(Cache)을 활용하여 더욱 빠르게 서비스를 제공할 수 있습니다.

<br>

**캐싱 데이터 유효성 확인: "만기일 메커니즘"**

&nbsp;&nbsp;캐싱된 데이터가 만료되지 않았다면 이후의 동일한 요청에 대해서는 해당 자원을 재요청하지 않지만(설정에 따라 다를 수 있습니다.) 만료되었다면 이 데이터가 여전히 유효한지 확인할 필요가 있습니다. 캐싱된 데이터가 여전히 유효한지 확인하는 메커니즘은 다음과 같습니다.

1. 원 서버는 요청에 대한 응답 헤더에 `Last-modified`와 `ETag`, `Cache-Control` 필드에 값을 넣어 반환하며, 응답의 결과는 캐싱됩니다.
2. 이후 사용자(클라이언트 or 프록시 서버)는 동일한 요청에 대해 `If-modified-since` 필드에는 `Last-modified`를, `If-none-match`에는 `ETag`를 넣어 요청을 보내고 서버는 두 필드(혹은 하나만)를 확인해 캐싱된 데이터에 대한 유효성 검사를 실시합니다.

<br>

>[!tip] **ETag(Entity Tag)**
>
>&nbsp;&nbsp;`ETag`는 원본 서버가 자원(Resource)를 식별하기 위해 각 자원에 부여하는 고유한 식별번호입니다. `ETag`는 임의의 문자열로 구성되어 있으며, 구성방법은 원본 서버의 정책에 따라 달라질 수 있습니다. 캐시 서버는 원본 서버에 캐싱된 데이터가 원본 서버에서 만료되어 갱신되어야 하는지 확인하기 위해 `ETag`를 사용합니다.


<br>

### Web Cache: Server Cache

![Cache Hit & Miss|400](CacheHit&Miss.png)

&nbsp;&nbsp;`Web Cache`의 방법 중 하나는 서버 측에서 캐싱을 통해 클라이언트의 요청에 대한 응답을 빠르게 제공하는 것입니다. 서버는 클라이언트로부터 자주 요청받을 것 같은 내용을 판단하고 필요한 데이터를 메모리/디스크(버퍼)에 저장합니다. 만약 버퍼에 저장된 내용을 클라이언트가 요청했다면 `Cache hit`를 발생시키고, 클라이언트에게 응답을 보냅니다. 반면에 버퍼에 저장되지 않은 내용을 클라이언트가 요청했다면 `Cache miss`가 발생하고, 웹 서버로부터 응답에 대한 결과를 읽어와 클라이언트에 제공합니다. 

`Cache hit`는 캐시에서 데이터를 효과적으로 활용하여 응답을 생성하는 경우이며, `Cache miss`는 캐시에 데이터가 없어 웹 서버에서 데이터를 가져와야 하는 상황을 나타냅니다.

<br>

### Web Cache: Browser Cache

&nbsp;&nbsp;`Browser Cache`는 요청에 대한 응답을 캐싱하여 이후의 요청에 대해 이미 유효한 데이터를 가지고 있다면 캐싱된 데이터를 활용하여 불필요한 트래픽을 방지하며, 빠르게 화면을 로드할 수 있는 방법입니다. HTTP 환경에서 `Browser Cache`를 활용하기 위해서는 응답 헤더를 활용할 수 있으며 다음과 같은 방법들이 존재합니다.

<br>

**Expire**

![expires http header|600](expires-http-header-2.png)

&nbsp;&nbsp;`HTTP/1.0`에서는 캐시를 제어하기 위한 명시적인 기술이 없었으나, `Expire` 헤더를 사용해 웹 서버 데이터의 유효기간을 지정할 수 있었습니다. `Expire` 필드는 데이터의 유효한 날짜를 날짜/시간의 형태의 값으로 가지고 있으며, 만약 유효하지 않은 날짜/시간이라면 해당 데이터는 "신선하지 못한"(만료된) 것으로 처리됩니다. `HTTP/1.1` 이후에는 캐시를 제어하기 위해 `Cache-control` 헤더를 활용하며 `Cache-control` 헤더가 존재할 경우, `Expire` 헤더는 무시됩니다.

<br>

**Cache-Control**

![Cache-Control|600](cache-control.png)

&nbsp;&nbsp;`Cache-Control`은 `HTTP/1.1`부터 제공되는 헤더로 캐싱을 위한 다양한 옵션들을 제공합니다. `Cache-Control`에 의한 캐싱은 `GET` Method에 대한 응답을 캐싱하는 것으로 제한되며, 헤더를 통해 서비스의 캐싱 정책을 정의할 수 있습니다. 만약 `Expire` 헤더가 존재한다면 이는 무시되고 `Cache-Control` 헤더에 정의된 설정값이 적용됩니다.

&nbsp;&nbsp;최신 브라우저는 모두 `Cache-Control` 헤더를 지원하며, `Cache-Control`은 응답(Response)뿐만 아니라 요청(Request) 헤더로도 사용이 가능합니다. 요청의  `Cache-Control` 헤더 설정을 통해 캐시 서버에 캐싱된 내용이 아닌  웹 서버의 최신 데이터를 요청하는 것도 가능합니다. 다음은 각각 요청과 응답에서 `Cache-Control` 헤더에 사용할 수 있는 설정값의 종류입니다. 각 설정에 대한 자세한 설명은 [MDN 공식문서](https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Cache-Control)에서 확인할 수 있습니다.

```json
// Request
Cache-Control: max-age=<seconds> // 리소스가 최신이라고 판단하기 위한 최대 시간을 지정합니다.
// Expire가 날짜/시간의 형태였다면 여기에서는 요청시간에 대한 유효시간(초)를 가리킵니다.
Cache-Control: max-stale[=<seconds>]
Cache-Control: min-fresh=<seconds>
Cache-control: no-cache // 캐싱된 복사본을 보여주기 이전에 재검증을 위해 원 서버에 요청을 강제.
Cache-control: no-store
Cache-control: no-transform
Cache-control: only-if-cached

// Response
Cache-control: must-revalidate // 캐싱된 내용을 재사용하기 이전에 검증과정을 강제하여 만료된 데이터는 사용하지 않도록 합니다.
Cache-control: no-cache
Cache-control: no-store
Cache-control: no-transform
Cache-control: public // 응답이 어떤 캐시에 의해서든 캐싱됩니다.
Cache-control: private // 단일 사용자(ex. 클라이언트)만을 위해서만 캐싱됩니다.
Cache-control: proxy-revalidate
Cache-Control: max-age=<seconds>
Cache-control: s-maxage=<seconds>
```

<br>

마지막으로 다음은 캐싱을 막기 위한 디렉티브입니다.

```json
// 캐싱 X
Cache-Control: no-cache, no-store, must-revalidate
```

<br>

**References**
- [MDN Docs, Expires](https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Expires)
- [holisticseo posts - Expires HTTP](https://www.holisticseo.digital/pagespeed/expires/)
- [MDN Docs, Cache-Control](https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Cache-Control)