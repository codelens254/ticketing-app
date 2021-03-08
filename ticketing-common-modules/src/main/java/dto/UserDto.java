package dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UserDto implements Serializable {

    private UUID userId;
    private String name;
    private String address;
    private String phoneNumber;

    public UserDto(UUID userId, String name, String address, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
