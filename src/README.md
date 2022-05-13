# Springboot
## GetMapping PostMapping
## Model
* 해당 모델에 원하는 속성과 그것에 대한 값을 주어 전달할 뷰에 데이터를 전달할 수 있다.
* model.addAttribute("객체 이름", 객체명);
* 여기서 객체 이름은 view단에 객체 이름과 일치시켜야한다.
## @ModelAttribute("객체명") @Valid
* view단에서 데이터를 객체 형식으로 받아온다.
* 여기서도 view단의 객체명과 여기서의 객체명을 일치 시켜야한다.
* @Valid는 가져오는 데이터를 검증하기 위해서 사용
## @PathVariable
* 경로의 값을 읽어옴
* ex) 
* 경로 - localhost:8080/board/boardId/delete
* @GetMapping("/board/{boardId}/delete")
* @PathVariable("boardId") Long boardId {}
* 여기서 ("boardId") 는 변수명과 일치하면 생략가능

# Thymeleaf
## th:object
* form을 submit 할 때 th:object로 설정한 객체명으로 받아친다
* th:object="${객체명}"
## th:field
* th:object 내부의 각각의 필드들을 매핑해준다
* th:field="${객체명.필드명}"
## th:for
* th:name과 th:id 를 한번에 설정
## th:if th:unless
* if else 문
## th:each
* 반복문
* th:each="${board}
* 위와 같이 쓰면 board 테이블 안에 row 개수 만큼 반복
