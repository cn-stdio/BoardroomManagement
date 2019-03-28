package com.ectocyst.controller;

import com.ectocyst.mapper.DoorMapper;
import com.ectocyst.mapper.EmployeeMapper;
import com.ectocyst.model.Door;
import com.ectocyst.model.Employee;
import com.ectocyst.service.LoginService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Seagull_gby
 * @date 2019/3/23 13:31
 * Description: 登录
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DoorMapper doorMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录并保存跳转URL
     * @param request 请求域
     * @return 跳转页面
     */
    /*@RequestMapping("/login")
    public String login(HttpServletRequest request) {

        request.getSession().setAttribute("url",request.getHeader("Referer"));

        return "login";
    }*/

    @ResponseBody
    @PostMapping("/loginCheck")
    public JSONObject loginCheck(HttpServletRequest request) {
        JSONObject lc = new JSONObject();

        long jobId = Long.valueOf(request.getParameter("jobId"));

        lc = loginService.jobIdRegisterCheck(jobId);

        return lc;
    }

    @ResponseBody
    @GetMapping("/loginError")
    public JSONObject loginError() {
        JSONObject lc = new JSONObject();

        lc.put("code", 200);
        lc.put("msg", "登录失败！");
        return lc;
    }

    @ResponseBody
    @GetMapping("/loginJump")
    public JSONObject loginJump() {
        JSONObject lc = new JSONObject();

        lc.put("code", 200);
        lc.put("msg", "success");
        return lc;
    }

    /*@ResponseBody
    @RequestMapping("/loginSuccess")
    public JSONObject loginSuccessCheck(HttpServletRequest request) {
        JSONObject lsc = new JSONObject();

        long jobId = Long.valueOf(request.getParameter("jobId"));
        String password = request.getParameter("password");

        lsc = loginService.loginCheck(jobId, password);

        return lsc;
    }*/

    @RequestMapping("/doLogin")
    public JSONObject doLogin(@RequestParam("username") String username, @RequestParam("password") String password){
        Employee user = employeeMapper.queryEmployByJobIdAndRole(Long.valueOf(username));

        JSONObject aa = new JSONObject();

        //如果用户不存在则抛出异常
        if(user==null){
            throw new UsernameNotFoundException("没有当前用户");
        }
        else {
            aa.put("code", 200);
            aa.put("msg", "success");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String roleName = employeeMapper.queryUserRole(employeeMapper.queryRoleId(user.getJobId()));
            //如果用户存在且用户的密码相同，则在SecurityContextHolder.getContext().setAuthentication()放入authentication
            if(encoder.matches(password, user.getPassword())){
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList(roleName));
                Authentication authentication = authenticationManager.authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return aa;
            }
        }

        aa.put("code", 500);
        aa.put("msg", "false");
        return aa;
    }

    @ResponseBody
    @RequestMapping("/faceDetection")
    public void faceDetec(HttpServletRequest request) {

        loginService.faceDetection(request.getParameter("base"));

    }

    @ResponseBody
    @GetMapping("/door")
    public int door() {
        int flag = 0;
        Door door = doorMapper.queryDoor();

        if(door.getFlag()==0 && door.getSta()==1) {
            flag = 1;
            door.setSta(0);
        } else if(door.getFlag()==1 && door.getSta()==0) {
            flag = 1;
            door.setFlag(0);
        }

        return flag;
    }
}
