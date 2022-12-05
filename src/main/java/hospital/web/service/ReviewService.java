package hospital.web.service;

import hospital.web.domain.entity.Hospital;
import hospital.web.domain.entity.Review;
import hospital.web.repository.HospitalRepository;
import hospital.web.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Review createReview(Review review) {
        Review savedReview = reviewRepository.save(review);
        return savedReview;
    }

}
