package cn.smbms.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.UserService;
import cn.smbms.tools.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
public class UserController {
	@Autowired
	private UserService uService;

	public void setuService(UserService uService) {
		this.uService = uService;
	}

	@RequestMapping(value = "login.html", method = RequestMethod.GET)
	public String login() {
		return "../login";
	}

	// /登录
	@RequestMapping(value = "login.html", method = RequestMethod.POST)
	public String login(Model model, @RequestParam("userCode") String userCode,
			@RequestParam(value = "userPassword") String userPassword,
			HttpSession session) {
		User user = uService.findByName(userCode);
		if (user != null) {
			if (user.getUserPassword().equals(userPassword)) {
				if (session.getAttribute("USER_CODE") != null) {
					session.removeAttribute("USER_CODE");
				}
				session.setAttribute("USER_CODE", user);
				session.setMaxInactiveInterval(60000);
				return "frame";
			} else {
				model.addAttribute("error", "抱歉,账号或密码不正确!");
				return "../login";
			}
		} else {
			model.addAttribute("error", "抱歉,账号或密码不正确!");
			return "../login";
		}
	}

	// / 展示
	@RequestMapping(value = "user.html", method = RequestMethod.GET)
	public String userShow(
			Model model,
			@RequestParam(value = "queryname", required = false) String queryname,
			@RequestParam(value = "queryUserRole", required = false) Integer queryUserRole,
			@RequestParam(value = "totalPageCount", required = false) Integer totalPageCount,
			@RequestParam(value = "totalCount", required = false) Integer totalCount,
			@RequestParam(value = "pageIndex", required = false) Integer pageIndex) {
		totalCount = uService.countByQuery(queryname, queryUserRole);
		List<Role> lists = uService.findByRoleAll();
		Page<User> page = new Page<User>();
		page.setCount(totalCount);
		if (totalCount % page.getSize() == 0) {
			totalPageCount = totalCount / page.getSize();
		} else if (totalCount % page.getSize() != 0) {
			totalPageCount = totalCount / page.getSize() + 1;
		}
		if ("".equals(pageIndex) || null == pageIndex) {
			pageIndex = 1;
		}
		page.setIndex(pageIndex);
		page.setTotal(totalPageCount);
		List<User> list = uService.findByQuery(queryname, queryUserRole,
				page.getSize() * (pageIndex - 1), page.getSize());
		model.addAttribute("userList", list);
		model.addAttribute("roleList", lists);
		model.addAttribute("queryUserName", queryname);
		model.addAttribute("queryUserRole", queryUserRole);
		model.addAttribute("totalCount", page.getCount());
		model.addAttribute("totalPageCount", page.getTotal());
		model.addAttribute("currentPageNo", page.getIndex());
		return "userlist";
	}

	// / 删除
	@RequestMapping(value = "userdel.json", method = RequestMethod.POST)
	@ResponseBody
	public Object del(Model model,
			@RequestParam(value = "uid", required = false) Integer uid) {
		Integer result = uService.deleteUser(uid);
		JSONObject json = new JSONObject();
		if (result == 0) {
			json.put("delResult", "true");
		} else {
			json.put("delResult", "false");
		}
		return json;
	}

	// / 进入用户新增界面
	@RequestMapping(value = "userAdd.html", method = RequestMethod.GET)
	public String userAdd() {
		return "useradd";
	}

	// / roleList跳转添加页面的时候实现角色列表的展示
	@RequestMapping("role.json")
	@ResponseBody
	public Object roleList() {
		List<Role> list = uService.findByRoleAll();
		return JSON.toJSONString(list);
	}

	@RequestMapping("getRoleCode.json")
	@ResponseBody
	public Object roleList(@RequestParam("userCode") String userCode) {
		User user = uService.findByName(userCode);
		JSONObject jsObject = new JSONObject();
		if (user != null) {
			jsObject.put("userCode", "exist");
		} else {
			jsObject.put("userCode", "success");
		}
		return jsObject;
	}

	// / 查看详情
	@RequestMapping(value = "userview.html", method = RequestMethod.GET)
	public String xiangQing(
			@RequestParam(value = "uid", required = false) Integer uid,
			Model model) {
		User user = uService.findByid(uid);
		model.addAttribute("user", user);
		return "userview";
	}

	// / 更新
	@RequestMapping(value = "usermodify.html", method = RequestMethod.GET)
	public String usermodify(Model model,
			@RequestParam(value = "uid", required = false) String uid) {
		User user = uService.findByid(Integer.parseInt(uid));
		model.addAttribute("user", user);
		return "usermodify";
	}

	// / 修改用户
	@RequestMapping(value = "userUpdate.html", method = RequestMethod.POST)
	public String userUpdate(Model model, User user, HttpSession session) {
		if (session.getAttribute("USER_CODE") != null) {
			User u = (User) session.getAttribute("USER_CODE");
			user.setModifyBy(u.getId());
		} else {
			user.setModifyBy(1);
		}
		user.setModifyDate(new Date());
		Integer id = uService.updateUser(user);
		if (id > 0) {
			return "userlist";
		} else {
			model.addAttribute("message", "抱歉,更新失败!");
			return "usermodify";
		}
	}

	// / 添加用户
	@RequestMapping(value = "userAdd.html", method = RequestMethod.POST)
	public String userAdd(Model model, User user, HttpSession session) {
		if (session.getAttribute("USER_CODE") != null) {
			User u = (User) session.getAttribute("USER_CODE");
			user.setCreatedBy(u.getId());
		} else {
			user.setCreatedBy(1);
		}
		user.setCreationDate(new Date());
		Integer id = uService.saveUser(user);
		if (id > 0) {
			return "redirect:user.html";
		} else {
			model.addAttribute("message", "抱歉,添加失败!");
			return "useradd";
		}
	}

	// / 修改密码页面
	@RequestMapping("pwd.html")
	public String pwd() {
		return "pwdmodify";
	}

	// / 供应商页面
	@RequestMapping("provider.html")
	public String provider() {
		return "providerlist";
	}

	// / 订单管理页面
	@RequestMapping("bill.html")
	public String bill() {
		return "billlist";
	}
}
