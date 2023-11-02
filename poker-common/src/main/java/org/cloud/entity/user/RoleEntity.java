package org.cloud.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = -2222489977114550194L;
    private String name;
}
