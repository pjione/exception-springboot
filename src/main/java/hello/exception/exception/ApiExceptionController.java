package hello.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionController {
    @GetMapping("/api/members/{id}")
    public Member getMember(@PathVariable String id){
        if(id.equals("ex")){
            throw new RuntimeException("잘못된 사용자");
        }
        if(id.equals("bad")){
            throw new IllegalArgumentException("잘못된 입력");
        }
        return new Member(id, "hello" + id);
    }
    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data) {
        return "ok";
    }
    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1() {
        throw new Handler();
    }

    @Data
    @AllArgsConstructor
    static class Member{
        String id;
        String name;
    }
}
