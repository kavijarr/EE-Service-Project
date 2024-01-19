package bo.custom.impl;

import bo.custom.UserBo;
import dao.DaoFactory;
import dao.custom.UserDao;
import dto.UserDto;
import entity.Item;
import entity.User;
import util.BoType;
import util.DaoType;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {
    UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);
    @Override
    public String generateId() {
        User lastUser = userDao.getLastUser();
        if (lastUser!=null){
            String id = lastUser.getId();
            int num = Integer.parseInt(id.split("[U]")[1]);
            num++;
            return (String.format("U%03d",num));
        }else {
            return ("U001");
        }
    }

    @Override
    public Boolean saveUser(UserDto dto) {
        return userDao.save(new User(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPassword()
        ));
    }

    @Override
    public List<UserDto> getAll() {
        List<User> all = userDao.getAll();
        List<UserDto> dtoList = new ArrayList<>();
        for (User entity : all) {
            dtoList.add(new UserDto(
                    entity.getId(),
                    entity.getName(),
                    entity.getEmail(),
                    entity.getPassword()
            ));
        }
        return dtoList;
    }

    @Override
    public Boolean deleteUser(String value) {
        return userDao.delete(value);
    }

    @Override
    public UserDto getUser(String value) {
        User user = userDao.getUser(value);
        try {
            return new UserDto(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPassword()
            );

        }catch (NullPointerException e){
            return null;
        }
    }
}
