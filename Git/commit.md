
&nbsp;&nbsp;본문은 `git commit`과 관련된 내용을 전체적으로 정리한 내용을 담고 있습니다.

<br>

## 기본 사용법

### 1. 변경 사항 스테이징

```sh
git add <file1> <file2> ...   # 특정 파일 추가
git add .                     # 현재 디렉토리의 모든 변경 사항 추가
```

### 2. 커밋

```sh
git commit -m 'Your commit message'
```

- `-m, --message <msg>`: 커밋 메시지를 직접 명령어에 포함시켜 작성합니다.

### 3. 메시지 없이 커밋

```sh
git commit
```

- 편집기가 열리고, 메시지를 작성할 수 있습니다.

### 4. 커밋 수정

```sh
git commit --amend
```

- 마지막 커밋을 수정합니다. 커밋 메시지나 포함된 파일을 변경할 때 유용합니다.

<br>

## 커밋 옵션 종류

### 기본 옵션

- `-a, --all`: 변경된 파일을 자동으로 스테이징합니다. 단, 새로 추가된 파일은 포함되지 않습니다.

\"git commit -a -m 'Commit message'\"

### 수정 및 병합 옵션

- `--amend`: 마지막 커밋을 수정합니다.

\"git commit --amend\"

- `--no-edit`: `--amend` 옵션과 함께 사용되어 이전 커밋 메시지를 그대로 사용합니다.

\"git commit --amend --no-edit\"

### 파일 및 경로 관련 옵션

- `--include <file>`: 지정된 파일을 포함하여 커밋합니다.
- `--only <file>`: 지정된 파일만 커밋합니다.

### 템플릿 및 메시지 관련 옵션

- `-t, --template <file>`: 커밋 메시지를 작성할 때 사용할 템플릿 파일을 지정합니다.
- `--cleanup <mode>`: 커밋 메시지를 정리하는 방식을 지정합니다. (`default`, `verbatim`, `whitespace`, `strip`)

### 날짜 및 시간 관련 옵션

- `--date <date>`: 커밋 날짜를 지정합니다.

\"git commit --date='2024-06-19T12:00:00'\"

### 서명 관련 옵션

- `-S, --gpg-sign`: 커밋을 GPG 키로 서명합니다.
- `--no-gpg-sign`: GPG 서명을 하지 않고 커밋합니다.

### 상태 및 메타데이터 관련 옵션

- `-v, --verbose`: 커밋할 때 변경된 내용을 보여줍니다.
- `--no-verify`: 커밋 훅을 무시하고 커밋합니다.
- `-q, --quiet`: 커밋 과정에서 메시지를 최소화합니다.

### 기타 옵션

- `--dry-run`: 실제로 커밋을 하지 않고 어떤 작업이 수행될지 확인합니다.
- `--allow-empty`: 변경 사항이 없는 빈 커밋을 생성합니다.
- `--allow-empty-message`: 커밋 메시지가 없는 커밋을 허용합니다.

## `git commit -a`와 `git add .` + `git commit -m` 차이점

### `git commit -a`

- 수정되거나 삭제된 **추적된 파일**을 자동으로 스테이징하고 커밋합니다.
- 새로운 파일은 포함되지 않습니다.

\"git commit -a -m 'Commit message'\"

### `git add .` + `git commit -m`

- `git add .` 명령어는 **추적된 파일**과 **새로운 파일**을 모두 스테이징합니다.

\"git add .\"
\"git commit -m 'Commit message'\"

## `git commit --amend` 사용 예시

### 1. 커밋 메시지 수정

\"git commit --amend\"
# 텍스트 편집기가 열리고 메시지를 수정할 수 있습니다.

또는

\"git commit --amend -m 'Updated commit message'\"

### 2. 추가 수정사항 포함

# 파일 수정
\"echo 'Additional content' >> file.txt\"

# 변경 사항 스테이징
\"git add file.txt\"

# 마지막 커밋 수정
\"git commit --amend\"
# 편집기에서 커밋 메시지를 수정하거나, 기존 메시지를 그대로 사용할 수 있습니다.

### 3. 파일 누락 수정

# 누락된 파일 추가
\"git add missing_file.txt\"

# 마지막 커밋 수정
\"git commit --amend\"
# 편집기에서 커밋 메시지를 수정하거나, 기존 메시지를 그대로 사용할 수 있습니다.

### 주의사항

1. **로컬 커밋일 경우**:
   - `git commit --amend`는 로컬 브랜치에서 작업할 때 문제가 없습니다.

2. **공유된 커밋일 경우**:
   - 이미 원격 저장소나 다른 개발자와 공유된 커밋을 수정하는 것은 조심해야 합니다.
   - 만약 꼭 수정해야 한다면, 팀원들과의 협의 후 `git push --force`를 사용하여 원격 저장소에 반영할 수 있습니다.

\"git push --force\"

## 사례 확인

# 리드미 파일 생성 및 수정
\"echo 'Initial content' > README.md\"
\"git add README.md\"
\"git commit -m 'docs: 리드미 추가'\"

# 리드미 파일 내용 추가
\"echo 'Additional content' >> README.md\"
\"git add README.md\"
\"git commit --amend\"

`git log`를 확인하면, 커밋은 하나만 존재하며, 해당 커밋에는 리드미 파일의 초기 내용과 추가된 내용이 모두 포함되어 있음을 알 수 있습니다.

\"git log\"

출력 예시:

\"commit <new_commit_hash> (HEAD -> main)\"
\"Author: Your Name <you@example.com>\"
\"Date:   Date and Time\"

\"    docs: 리드미 추가\"
\""