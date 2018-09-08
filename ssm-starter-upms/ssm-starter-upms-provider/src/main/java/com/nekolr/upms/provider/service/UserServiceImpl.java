package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.entity.UserDO;
import com.nekolr.upms.provider.dao.UserMapper;
import com.nekolr.upms.api.rpc.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import net.sf.cglib.beans.BeanCopier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.nekolr.upms.api.dto.UserDTO;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    private final BeanCopier dto2DoBeanCopier = BeanCopier.create(UserDTO.class, UserDO.class, false);
    private final BeanCopier do2DtoBeanCopier = BeanCopier.create(UserDO.class, UserDTO.class, false);

    private UserDO dto2Do(UserDTO entity) {
        UserDO userDO = new UserDO();
        dto2DoBeanCopier.copy(entity, userDO, null);
        return userDO;
    }

    private UserDTO do2Dto(UserDO entity) {
        UserDTO userDTO = new UserDTO();
        do2DtoBeanCopier.copy(entity, userDTO, null);
        return userDTO;
    }

    @Override
    public boolean saveDTO(UserDTO entity) {
        return super.save(dto2Do(entity));
    }

    @Override
    public boolean saveBatchDTO(Collection<UserDTO> entityList) {
        return this.saveBatchDTO(entityList, 30);
    }

    @Override
    public boolean saveBatchDTO(Collection<UserDTO> entityList, int batchSize) {
        List<UserDO> targetList = new ArrayList<>(entityList.size());
        entityList.forEach(e -> targetList.add(dto2Do(e)));
        return super.saveBatch(targetList, batchSize);
    }

    @Override
    public boolean saveOrUpdateDTO(UserDTO entity) {
        return super.saveOrUpdate(dto2Do(entity));
    }

    @Override
    public boolean saveOrUpdateBatchDTO(Collection<UserDTO> entityList) {
        return this.saveOrUpdateBatchDTO(entityList, 30);
    }

    @Override
    public boolean saveOrUpdateBatchDTO(Collection<UserDTO> entityList, int batchSize) {
        List<UserDO> targetList = new ArrayList<>(entityList.size());
        entityList.forEach(e -> targetList.add(dto2Do(e)));
        return super.saveOrUpdateBatch(targetList, batchSize);
    }

    @Override
    public boolean updateByIdDTO(UserDTO entity) {
        return super.updateById(dto2Do(entity));
    }

    @Override
    public boolean updateBatchByIdDTO(Collection<UserDTO> entityList) {
        return this.updateBatchByIdDTO(entityList, 30);
    }

    @Override
    public boolean updateBatchByIdDTO(Collection<UserDTO> entityList, int batchSize) {
        List<UserDO> targetList = new ArrayList<>(entityList.size());
        entityList.forEach(e -> targetList.add(dto2Do(e)));
        return super.updateBatchById(targetList, batchSize);
    }

    @Override
    public UserDTO getByIdDTO(Serializable id) {
        return do2Dto(super.getById(id));
    }

    @Override
    public Collection<UserDTO> listByIdsDTO(Collection<? extends Serializable> idList) {
        Collection<UserDO> list = super.listByIds(idList);
        List<UserDTO> result = new ArrayList<>(list.size());
        if (list.size() != 0) {
            list.forEach(e -> result.add(do2Dto(e)));
        }
        return result;
    }

    @Override
    public Collection<UserDTO> listByMapDTO(Map<String, Object> columnMap) {
        Collection<UserDO> list = super.listByMap(columnMap);
        List<UserDTO> result = new ArrayList<>(list.size());
        if (list.size() != 0) {
            list.forEach(e -> result.add(do2Dto(e)));
        }
        return result;
    }
}
