package hospital.web;

import com.google.gson.Gson;
import hospital.web.configuration.EncryptorConfig;
import hospital.web.controller.UserRestController;
import hospital.web.domain.dto.join.UserJoinRequest;
import hospital.web.domain.dto.login.UserLoginRequest;
import hospital.web.domain.entity.User;
import hospital.web.exception.ErrorCode;
import hospital.web.exception.HospitalReviewAppException;
import hospital.web.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({UserRestController.class, EncryptorConfig.class})
class UserRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;


    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void join_success() throws Exception {
        UserJoinRequest userJoinRequest =
                new UserJoinRequest("inkyu312",
                        "12345",
                        "윤인규",
                        "ikyoon",
                        "010-0100-0100");

        when(userService.join(any())).thenReturn(mock(User.class));

        Gson gson = new Gson();
        String content = gson.toJson(userJoinRequest);


        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf()) //토큰 인증 통과
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패")
    @WithMockUser
    void join_fail() throws Exception {

        UserJoinRequest userJoinRequest =
                new UserJoinRequest("inkyu312",
                        "12345",
                        "윤인규",
                        "ikyoon",
                        "010-0100-0100");

        when(userService.join(any())).thenThrow(new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, ""));

        Gson gson = new Gson();
        String content = gson.toJson(userJoinRequest);

        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isConflict());
    }
//
    @Test
    @DisplayName("로그인 실패 - id 없음")
    @WithMockUser
    void login_fail() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest("yooninkyu", "12345");

        when(userService.login(any(), any())).thenThrow(new HospitalReviewAppException(ErrorCode.USER_NOT_FOUNDED, ""));

        Gson gson = new Gson();
        String content = gson.toJson(userLoginRequest);

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}