package hello.thymeleaf.basic;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    // text 출력
    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "Hi~~");
        return "basic/text-basic";
        /* 텍스트 출력 <span th:text="${data}">
         *  태그 밖에서 하려면 [[...]] 형식
         * */
    }

    // escape
    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "Hi~~<b>xogns</b>");
        return "basic/text-unescaped";

        /*
         * escape
         * th:text 사용 하면 < > 를 &lt; , &gt; 형식(HTML 엔티티)으로 읽어옴. html 엔티티로 변경 해주는 것을 escape 라고 한다.
         * 만약 하이라이트를 하고 싶으면 th:utext or [(...)] 이용
         * th:inline="none" 을 사용하면 [[...]] 같은 것을 타임리프가 해석하지 않고 글자 그대로 보여주기 위해 사용.
         * */
    }

    // 스프링 EL 표현식
    @GetMapping("/variable")
    public String variable(Model model) {
        User userA = new User("userA", 10);
        User userB = new User("userB", 20);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        /*
        * user.username , user['username'], user.getUsername() 형식이 있음
        * th:with 지역 변수
        * */
        return "basic/variable";
    }

    // 기본 객체 ${#request}, ${#response} 등등 스프링 부트 3.0 이하에서만.
    @GetMapping("/basic-objects")
    public String basicObjects(HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        return "basic/basic-objects";

        /* 파라미터 가저오기 ${param.paramName}
        * 아래 빈 가져오기 ${@HelloBean.hello('spring')}
        * */
    }

    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "Hello " + data;
        }
    }

    // 날짜
    @GetMapping("date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }

    // URL 링크
    @GetMapping("link")
    public String link(Model model) {
        model.addAttribute("param1", "date1");
        model.addAttribute("param2", "date2");

        return "basic/link";
    }

    // literal
    @GetMapping("/literal")
    public String literal(Model model) {
        model.addAttribute("data", "spring");

        return "basic/literal";
    }

    // 연산
    @GetMapping("/operation")
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");
        return "basic/operation";
    }

    // 속성 값
    @GetMapping("/attribute")
    public String attribute() {
        return "basic/attribute";
    }

    //반복
    @GetMapping("/each")
    public String each(Model model) {
        addUsers(model);
        return "basic/each";
    }

    // 조건문 평가
    @GetMapping("/condition")
    public String condition(Model model) {
        addUsers(model);
        return "basic/condition";
    }

    // 주석
    @GetMapping("/comments")
    public String comments(Model model) {
        model.addAttribute("data", "Spring!");
        return "basic/comments.html";
    }

    // 블록  : each와 비교해서 보면 좋음
    @GetMapping("/block")
    public String block(Model model) {
        addUsers(model);
        return "basic/block";
    }

    // 자바 스크립트 인라인
    @GetMapping("/javascript")
    public String javascript(Model model) {
        model.addAttribute("user", new User("userA", 10));
        addUsers(model);
        return "basic/javascript";
    }

    // 반복 사용
    private void addUsers(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("userA", 10));
        list.add(new User("userB", 20));
        list.add(new User("userC", 30));
        model.addAttribute("users", list);
    }

    // EL 표현식 사용
    @Data
    static class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}
