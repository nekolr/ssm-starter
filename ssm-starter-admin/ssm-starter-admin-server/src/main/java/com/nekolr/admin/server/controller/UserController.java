package com.nekolr.admin.server.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nekolr.admin.api.entity.User;
import com.nekolr.admin.api.rpc.UserService;
import com.nekolr.admin.server.vo.user.UserVO;
import com.nekolr.common.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author nekolr
 * @since 2018-09-08
 */
@RestController
@RequestMapping("/users")
@Api
public class UserController {

    private final BeanCopier do2VoBeanCopier = BeanCopier.create(User.class, UserVO.class, false);

    @Reference
    private UserService userService;

    @GetMapping()
    @ApiOperation("根据条件获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "当前页号", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机号", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "查询条件：开始日期", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "查询条件：结束日期", paramType = "query")
    })
    public ResultBean<Page<UserVO>> getUserListByConditions(@NotNull @Digits(integer = 4, fraction = 0) Integer pageNumber,
                                                            @NotNull @Digits(integer = 3, fraction = 0) Integer pageSize,
                                                            String username, String mobile, String email, String sex,
                                                            String status, String startDate, String endDate) {
        // 查询条件组装
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
        if (!StringUtils.isEmpty(username)) {
            queryWrapper.like(User::getUsername, username);
        }
        if (!StringUtils.isEmpty(mobile)) {
            queryWrapper.like(User::getMobile, mobile);
        }
        if (!StringUtils.isEmpty(email)) {
            queryWrapper.like(User::getEmail, email);
        }
        if (!StringUtils.isEmpty(sex)) {
            queryWrapper.eq(User::getSex, sex);
        }
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq(User::getStatus, status);
        }
        if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            queryWrapper.between(User::getCreateTime, startDate, endDate);
        }

        // 执行查询
        IPage<User> page = userService.page(new Page<>(pageNumber, pageSize), queryWrapper);

        List<User> userList = page.getRecords();

        // DO 转换成 VO
        List<UserVO> userVOList = userList.stream().map(user -> {
            UserVO userVO = new UserVO();
            do2VoBeanCopier.copy(user, userVO, null);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            userVO.setCreateTime(formatter.format(user.getCreateTime()));
            userVO.setUpdateTime(formatter.format(user.getUpdateTime()));
            return userVO;
        }).collect(Collectors.toList());

        // 组装 Page 对象
        Page<UserVO> voPage = new Page<>();
        voPage.setRecords(userVOList);
        voPage.setCurrent(page.getCurrent());
        voPage.setSize(page.getSize());
        voPage.setTotal(page.getTotal());

        return new ResultBean<>().setData(voPage);
    }
}

