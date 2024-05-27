package com.ingryd.hms.mapper;

import com.ingryd.hms.dto.UserDTO;
import com.ingryd.hms.entity.User;

@org.mapstruct.Mapper
public interface Mapper {

    User mapToUser(UserDTO userDTO);
}
