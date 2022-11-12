package com.gazprom.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VkUserDto {
    @ApiModelProperty(notes = "VkUser ID", example = "1", required = true)
    private Long id;
    @ApiModelProperty(notes = "VkUser firstName", example = "Pavel")
    private String firstName;
    @ApiModelProperty(notes = "VkUser lastName", example = "Durov")
    private String lastName;
    @ApiModelProperty(notes = "VkUser middleName", example = "null)")
    private String middleName;
    @ApiModelProperty(notes = "Shows if vkUser is a member of a group", example = "false")
    private Boolean isMember;
}
