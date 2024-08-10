# 기타 전달사항
1. auth 서버의 경우 실제 DB와 연결 전까지 profile을 구분할 이유가 없다고 판단 - 현상유지(24.8.10 11:00)
2. 기본 구현 완료 - (24.8.10 11:17)

---

# 기본 구현

- [x]  **기본 API 구성하기**
    - API 목록
        1. `POST /products`  상품 추가 API 
        **상품 Entity**
            
            
            | Key | Value |
            | --- | --- |
            | product_id | Long (Primary, Auto Increment) |
            | name | String |
            | supply_price | Integer |
            
            **상품 추가시 Request/Response 객체 구성은 자유입니다.**
            
        2. `GET /products` 상품 목록 조회 API
            
            **응답 형태: List<응답 객체>**
            
            **응답 객체**
            
            | Key | Value |
            | --- | --- |
            | product_id | Long |
            | name | String |
            | supply_price | Integer |
            
            **상품 목록 조회시 Request 객체 구성은 자유입니다.**
            
        3. `POST /order` 주문 추가 API
            
            **주문 Entity**
            
            | Key | Value |
            | --- | --- |
            | order_id | Long (Primary, Auto Increment) |
            | name | String |
            | product_ids | List<https://www.notion.so/Chapter-1-8049fc370de54e2f84e07c4b5fb68705?pvs=21> |
            
            **Request/Response 객체 구성은 자유입니다.**
            
            **주문 매핑 상품 Entity**
            
            | Key | Value |
            | --- | --- |
            | id | Long (Primary, Auto Increment) |
            | order | https://www.notion.so/Chapter-1-8049fc370de54e2f84e07c4b5fb68705?pvs=21 |
            | product_id | Long |
        4. `PUT /order/{orderId}`  주문에 상품을 추가하는 API
            
            **요청 Body**
            
            | Key | Value |
            | --- | --- |
            | product_id | Long |
            
            **Request/Response 객체 구성은 자유입니다.**
            
        5. `GET /order/{orderId}`  주문 단건 조회 API
            
            **응답 객체**
            
            | Key | Value |
            | --- | --- |
            | order_id | Long |
            | product_ids | List<Long> |
        6. `GET /auth/signIn?user_id={string}`  로그인 API 
            
            DB 연결이 되지 않아도 됩니다. MSA 강의 문서를 참고해서 Gateway 서비스의 Filter 만 통과할 수 있도록 구성해주세요.
            

- [x]  **상품 서비스는 라운드로빈 형식으로 로드밸런싱 구성하기**
    1. IntelliJ Configuration 을 이용하여 상품 서비스를 `19094` 포트로 하나 더 실행 해보세요.
    2. 라운드로빈 형식으로 로드밸런싱을 구현해서 상품 서비스가 호출될 때마다 두 서비스를 반복하며 호출되게 구성해 보세요.
    3. 상품 목록을 조회할 때마다 API 의 응답 헤더의 `Server-Port` 값이 `19093` , `19094` 로 변경되어야 합니다.
    
- [x]  [**주문에 상품을 추가하는 API](https://www.notion.so/Chapter-1-8049fc370de54e2f84e07c4b5fb68705?pvs=21) 만들 때 아래와 같이 구성해보세요.**
    1.  `FeignClient`를 이용해서 주문 서비스에 상품 서비스 클라이언트 연결
    2. [상품 목록 조회 API](https://www.notion.so/Chapter-1-8049fc370de54e2f84e07c4b5fb68705?pvs=21)를 호출해서 파라미터로 받은 `product_id` 가 상품 목록에 존재하는지 검증
    3. 존재할경우 주문에 상품을 추가하고, 존재하지 않는다면 주문에 저장하지 않음.
    
- [x]  **분산추적 구현해보기**
    - `주문 서비스` 와 `상품 서비스` 에 Zipkin 을 연동하고, 호출시 Zipkin 대시보드에 `Duration` 이 측정 되는지 확인해보세요.
    
- [x]  **캐싱 기능 구현하기**
    - 주문 조회 API 의 결과를 캐싱 처리하여 **60초 동안**은 DB 에서 불러오는 데이터가 아닌 
    **메모리에 캐싱된 데이터**가 보여지도록 설정해주세요.

- [x]  **외부 요청 보호**
    - Oauth2,JWT 기반으로 인증/인가를 구성하여 인가 없이 `상품 서비스`, `주문 서비스`를 호출할 때 
    **401** HTTP ****Status Code를 응답하도록 설정해주세요.

- [x]  **캐시를 더 활용 해볼까요?**
    1. [상품 추가 API](https://www.notion.so/Chapter-1-8049fc370de54e2f84e07c4b5fb68705?pvs=21)  를 호출 할 경우 [상품 목록 조회 API](https://www.notion.so/Chapter-1-8049fc370de54e2f84e07c4b5fb68705?pvs=21) 의 응답 데이터 캐시가 갱신되도록 구현해주세요.
    (~~MSA~~ **인메모리 저장소 및 캐싱 강의** 중 **Spring Boot 프로젝트에 캐싱 적용하기** 를 참고해서 구현해주세요.)
    2. 상품 추가 후 [상품 목록 조회 API](https://www.notion.so/Chapter-1-8049fc370de54e2f84e07c4b5fb68705?pvs=21) 호출 시 데이터가 변경 되는지 확인합니다.
    
- [x]  **로컬과 서버의 환경을 분리해 보세요!**
    - 로컬에서는 [localhost:3306](http://localhost:3306) 으로 DB에 접근하고, 서버에 배포시엔 RDS 주소로 접근하게 구성하도록 환경을 분리해봅시다! - 환경은 `dev`, `prod` 프로필로 나누어 주세요.)

---

# 추가 구현

- [ ]  **CI/CD를 구성 해봅시다! (이 기능만 직전 제출하신 과제로 진행해주시면 됩니다.)**
    
    `Spring 심화 과제` 프로젝트의 Git을 수정하여 Git Push 만으로 서버에 업데이트 할 수 있도록 구성해보세요. ( Github Actions, EC2 ECR/ECS 를 이용해서 구성 해봅시다. )
    **실무 예시:** [AWS Fargate와 Amazon ECR을 통한 삼성 개발자 포탈 구축 사례](https://aws.amazon.com/ko/blogs/korea/samsung-builds-a-secure-developer-portal-with-fargate-and-ecr/)
    
- [ ]  Layered Architecture 에 따라 고수준 모듈을 보호하는 구성으로 프로젝트를 구현 해보세요!
**실무 예시:** [우아한 형제들 - 주니어 개발자의 클린 아키텍처 맛보기](https://techblog.woowahan.com/2647/)
- [ ]  DB를 이용한 회원가입으로 서비스를 만들어보세요!
    - MSA 환경에서 Oauth2 인증/인가 구현
        1. 심화과정 과제를 참고하여 회원가입 기능을 구현합니다. = `POST auth/signUp`
        2. `Gateway` 서비스의 Filter 로직을 수정하여 Oauth2 규칙에 따라 
        헤더의 JWT 토큰이 회원가입 한 유저의 토큰인지 검증하는 로직을 구현해보세요!
        3. `상품 서비스`, `주문 서비스` 를 호출해보며 정상적으로 인가 처리가 되었는지 확인합니다.
    
    **실무 예시:** [카카오 광고 플랫폼 - Gateway 를 이용한 인증/인가 처리 사례 (78슬라이드 ~ )](https://www.slideshare.net/slideshow/msa-api-gateway/113145634#78)
