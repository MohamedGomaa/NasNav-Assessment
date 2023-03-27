package com.nasnav.assessment.service;

import com.nasnav.assessment.dto.PictureDTO;
import com.nasnav.assessment.dto.payload.request.UploadImageRequest;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface IPictureService {

  PictureDTO uploadPicture(MultipartFile image, String description, String category);

  List<PictureDTO> getAllUnprocessedImages();

  @Transactional
  List<PictureDTO> getAllApprovedImages();

  @Transactional
  PictureDTO getImage(Long id);

  @Transactional
  PictureDTO approvePicture(Long id);

  PictureDTO rejectPicture(Long id);
}
