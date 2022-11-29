package hospital.web.service;

import hospital.web.domain.dto.UserJoinRequest;
import hospital.web.domain.entity.User;
import hospital.web.exception.ErrorCode;
import hospital.web.exception.HospitalReviewAppException;
import hospital.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User join(User user) {
        userRepository.findByUserName(user.getUserName())
                .ifPresent( user1 -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, ErrorCode.DUPLICATED_USER_NAME.getMessage());
                });
        userRepository.save(user);

        return user;
    }

}
