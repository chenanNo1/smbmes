package cn.smbms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;

public interface UserMapper {
	User findByName(@Param("name") String name);// 按姓名查询用户

	List<User> findByQuery(@Param("userName") String userName,
			@Param("roleId") Integer roleId, @Param("index") Integer index,
			@Param("size") Integer size);// 动态查询+分页

	Integer saveUser(User user);// 添加

	Integer updateUser(User user);// 修改

	Integer deleteUser(@Param("id") Integer id);// 删除

	List<Role> findByRoleAll();// 查询角色表全部信息

	User findByid(@Param("id") Integer id);// 通过id获取用户信息

	Integer countByQuery(@Param("userName") String userName,
			@Param("roleId") Integer roleId);// 总条数
}
