package com.nasnav.assessment.repository;

import com.nasnav.assessment.enumeration.PictureStatus;
import com.nasnav.assessment.model.Picture;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

  List<Picture> findByPictureStatus(PictureStatus status);
}
