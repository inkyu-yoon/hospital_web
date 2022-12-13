
# 전국 병·의원 데이터 웹 페이지 만들기

## ERD

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221212231345033.png" alt="image-20221212231345033"  />
</p>

<br>

## 병·의원 데이터 정보 및 라이브러리

데이터 출처 : https://www.localdata.go.kr/devcenter/dataDown.do?menuNo=20001

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221109132651464.png" alt="image-20221109132651464" style="zoom:80%;" />
</p>

병원 데이터와 의원 데이터 결합, 총 121,005개의 데이터 (병원 6992개 + 의원 114013개)

데이터 형식 에러 튜플 2개 제외, 총 121003개 데이터

- Gradle 프로젝트 빌드

  ```groovy
  dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-mustache'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'com.mysql:mysql-connector-j:8.0.31'
	implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.8.1'
	implementation 'com.google.code.gson:gson:2.10'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'

	//security 관련 라이브러리
	implementation 'org.springframework.security:spring-security-test'
	implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'
	implementation group: 'org.springframework.boot', name: 'spring-boot-starter-security', version: '2.7.5'

  }
  ```

추가한 라이브러리는 위와 같다.

<br>


## 1. 전국 병 · 의원 데이터, MYSQL 데이터 베이스에 데이터 입력하기



### DDL 사용해서 데이터 테이블 생성 및 DB에 데이터 입력

1. application.yml 설정

```yaml
server:
  servlet:
    encoding:
      force-response: true #웹사이트 한글 깨짐 오류 해결
      
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/project-db #ec2 서버 사용시, 환경변수 사용
    username: root
    password: 12341234
    driver-class-name: com.mysql.cj.jdbc.Driver
    
  jpa:
    hibernate:
      ddl-auto: create  #테이블 생성 후 update 로 변경
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL55Dialect
    database: mysql

logging:
  level:
    org.hibernate.sql: debug

jwt:
  token:
    secret: hello # Jwt 토큰 생성 시 사용, 실제 사용하는 값은 환경변수로 입력
```



2. 데이터베이스 연결 & Jwt 토큰 사용을 위한 환경변수 추가
```
SPRING_DATASOURCE_URL=jdbc:mysql://데이터베이스연결된ec2주소 :3306/db명 ;SPRING_DATASOURCE_PASSWORD=비밀번호 ; JWT_TOKEN_SECRET=비밀키
```



3. Hospital 클래스 생성

[Hospital 엔티티 소스코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/domain/entity/Hospital.java)



4. 데이터 파일(txt) 한 줄 씩 읽어와서 객체 리스트를 생성하는 클래스 ReadData

[ReadData 소스코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/parser/ReadData.java)



5. 읽어온 한 줄을 객체로 정의하고, List 컬렉션에 저장하기 위한 HospitalParser

[HospitalParser 소스코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/parser/HospitalParser.java)


6. List 에 담긴 객체들을 데이터베이스에 입력하는 HospitalJpaRepository 소스 코드

데이터베이스에 저장하기 위해, 스프링 부트가 아닌 `resources.META-INF.persistence.xml` 을 생성하고, 순수 JPA의 persist로 입력하였다.

- [persistence.xml 소스 코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/resources/META-INF/persistence.xml)

- [HospitalJpaRepository 소스코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/repository/HospitalJpaRepository.java)

- [데이터 입력 실행 메인 소스 코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/InsertData.java)


<br>

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221129011919246.png" alt="image-20221129011919246" style="zoom:80%;" />
</p>

SQL 쿼리문의 결과와 같이, 총 121003개의 데이터가 잘 입력되었다.

<br>

---

## 2. 회원 가입 기능과 암호화

`BcryptPasswordEncoder` 클래스의 `encode()` 메서드를 사용하여 암호화 한 뒤 DB에 저장하도록 구현하였고,

병원 리뷰 작성 & 게시판 게시글 작성 시 ID와 비밀번호를 함께 입력받도록 하여, `matches()` 메서드로 DB에 암호화 되어 있는 비밀번호와 같은지 확인한 뒤

일치하는 경우에만 입력할 수 있고록 구현하였다.

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221213223728906.png" alt="image-20221213223728906" style="zoom:80%;" />
</p>

회원 가입 시, 계정명, 비밀번호, 이름, 이메일, 전화번호를 입력받도록 하였고, 회원 계정명은 `unique` 속성을 부여하여, 중복될 수 없다.

