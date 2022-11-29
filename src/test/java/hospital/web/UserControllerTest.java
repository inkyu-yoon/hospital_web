package hospital.web;

import com.google.gson.Gson;
import hospital.web.controller.UserController;
import hospital.web.domain.dto.UserJoinRequest;
import hospital.web.domain.entity.User;
import hospital.web.exception.ErrorCode;
import hospital.web.exception.HospitalReviewAppException;
import hospital.web.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;


    @Test
    @DisplayName("회원가입 성공")
    void join_success() throws Exception {
        UserJoinRequest userJoinRequest = new UserJoinRequest("yooninkyu", "12345", "ikyoon", "010-0100-0100");

        when(userService.join(any())).thenReturn(mock(User.class));

        Gson gson = new Gson();
        String content = gson.toJson(userJoinRequest);


        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패")
    void join_fail() throws Exception {

        UserJoinRequest userJoinRequest = new UserJoinRequest("yooninkyu", "12345", "ikyoon", "010-0100-0100");

        when(userService.join(any())).thenThrow(new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, ""));

        Gson gson = new Gson();
        String content = gson.toJson(userJoinRequest);

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}