package com.szxy.provider.service.impl;

import com.szxy.mapper.UserMapper;
import com.szxy.pojo.User;
import com.szxy.provider.service.ProviderUserService;
import com.szxy.utils.MD5;
import com.szxy.utils.UserGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProviderUserServiceImpl  implements ProviderUserService {

    @Autowired
    private UserMapper userMapper;

    public boolean userCheckService(String phone) {
        User user = this.userMapper.selectByUserPhoneMapper(phone);
        if(user != null){
            return true;
        }
        return false;
    }

    @Override
    public User findSellerByIdService(Integer id) {
        User user = this.userMapper.selSellerInfoByIdMapper(id);
        if(user != null){
            return user;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public User userLoginService(String phone, String password) {
        User user = this.userMapper.selectByUserPhoneMapper(phone);
        if(user != null){
            String md5 = MD5.md5(password);
            if(user.getPassword().equals(md5)){
                return user;
            }
        }
        return null;
    }


    @Override
    @Transactional
    public int userRegisterService(String username, String phone, String password) {
        return this.userMapper.inserUserMapper(username,phone, MD5.md5(password));
    }

    @Override
    @Transactional
    public void updateUserInfoService(User user) {
        this.userMapper.updateUserInfoMapper(user);
    }

    @Override
    public UserGrid findUserByPaginationService(Integer pageNum, Integer pageSize) {
        Integer start = (pageNum-1)*pageSize;
        Integer end = pageSize;
        List<User> userList = this.userMapper.selUserByPaginationMapper(start, end);
        UserGrid userGrid = new UserGrid();
        userGrid.setCurrent(pageNum);
        userGrid.setRowCount(pageSize);
        userGrid.setRows(userList);
        Integer count = this.userMapper.selCount();
        userGrid.setTotal(count);
        return userGrid;
    }

    @Override
    public User findUserByPhoneService(String phone) {
        return this.userMapper.selectByUserPhoneMapper(phone);
    }

    @Override
    public void addUserService(User user) {
        this.userMapper.addUserMapper(user);
    }


}
