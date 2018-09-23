package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.entity.Resource;
import org.springframework.stereotype.Service;
import com.nekolr.upms.api.rpc.ResourceService;
import net.sf.cglib.beans.BeanCopier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.nekolr.upms.api.dto.ResourceDTO;
import com.nekolr.upms.api.rpc.ResourceDtoService;

/**
 * <p>
 * 资源 DTO 服务实现类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-23
 */
@Service
public class ResourceDtoServiceImpl implements ResourceDtoService {

    private final BeanCopier dto2DoBeanCopier = BeanCopier.create(ResourceDTO.class, Resource.class, false);
    private final BeanCopier do2DtoBeanCopier = BeanCopier.create(Resource.class, ResourceDTO.class, false);

    @javax.annotation.Resource
    private ResourceService resourceService;

    /**
     * DTO 转 DO
     *
     * @param entity DTO 对象
     * @return
     */
    private Resource dto2Do(ResourceDTO entity) {
        Resource target = new Resource();
        dto2DoBeanCopier.copy(entity, target, null);
        return target;
    }

    /**
     * DO 转 DTO
     *
     * @param entity DO 对象
     * @return
     */
    private ResourceDTO do2Dto(Resource entity) {
        ResourceDTO target = new ResourceDTO();
        do2DtoBeanCopier.copy(entity, target, null);
        return target;
    }

    @Override
    public boolean save(ResourceDTO entity) {
        return resourceService.save(dto2Do(entity));
    }

    @Override
    public boolean saveBatch(Collection<ResourceDTO> entityList) {
        return this.saveBatch(entityList, 30);
    }

    @Override
    public boolean saveBatch(Collection<ResourceDTO> entityList, int batchSize) {
        List<Resource> targetList = new ArrayList<>(entityList.size());
        entityList.forEach(e -> targetList.add(dto2Do(e)));
        return resourceService.saveBatch(targetList, batchSize);
    }

    @Override
    public boolean saveOrUpdate(ResourceDTO entity) {
        return resourceService.saveOrUpdate(dto2Do(entity));
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<ResourceDTO> entityList) {
        return this.saveOrUpdateBatch(entityList, 30);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<ResourceDTO> entityList, int batchSize) {
        List<Resource> targetList = new ArrayList<>(entityList.size());
        entityList.forEach(e -> targetList.add(dto2Do(e)));
        return resourceService.saveOrUpdateBatch(targetList, batchSize);
    }

    @Override
    public boolean updateById(ResourceDTO entity) {
        return resourceService.updateById(dto2Do(entity));
    }

    @Override
    public boolean updateBatchById(Collection<ResourceDTO> entityList) {
        return this.updateBatchById(entityList, 30);
    }

    @Override
    public boolean updateBatchById(Collection<ResourceDTO> entityList, int batchSize) {
        List<Resource> targetList = new ArrayList<>(entityList.size());
        entityList.forEach(e -> targetList.add(dto2Do(e)));
        return resourceService.updateBatchById(targetList, batchSize);
    }

    @Override
    public ResourceDTO getById(Serializable id) {
        return do2Dto(resourceService.getById(id));
    }

    @Override
    public Collection<ResourceDTO> listByIds(Collection<? extends Serializable> idList) {
        Collection<Resource> list = resourceService.listByIds(idList);
        List<ResourceDTO> result = new ArrayList<>(list.size());
        if (list.size() != 0) {
            list.forEach(e -> result.add(do2Dto(e)));
        }
        return result;
    }

    @Override
    public Collection<ResourceDTO> listByMap(Map<String, Object> columnMap) {
        Collection<Resource> list = resourceService.listByMap(columnMap);
        List<ResourceDTO> result = new ArrayList<>(list.size());
        if (list.size() != 0) {
            list.forEach(e -> result.add(do2Dto(e)));
        }
        return result;
    }
}
