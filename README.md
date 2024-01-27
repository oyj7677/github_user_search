# remember_test

## 프로젝트 설명
Github API의 Search users를 기반으로 사용자 검색 및 즐겨찾기 기능을 수행합니다.

## 기능 요구 사항
 - Github 사용자 검색 API(https://developer.github.com/v3/search/#search-users)를
이용하여 사용자를 검색 및 표시
- 즐겨찾기한 사용자를 로컬 DB에 저장
- 즐겨찾기한 사용자를 로컬 DB에서 검색

## 사용된 기술 스택
 - Kotlin
 - Retrofit
 - Coroutine
 - MVVM Architecture
 - Room Database

## 구현 기능
 - 깃허브 API를 활용한 유저 검색
 - 로컬 데이터베이스를 활용한 즐겨찾기 추가 및 삭제
 - 사용자 정렬 및 사용자 이름의 초성,알파벳 기준 헤더 구현

## 사용된 기술 및 라이브러리
 - Kotlin
 - MVVM Architecture
 - Retrofit: API 요청
 - Gson: 데이터 변환
 - Room: 로컬 데이터베이스 관리
 - Coroutine: 비동기 작업 처리
 - Hilt : 의존성 주입

## 개발 과정 및 고려 사항
프로젝트 개발 시 중요하게 고려했던 부분은 다음과 같습니다.
1. 아키텍처 디자인
 - 클린아키택처를 지향하며 app, data, domain 모듈로 구성되어있습니다.
 - domain module과 data module간에 의존성 역전 원칙 적용
 - app module : MVVM패턴 적용, di
 - data module : 저장소 패턴, 데이터 접근 담당
 - domain module : github user검색 역할 담당

2. 사용자 이름에 대한 정의
 - 사용자 검색 API 응답 결과 중 "loging" 필드를 이름이라 정의

## 보안되어야 할 점
 - retrofit converter
 retrofit의 컨버터로 gson을 사용하고 있습니다. gson의 문제점으로는 응답 데이터를 받았을 때 non-null 변수에 null이 들어갈 수 있다는 점입니다.
 kotlinx-serialization은 default value를 적용할 수 있어 이러한 문제를 해결할 수 있습니다.
 github api의 경우 아직까지는 오류없이 정확하게 응답이 오고 있으나 예상하지 못한 오류로 인한 에러가 발생할 수 있으므로 보완되어야할 점이라고 판단됩니다.

- 클린아키택처
클린아키텍처적으로 구성하기 위해서는 presentation module이 존재해야합니다. 하지만 앱의 규모가 작아 presentation module을 구성하지 않고 app module에 많은 역할을 부여했습니다.
