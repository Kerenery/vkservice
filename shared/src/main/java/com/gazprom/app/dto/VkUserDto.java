package com.gazprom.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VkUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean isMember;
}
