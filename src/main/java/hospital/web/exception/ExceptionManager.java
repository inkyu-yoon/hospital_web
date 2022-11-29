package hospital.web.exception;

import hospital.web.domain.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*RestControllerAdvice
- `@ControllerAdvice와 @ResponseBody`를 합쳐놓은 어노테이션
- 단순히 예외만 처리하고 싶다면 `@ControllerAdvice`를 적용하면 되고, 응답으로 객체를 리턴해야 한다면 `@RestControllerAdvice`를 적용하면 됩니다.
ControllerAdvice는
- `@ExceptionHandler`, `@ModelAttribute`, `@InitBInder`가 적용된 메서드들에 **AOP**를 적용해 Controller 단에 적용하기 위해 고안된 어노테이션.
- 모든 @Controller에 대한 전역적으로 발생할 수 있는 예외를 잡아서 처리할 수 있습니다.
 */
@RestControllerAdvice
public class ExceptionManager {
/* ExceptionHandler
- @Controller, @RestController가 적용된 Bean 내에서 발생하는 예외를 잡아서 하나의 메서드에서 처리해주는 기능을 담당합니다.
- 인자로 캐치하고 싶은 예외 클래스를 등록해주면 됩니다.
- ExceptionManager에 해당하는 Bean에서 HostpitalReviewAppException이 발생한다면, `@ExceptionHandler(HospitalReviewAppException.class)`가 적용된 메서드가 호출될 것입니다.
 */
    @ExceptionHandler(HospitalReviewAppException.class)
    public ResponseEntity<?> hospitalReviewAppExceptionHandler(HospitalReviewAppException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Response.error(e.getErrorCode().name()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> exceptionHandler(RuntimeException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorCode.INTERNER_SERVER_ERROR.name());
    }
}
