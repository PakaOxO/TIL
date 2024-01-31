
&nbsp;&nbsp;`Skeleton UI`는 프론트엔드 개발자라면 `사용자 경험(User Experience)`을 위해서 Spinner의 형태든, Bar의 형태든 데이터를 기다리는 동안 보여주기 위한 화면을 활용해본 경험이 있을 것입니다. `Skeleton UI`가 없다면 사용자는 데이터를 기다리는 동안 빈 화면만을 보고 있어야 하고, 실제로 화면이 로드되는 중인지 알 수 없습니다. 그렇기 때문에 `Skeleton UI`는 사용자로 하여금 무언가 로드되는 중임을 나타내고, 기다리는 동안 생길 수 있는 지루함을 조금이나마(?) 덜기 위해 사용됩니다.

![|Spinner](../images/spinner.gif)
[출처: cssloaders](https://cssloaders.github.io/)

<br>

### Skeleton UI 타입

&nbsp;&nbsp;`Skeleton UI`에는 앞서 이미지로 살펴본 Spinner처럼 Spinner와 Bar를 사용해 로딩 중임을 나타내는 방법과 함께 최근에는 대체 컴포넌트를 활용해 해당 영역에 내용이 어떻게 표시될 것인지 간접적으로 표현하는 방법이 자주 사용됩니다. 대표적인 항공권 예매 서비스인 Skyscanner에서 검색한 항공편 데이터를 가져오고 로드하는 부분에 대체 컴포넌트를 활용한 `Skeleton UI`가 적용되어 있습니다.

![|skyscanner skeleton UI](../images/skyscanner_skeletonUI.gif)