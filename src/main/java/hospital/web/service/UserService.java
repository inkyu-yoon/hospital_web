package hospital.web.service;

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
        userRepository.findByUserId(user.getUserId())
                .ifPresent( user1 -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserId : %s",user1.getUserId()));
                });
        userRepository.save(user);

        return user;
    }


    public String login(String userId, String password) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new HospitalReviewAppException(ErrorCode.USER_NOT_FOUNDED, String.format("%s는 가입된 적이 없습니다.", userId)));

        if(!encoder.matches(password,user.getPassword())){
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, String.format("userName 또는 password가 잘 못 되었습니다."));
        }
        return JwtTokenUtil.createToken(userId, secretKey, expiredTimeMs);
    }

}
