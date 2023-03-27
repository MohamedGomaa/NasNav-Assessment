package com.nasnav.assessment.controller;

import static com.nasnav.assessment.strings.Paths.PICTURE_API;

import com.nasnav.assessment.dto.PictureDTO;
import com.nasnav.assessment.dto.payload.request.UploadImageRequest;
import com.nasnav.assessment.service.IPictureService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(PICTURE_API)
@RequiredArgsConstructor
public class PictureController {

  private final IPictureService pictureService;

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping
  public ResponseEntity<List<PictureDTO>> getAllPictures() {
    return new ResponseEntity<>(pictureService.getAllUnprocessedImages(), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping("/approve/{id}")
  public ResponseEntity<PictureDTO> approveImage(@PathVariable("id") Long id) {
    return new ResponseEntity<>(pictureService.approvePicture(id), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping("/reject/{id}")
  public ResponseEntity<PictureDTO> rejectImage(@PathVariable("id") Long id) {
    return new ResponseEntity<>(pictureService.rejectPicture(id), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('REGULAR')")
  @PostMapping(value = "/upload",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<PictureDTO> uploadImage(
      @RequestParam String description, @RequestParam String category,
      @RequestPart("picture") MultipartFile image) {
    return new ResponseEntity<>(pictureService.uploadPicture(image, description, category),
        HttpStatus.CREATED);
  }
}
