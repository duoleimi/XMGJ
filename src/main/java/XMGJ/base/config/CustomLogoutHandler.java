package XMGJ.base.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.zzy.base.utils.JsonUtil;

import XMGJ.base.model.CommonResult;
import lombok.SneakyThrows;


public class CustomLogoutHandler extends SecurityContextLogoutHandler {

	@Override
	@SneakyThrows
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {

		String msg = "登陆超时或在其他端登陆";
		String body = JsonUtil.bean2Json(CommonResult.error(0, msg));
		response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getOutputStream().write(body.getBytes());
		response.getOutputStream().flush();
		
		super.logout(request, response, authentication);
	}

}
