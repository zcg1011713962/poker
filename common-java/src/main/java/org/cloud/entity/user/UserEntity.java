package org.cloud.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -9171812731598801961L;
    private Long id;
    private String userName;
    private String pwd;
    private List<RoleEntity> roles;
    private String email;

}
