package com.ingryd.hms.mapper;

import com.ingryd.hms.dto.StaffDTO;
import com.ingryd.hms.dto.UpdateUserDTO;
import com.ingryd.hms.dto.UserDTO;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.entity.User;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface Mapper {
    Mapper mapper = Mappers.getMapper(Mapper.class);
    User mapToUser(UserDTO userDTO);
    UpdateUserDTO mapToUserDto(User user);
    User mapToUser(StaffDTO staffDTO);
    Staff mapToStaff(StaffDTO staffDTO);
}
