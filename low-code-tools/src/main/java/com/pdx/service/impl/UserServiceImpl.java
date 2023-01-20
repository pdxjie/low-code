package com.pdx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pdx.constant.Constant;
import com.pdx.entity.Tags;
import com.pdx.entity.User;
import com.pdx.entity.dto.UserDetailDto;
import com.pdx.mapper.TagsMapper;
import com.pdx.mapper.UserMapper;
import com.pdx.service.UserService;
import com.pdx.utils.AvatarUtils;
import com.pdx.utils.JwtTokenUtil;
import com.pdx.utils.PasswordUtils;
import com.pdx.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 派大星
 * @Date: 2023/01/19 2023/1/19
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private TagsMapper tagsMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void insertUser(User user) {
        int result = userMapper.insert(user);
        if (result != 1){
            try {
                throw new Exception("注册失败");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 登录
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,Object> loginOperate(String email, String password) throws Exception {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        User userOne = userMapper.selectOne(wrapper);
        if (null == userOne){
            throw new Exception("用户信息不存在");
        }
        String originPassword = userOne.getPassword();
        String salt = userOne.getSalt();
        boolean matches = PasswordUtils.matches(salt, password, originPassword);
        if (!matches){
            throw new Exception("密码错误");
        }
        //生成token
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userOne.getId());
        String accessToken = JwtTokenUtil.getAccessToken(userOne.getId(), claims);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("accessToken",accessToken);
        return resultMap;
    }

    @Override
    public Map<String, Object> userDetailInfo(String userId) {
        User user = userMapper.selectById(userId);
        List<Tags> tags = tagsMapper.selectList(new QueryWrapper<Tags>().eq("user_id", userId));
        UserDetailDto userDetailDto = new UserDetailDto();
        BeanUtils.copyProperties(user,userDetailDto);
        userDetailDto.setUserId(user.getId());
        userDetailDto.setTags(tags);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("userDetail",userDetailDto);
        return resultMap;
    }

    /**
     * 注册
     * @param email
     * @param password
     * @param code
     * @return
     */
    @Override
    public Map<String,Object> registerOperate(String email, String password, String code) {
        Map<String,Object> resultMap = new HashMap<>();
        String registerCode = (String) redisTemplate.opsForValue().get(Constant.REGISTER_CODE + email);
        if (StringUtils.isBlank(registerCode)){
            try {
                throw new Exception("验证码已失效");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //判断邮箱是否已存在
        User userOne = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        if (null != userOne){
            try {
                throw new Exception("该邮箱已存在");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            String salt = PasswordUtils.getSalt();
            String encode = PasswordUtils.encode(password, salt);
            User user = new User();
            String userId = IdWorker.getIdStr();
            user.setId(userId);
            user.setAvatar(AvatarUtils.generatorAvatar());
            user.setEmail(email);
            user.setSalt(salt);
            user.setNickName("用户"+IdWorker.get32UUID().substring(0,6));
            user.setPassword(encode);
            user.setRegisterTime(new Timestamp(new Date().getTime()));
            user.setUpdateTime(new Timestamp(new Date().getTime()));
            int result = userMapper.insert(user);
            if (result != 1){
                try {
                    throw new Exception("注册失败");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                //生成token
                Map<String,Object> claims = new HashMap<>();
                claims.put("userId",userId);
                String accessToken = JwtTokenUtil.getAccessToken(userId, claims);
                resultMap.put("accessToken",accessToken);
                resultMap.put("isSuccess",result == 1);
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getCodeInfo(String email) {
        String code = IdWorker.getIdStr().substring(0, 8);
        redisTemplate.opsForValue().set(Constant.REGISTER_CODE+email,code,60, TimeUnit.SECONDS);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",code);
        return resultMap;
    }
}
