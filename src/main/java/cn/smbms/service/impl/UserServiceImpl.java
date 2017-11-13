package cn.smbms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.smbms.mapper.UserMapper;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper uMapper;

	public void setuMapper(UserMapper uMapper) {
		this.uMapper = uMapper;
	}

	public User findByName(String name) {
		return uMapper.findByName(name);
	}

	public List<Role> findByRoleAll() {
		return uMapper.findByRoleAll();
	}

	public Integer deleteUser(Integer id) {
		return uMapper.deleteUser(id);
	}

	public Integer countByQuery(String userName, Integer roleId) {
		return uMapper.countByQuery(userName, roleId);
	}

	public List<User> findByQuery(String userName, Integer roleId,
			Integer index, Integer size) {
		return uMapper.findByQuery(userName, roleId, index, size);
	}

	public User findByid(Integer id) {
		return uMapper.findByid(id);
	}

	public Integer updateUser(User user) {
		return uMapper.updateUser(user);
	}

	public Integer saveUser(User user) {
		return uMapper.saveUser(user);
	}
}
