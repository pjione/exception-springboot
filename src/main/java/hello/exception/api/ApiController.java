package hello.exception.api;

import hello.exception.exceptionhandler.ErrorResult;
import hello.exception.exceptionhandler.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiController {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult exHandler(Exception e){
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e){
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e){
        log.error("[exceptionHandler] ex", e);
        return new ResponseEntity<>(new ErrorResult("USER-EX", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api2/members/{id}")
    public Member getMember(@PathVariable String id){
        if(id.equals("ex")){
            throw new RuntimeException("잘못된 사용자");
        }
        if(id.equals("bad")){
            throw new IllegalArgumentException("잘못된 입력");
        }
        if(id.equals("user-ex")){
            throw new UserException("사용자 오류");
        }
        return new Member(id, "hello" + id);
    }
    @Data
    @AllArgsConstructor
    static class Member{
        String id;
        String name;
    }
}
