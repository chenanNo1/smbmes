package cn.smbms.service;

import java.util.List;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;

public interface UserService {
	User findByName(String name);// 登录

	List<Role> findByRoleAll();// 查询角色表全部信息

	Integer deleteUser(Integer id);// 删除

	Integer countByQuery(String userName, Integer roleId);// 总条数

	List<User> findByQuery(String userName, Integer roleId, Integer index,
			Integer size);// 动态查询+分页

	User findByid(Integer id);// 通过id获取用户信息

	Integer updateUser(User user);// 修改

	Integer saveUser(User user);// 添加
}