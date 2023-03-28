package com.nasnav.assessment.utils;

import static com.nasnav.assessment.strings.ExceptionMessages.MEDIA_TYPE_NOT_SUPPORTED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

import com.nasnav.assessment.error.ImageTypeNotSupportedException;
import com.nasnav.assessment.util.PictureUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith({MockitoExtension.class})
public class PictureUtilsTest {

  @InjectMocks
  PictureUtils pictureUtils;

  void setUp() {
    String allowedTypes = "image/jpeg,image/png,image/gif";
    this.pictureUtils = new PictureUtils(allowedTypes);
  }

  @Test
  void shouldDoNothingForValidImage() {
    setUp();

    MultipartFile file = new MockMultipartFile("testFile", "testFile", "image/png", new byte[]{});

    pictureUtils.validatePicture(file);
  }

  @Test
  void shouldThrowWhenUploadInvalidType() {
    setUp();

    MultipartFile file = new MockMultipartFile("testFile", "testFile", "image/pdf", new byte[]{});

    ImageTypeNotSupportedException exception = assertThrows(
        ImageTypeNotSupportedException.class, () -> pictureUtils.validatePicture(file)
    );
    assertThat(exception.getMessage(), is(MEDIA_TYPE_NOT_SUPPORTED));
  }
}
