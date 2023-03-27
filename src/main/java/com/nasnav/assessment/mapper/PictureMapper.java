package com.nasnav.assessment.mapper;

import com.nasnav.assessment.dto.PictureDTO;
import com.nasnav.assessment.model.Picture;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PictureMapper {

  Picture toEntity(PictureDTO pictureDTO);
  PictureDTO toDTO(Picture picture);
  List<PictureDTO> toDTOList(List<Picture> pictureList);
}
