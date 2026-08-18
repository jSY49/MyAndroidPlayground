# Contributing Guide

이 프로젝트는 여러 안드로이드 기술을 연습하기 위한 Playground 프로젝트입니다.
아래 커밋 메시지 컨벤션을 따릅니다.

## 커밋 메시지 포맷

```
<type>(<scope>): <subject>

<body> (선택)

<footer> (선택)
```

## Type

| Type | 의미 | 예시 |
|---|---|---|
| `feat` | 새 기능 추가 | `feat(feature-todolist): 할 일 추가 기능 구현` |
| `fix` | 버그 수정 | `fix(feature-todolist): 삭제 시 크래시 나는 버그 수정` |
| `refactor` | 기능 변화 없는 코드 개선 | `refactor(feature-todolist): ViewModel 로직 분리` |
| `style` | 코드 포맷팅 등 (로직 변화 없음) | `style: ktlint 규칙 적용` |
| `docs` | 문서 수정 | `docs: README에 모듈 구조 설명 추가` |
| `test` | 테스트 코드 추가/수정 | `test(feature-todolist): ViewModel 단위테스트 추가` |
| `chore` | 빌드 설정, 의존성 등 잡무 | `chore: Gradle 버전 업데이트` |
| `perf` | 성능 개선 | `perf(feature-todolist): LazyColumn 리컴포지션 최소화` |
| `design` | UI/UX 디자인 변경 | `design(feature-todolist): 버튼 스타일 수정` |

## Scope

멀티모듈 프로젝트이므로, 어느 모듈을 작업했는지 scope에 명시합니다.

```
feat(feature-todolist): 완료 체크박스 UI 추가
fix(core-designsystem): 다크모드 컬러 대비 수정
chore(app): Navigation 모듈 의존성 추가
```

## Subject 작성 규칙

- 한글: "~함", "~수정", "~추가"처럼 명사형/동사 어간으로 통일
- 영어: 동사 원형으로 시작, 마침표 없음 (예: `Add todo delete function`)
- 50자 이내 권장

## Body (선택)

"무엇을" 바꿨는지는 subject로 충분한 경우가 많습니다.
"왜" 이렇게 바꿨는지 설명이 필요할 때만 body를 추가합니다.

```
fix(feature-todolist): LazyColumn 스크롤 시 항목 깜빡이는 버그 수정

key를 item의 index 대신 고유 id로 지정해서
Compose가 아이템을 잘못 재활용하는 문제를 해결함
```

## 예시 커밋 로그

```
feat(feature-todolist): 할 일 목록 화면 UI 구현
feat(feature-todolist): 할 일 추가/삭제 기능 구현
fix(feature-todolist): 빈 텍스트 입력 시 크래시 수정
refactor(feature-todolist): State를 UiState 클래스로 분리
test(feature-todolist): TodoViewModel 유닛테스트 작성
chore: feature:animation 모듈 스캐폴딩 추가
docs: README에 모듈 구조 다이어그램 추가
style(feature-todolist): ktlint 포맷팅 적용
perf(feature-todolist): remember로 불필요한 리컴포지션 방지
```
