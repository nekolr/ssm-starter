package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.entity.User;
import org.springframework.stereotype.Service;
import com.nekolr.upms.api.rpc.UserService;
import net.sf.cglib.beans.BeanCopier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.nekolr.upms.api.dto.UserDTO;
import com.nekolr.upms.api.rpc.UserDtoService;

import javax.annotation.Resource;

/**
 * <p>
 * 用户 DTO 服务实现类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-09
 */
@Service
public class UserDtoServiceImpl implements UserDtoService {

    private final BeanCopier dto2DoBeanCopier = BeanCopier.create(UserDTO.class, User.class, false);
    private final BeanCopier do2DtoBeanCopier = BeanCopier.create(User.class, UserDTO.class, false);

    @Resource
    private UserService userService;

    /**
     * DTO 转 DO
     *
     * @param entity DTO 对象
     * @return
     */
    private User dto2Do(UserDTO entity) {
        User target = new User();
        dto2DoBeanCopier.copy(entity, target, null);
        return target;
    }

    /**
     * DO 转 DTO
     *
     * @param entity DO 对象
     * @return
     */
    private UserDTO do2Dto(User entity) {
        UserDTO target = new UserDTO();
        do2DtoBeanCopier.copy(entity, target, null);
        return target;
    }

    @Override
    public boolean save(UserDTO entity) {
        return userService.save(dto2Do(entity));
    }

    @Override
    public boolean saveBatch(Collection<UserDTO> entityList) {
        return this.saveBatch(entityList, 30);
    }

    @Override
    public boolean saveBatch(Collection<UserDTO> entityList, int batchSize) {
        List<User> targetList = new ArrayList<>(entityList.size());
        entityList.forEach(e -> targetList.add(dto2Do(e)));
        return userService.saveBatch(targetList, batchSize);
    }

    @Override
    public boolean saveOrUpdate(UserDTO entity) {
        return userService.saveOrUpdate(dto2Do(entity));
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<UserDTO> entityList) {
        return this.saveOrUpdateBatch(entityList, 30);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<UserDTO> entityList, int batchSize) {
        List<User> targetList = new ArrayList<>(entityList.size());
        entityList.forEach(e -> targetList.add(dto2Do(e)));
        return userService.saveOrUpdateBatch(targetList, batchSize);
    }

    @Override
    public boolean updateById(UserDTO entity) {
        return userService.updateById(dto2Do(entity));
    }

    @Override
    public boolean updateBatchById(Collection<UserDTO> entityList) {
        return this.updateBatchById(entityList, 30);
    }

    @Override
    public boolean updateBatchById(Collection<UserDTO> entityList, int batchSize) {
        List<User> targetList = new ArrayList<>(entityList.size());
        entityList.forEach(e -> targetList.add(dto2Do(e)));
        return userService.updateBatchById(targetList, batchSize);
    }

    @Override
    public UserDTO getById(Serializable id) {
        return do2Dto(userService.getById(id));
    }

    @Override
    public Collection<UserDTO> listByIds(Collection<? extends Serializable> idList) {
        Collection<User> list = userService.listByIds(idList);
        List<UserDTO> result = new ArrayList<>(list.size());
        if (list.size() != 0) {
            list.forEach(e -> result.add(do2Dto(e)));
        }
        return result;
    }

    @Override
    public Collection<UserDTO> listByMap(Map<String, Object> columnMap) {
        Collection<User> list = userService.listByMap(columnMap);
        List<UserDTO> result = new ArrayList<>(list.size());
        if (list.size() != 0) {
            list.forEach(e -> result.add(do2Dto(e)));
        }
        return result;
    }
}
