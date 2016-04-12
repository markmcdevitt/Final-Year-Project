package com.finalspringproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalspringproject.dao.ReviewDao;
import com.finalspringproject.entity.Review;
import com.finalspringproject.entity.User;

@Service("reviewService")
public class ReviewService {

	private ReviewDao reviewDao;

	@Autowired
	public void setReviewDao(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}

	public List<Review> getReview(User user) {
		return reviewDao.getReview(user);
	}

	public void deleteReview(Review review) {
		reviewDao.deleteReview(review);
		
	}

}
