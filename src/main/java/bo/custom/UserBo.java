package bo.custom;

import bo.SuperBo;
import dto.UserDto;
import entity.User;

import java.util.List;

public interface UserBo extends SuperBo {
    String generateId();
    Boolean saveUser(UserDto dto);
    List<UserDto> getAll();
    Boolean deleteUser(String value);
    UserDto getUser(String value);
    String encrypt(String data);
    Boolean checkPassword(String password, UserDto user);
    Boolean updatePassword(String pw, UserDto dto);
}
