package com.nasnav.assessment.service.impl;

import static com.nasnav.assessment.enumeration.PictureStatus.APPROVED;
import static com.nasnav.assessment.enumeration.PictureStatus.REJECTED;
import static com.nasnav.assessment.enumeration.PictureStatus.UNPROCESSED;
import static com.nasnav.assessment.strings.ExceptionMessages.INVALID_PIC_OPERATION;
import static com.nasnav.assessment.strings.ExceptionMessages.PIC_ALREADY_APPROVED;
import static com.nasnav.assessment.strings.ExceptionMessages.PIC_ALREADY_REJECTED;
import static com.nasnav.assessment.strings.ExceptionMessages.PIC_NOT_FOUND;
import static com.nasnav.assessment.strings.ExceptionMessages.SYSTEM_ERROR;

import com.nasnav.assessment.dto.PictureDTO;
import com.nasnav.assessment.enumeration.ImageCategory;
import com.nasnav.assessment.error.ApplicationException;
import com.nasnav.assessment.error.InvalidPictureOperationException;
import com.nasnav.assessment.mapper.PictureMapper;
import com.nasnav.assessment.model.Picture;
import com.nasnav.assessment.repository.PictureRepository;
import com.nasnav.assessment.service.IPictureService;
import com.nasnav.assessment.strings.Paths;
import com.nasnav.assessment.util.PictureUtils;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements IPictureService {

  private final PictureRepository pictureRepository;
  private final PictureUtils pictureUtils;
  private final PictureMapper pictureMapper;

  @Override
  public PictureDTO uploadPicture(MultipartFile image, String description, String category) {
    try {
      pictureUtils.validatePicture(image);
      PictureDTO pictureDTO = PictureDTO.builder()
          .type(image.getContentType())
          .name(image.getOriginalFilename())
          .data(image.getBytes())
          .description(description)
          .category(ImageCategory.getCategoryByName(category))
          .pictureStatus(UNPROCESSED)
          .build();

      return pictureMapper.toDTO(pictureRepository.save(pictureMapper.toEntity(pictureDTO)));
    } catch (Exception e) {
      log.error("Unable to upload image due to: [ " + e.getMessage() + " ]");
      throw new InvalidPictureOperationException(INVALID_PIC_OPERATION);
    }
  }

  @Override
  @Transactional
  public List<PictureDTO> getAllUnprocessedImages() {
    try {
      return pictureMapper.toDTOList(pictureRepository.findByPictureStatus(UNPROCESSED));
    } catch (Exception e) {
      log.error("Unable to get all unprocessed images due to: [ "+e.getMessage()+" ]");
      throw new ApplicationException(SYSTEM_ERROR);
    }
  }

  @Override
  @Transactional
  public List<PictureDTO> getAllApprovedImages() {
    try {
      return pictureMapper.toDTOList(pictureRepository.findByPictureStatus(APPROVED));
    } catch (Exception e) {
      log.error("Unable to get all approved images due to: [ "+e.getMessage()+" ]");
      throw new ApplicationException(SYSTEM_ERROR);
    }
  }

  @Override
  @Transactional
  public PictureDTO getImage(Long id) {
    return pictureMapper.toDTO(pictureRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException(PIC_NOT_FOUND)
    ));
  }

  @Override
  @Transactional
  public PictureDTO approvePicture(Long id){
    Picture picture = pictureRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException(PIC_NOT_FOUND));

    if(picture.getPictureStatus().equals(APPROVED)){
      throw new ApplicationException(PIC_ALREADY_APPROVED);
    }
    picture.setPictureStatus(APPROVED);
    picture.setUrl(Paths.PICTURE_API +"/" + id);
    return pictureMapper.toDTO(pictureRepository.save(picture));
  }

  @Override
  @Transactional
  public PictureDTO rejectPicture(Long id){
    Picture picture = pictureRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException(PIC_NOT_FOUND));

    if(picture.getPictureStatus().equals(REJECTED)){
      throw new ApplicationException(PIC_ALREADY_REJECTED);
    }
    picture.setPictureStatus(REJECTED);
    picture.setUrl(null);
    picture.setData(null);
    return pictureMapper.toDTO(pictureRepository.save(picture));
  }

}
