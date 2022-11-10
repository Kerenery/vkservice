package com.gazprom.app.entity;

import com.gazprom.app.service.VkHandleService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VkUser {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean isMember;
}
