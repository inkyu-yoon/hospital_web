package hospital.web.service;

import hospital.web.domain.dto.UserJoinRequest;
import hospital.web.domain.entity.User;
import hospital.web.exception.ErrorCode;
import hospital.web.exception.HospitalReviewAppException;
import hospital.web.repository.UserRepository;
import hospital.web.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;
    private long expiredTimeMs = 1000 * 60 * 60; //1시간

    @Transactional
    public User join(User user) {
        userRepository.findByUserName(user.getUserName())
                .ifPresent( user1 -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserName : %s",user1.getUserName()));
                });
        userRepository.save(user);

        return user;
    }


    public String login(String userName, String password) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new HospitalReviewAppException(ErrorCode.USER_NOT_FOUNDED, String.format("%s는 가입된 적이 없습니다.", userName)));

        if(!encoder.matches(password,user.getPassword())){
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, String.format("userName 또는 password가 잘 못 되었습니다."));
        }
        return JwtTokenUtil.createToken(userName, secretKey, expiredTimeMs);
    }

}
