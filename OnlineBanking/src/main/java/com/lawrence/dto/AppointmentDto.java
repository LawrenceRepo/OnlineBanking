package com.lawrence.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDto {

    private Date date;
    private String location;
    private String description;
    private boolean confirmed;


    private UserDto userDto;


}
