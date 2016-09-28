package com.xuanru.controller.user;

import com.xuanru.dto.req.user.LoginFormDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserCtrl {

	private static final Logger logger = LoggerFactory.getLogger(UserCtrl.class);

	/**
	 * 
	 * desc:将form表单内容封装成POJO
	 * <p>
	 * 创建人：Liaoxf , 2015-12-6 下午3:58:10
	 * </p>
	 * 
	 * @param loginBo
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(LoginFormDto loginBo, ModelMap model, HttpServletRequest request) {
		logger.info("username=" + loginBo.getUsername());
		if ("test".equals(loginBo.getUsername()) && "111111".equals(loginBo.getPwd())) {
			return "redirect:upload";
		} else {
			model.put("username", loginBo.getUsername());
			model.put("errMsg", "用户名或密码错误");
			return "login";
		}
	}

}
