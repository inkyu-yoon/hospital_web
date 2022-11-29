package hospital.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

//에러 코드를 포함시켜 리턴하기 위함
@AllArgsConstructor
@Getter
public class Response<T> {
    private String resultCode;
    private T result;

    public static Response<Void> error(String resultCode){
        return new Response(resultCode, null);
    }

    public static <T> Response<T> success(T result){
        return new Response<>("SUCCESS", result);
    }
}