form 에 입력 시, post 요청으로 DB에 입력되도록 하였다.

<br>

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221213224812442.png" alt="image-20221213224812442" style="zoom: 80%;" />
</p>

위와 같은 데이터로 회원가입을 진행해보겠다.

<br>

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221213224903966.png" alt="image-20221213224903966" style="zoom:80%;" />
</p>

암호화된 비밀번호로 DB에 저장되는 것을 확인할 수 있다.

- [User Entity 소스코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/domain/entity/User.java)

- [User Controller 소스 코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/controller/UserController.java)

---

## 3. 병원 정보 표시 및 검색 기능

### 메인 화면 
<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221213223951408.png" alt="image-20221213223951408" style="zoom:80%;" />
</p>

JpaRepository 를 상속받는 Hospital Repository의 Jpa 메서드 명명규칙과 Pageable 클래스를 사용해서 약 12만건 데이터 페이징 구현하였다.

화면 구성은 [Bootstrap](https://getbootstrap.com/docs/5.2/getting-started/introduction/) 을 사용하였고, template 라이브러리는  mustache를 사용하였다.

- [Hospital Controller 소스파일](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/controller/HospitalController.java)
- [Hospital repository 소스파일](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/repository/HospitalRepository.java)

#### 1. 지역명 & 병원명 검색 기능

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221213224105433.png" alt="image-20221213224105433" style="zoom: 80%;" />
</p>

<br>

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221213224310341.png" alt="image-20221213224310341" style="zoom:80%;" />
</p>

주소 검색 선택후, 키워드 검색 시 주소 이름을 기준으로 페이징이 된다. 주소 검색 `울산`으로 검색한 결과는 위와 같다.

<br>



<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221213224445093.png" alt="image-20221213224445093" style="zoom:80%;" />
</p>

병원명 검색 선택 후, `효치과` 키워드를 입력 한 결과는 위와 같다.

<br>


#### 2. 병원 상세 정보 및 리뷰 작성 기능

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221212051906612.png" alt="image-20221212051906612" style="zoom:67%;" />
</p>

병원 이름 클릭 시, 위와 같은 상세 정보가 표시되고, 병원 도로명 주소 클릭 시, 네이버 맵 `search` api를 적용하여 검색되도록 구현하였다.

```
<td><a href="/hospitals/{{id}}/details">{{hospitalName}}</a></td> 
<td><a href="https://map.naver.com/v5/search/{{roadNameAddress}} "target="_blank">{{roadNameAddress}}</a></td>
```

<br>

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221213225203493.png" alt="image-20221213225203493" style="zoom:80%;" />
</p>

회원 가입을 진행하여, 회원 계정이 DB에 있는 경우에만 리뷰를 위와 같이 등록할 수 있다.

또한, 회원가입을 했던 계정과 비밀번호가 일치해야 리뷰 등록이 되도록 구현하였다.

리뷰 삭제의 경우도, 비밀번호를 입력해야 삭제할 수 있으며, 게시글을 작성한 아이디의 비밀번호와 일치해야 삭제가 되도록 구현하였다.

- [Review Entity 소스파일](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/domain/entity/Review.java)
- [Review Controller 소스파일](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/controller/ReviewController.java)


## 4. 게시판 기능 구현

- [PostController 소스 코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/controller/PostController.java)

<br>

### 1. 게시글 작성 기능



```java
 @PostMapping("")
    public String add(PostCreateRequest postCreateRequest,Model model) {
        User user = userService.getUserByUserAccount(postCreateRequest.getUserAccount());
        if (encoder.matches(postCreateRequest.getPassword(), user.getPassword())) {
            Post savedPost = postService.createPost(new Post(postCreateRequest, user));
            return "redirect:/posts/" + savedPost.getId();
        }
        model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
        return "posts/error";
    }

```

게시글 작성 기능도 리뷰 작성 기능과 마찬가지로, DB에 회원으로 저장되어 있는 경우만 작성 가능하다.

또한, DB에 저장된 암호화된 `password`와 게시글을 작성할 때 입력하는 `password`가 일치해야 저장된다.

<br>

### 2. 게시글 삭제 기능

```java
 @GetMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id) {
        postService.deleteOne(id);
        return "redirect:/posts";
    }
```

`JpaRepository` 를 상속받은 `PostRepository` 의 메서드 명명규칙으로 만든 메서드로,

해당 id에 해당하는 게시글을 지운다.

<br>

### 3. 게시글 수정 기능

```java
  @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Long id, Model model) {
        Optional<Post> optPost = postService.getOne(id);
        if (!optPost.isEmpty()) {
            model.addAttribute("post", optPost.get());
            return "posts/edit";
        } else {
            model.addAttribute("message", String.format("%d가 없습니다.", id));
            return "posts/error";
        }
    }

    @PostMapping("/{id}/update")
    public String update(PostUpdateRequest postUpdateRequest, Model model) {
        User user = userService.getUserByUserAccount(postUpdateRequest.getUserAccount());
        if (encoder.matches(postUpdateRequest.getPassword(), user.getPassword())) {
            Post updatedPost = new Post(postUpdateRequest, user);
            postService.createPost(updatedPost);
            return "redirect:/posts/" + updatedPost.getId();
        }
        model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
        return "posts/error";

    }
```

<img>

먼저, 수정 버튼을 클릭하면, `/{id}/edit` 에 GetMapping 된다. 수정하기 전, 원래 내용을 보여준다.

수정 작업 역시, 회원 비밀번호가 일치해야 수정이 되도록 구현하였고, 게시글 작성자 ID는 이미 알고 있는 정보이기 때문에 비밀번호만 입력받도록 하였다.

비밀번호가 일치하지 않을 경우, error 페이지가 나타나도록 하였다.

### 4. 게시글 전체 조회 및 단건 조회

```java

    @GetMapping("/new")
    public String createPage() {
        return "posts/new";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<PostShow> posts = postService.getAll().stream().map(post -> new PostShow(post,post.getUser().getUserAccount())).collect(Collectors.toList());
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("")
    public String show() {
        return "redirect:/posts/list";
    }

    @GetMapping("/{id}")
    public String showOne(@PathVariable(name = "id") Long id, Model model) {
        Optional<Post> optPost = postService.getOne(id);

        if (!optPost.isEmpty()) {
            PostShow postShow = new PostShow(optPost.get(),optPost.get().getUser().getUserAccount());
            log.info("{}",optPost.get().getUser().getUserAccount());
            model.addAttribute("post", postShow);
            return "posts/show";
        } else {
            model.addAttribute("message", String.format("%d가 없습니다.", id));
            return "posts/error";
        }
    }

```

<img>

게시글 조회의 경우 `PostShow` 라는 DTO를 정의하여 사용하였다.

작성 일자와 수정 일자를 `LocalDateTime` 타입에서 `DateTimeFormatter` 클래스를 사용하여 `yyyy년 MM월 dd일 HH시 mm분` 과 같은 형식으로 나타나게 구현하였다.

<img>

또한, 수정된 게시글의 경우 `(수정됨)` 표시가 나타나도록 구현하였다.

<br>

---


## 4. REST API

<br>

### 1. User

[UserRestController 소스코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/controller/UserRestController.java)

- PostMapping "api/v1/join" : 회원 가입 기능 (userAccount, password , userName, email , phone)

- PostMapping "api/v1/login" : 회원 로그인 기능(userAccount, password), 패스워드 일치할 시 JWT 토큰 발급, 그 외는 에러 발생

<br>

### 2. Review

[ReviewRestController 소스코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/controller/ReviewRestController.java)

- PostMapping "api/v1/reviews" : 리뷰 등록 기능 (title, content, userAccount, hospitalId), 단 JWT토큰 헤더에 포함해야 등록 가능




<br>

---

## 5. Spring Security & Jwt 토큰


### 1. 회원가입 후, 로그인 시 Jwt 토큰으로 응답

```
jwt:
  token:
    secret: hello
```
`application.yml` 에 위 구문을 추가한다.

<br>

```
JWT_TOKEN_SECRET = 사용자비밀키
```

실제로 주입되는 값은 환경 변수로,  `@Value("${jwt.token.secret}")` 어노테이션을 사용하여 토큰 생성에 쓰일 key 값 주입한다.

<br>

[JwtToken 생성 소스 코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/Security/JwtTokenUtil.java)

토큰 만료시간은 1시간으로 설정하였다.

이렇게 생성된 토큰을 회원 login 시 body에 담아서 응답한다.

<br>

#### 2. join & login 외 Post 요청 시, JwtToken 으로 권한 확인


[Jwt Token 확인 및 권한 부여 소스 코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/Security/JwtTokenFilter.java)

[httpSecurity Token 확인 필터 추가](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/configuration/SecurityConfig.java)