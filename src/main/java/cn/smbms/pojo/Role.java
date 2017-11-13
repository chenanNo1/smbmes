package cn.smbms.pojo;

import java.io.Serializable;

/// 角色表实体类
public class Role implements Serializable {
	private static final long serialVersionUID = -2732116927738761785L;
	protected Integer id;// 角色Id
	protected String roleCole;// 角色编号
	protected String roleName;// 角色名称

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleCole() {
		return roleCole;
	}

	public void setRoleCole(String roleCole) {
		this.roleCole = roleCole;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Role() {
		super();
	}

	public Role(Integer id, String roleCole, String roleName) {
		super();
		this.id = id;
		this.roleCole = roleCole;
		this.roleName = roleName;
	}
}
