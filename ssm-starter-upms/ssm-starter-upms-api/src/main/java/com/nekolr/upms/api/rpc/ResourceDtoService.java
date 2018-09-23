package com.nekolr.upms.api.rpc;

import com.nekolr.upms.api.dto.ResourceDTO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * 资源 DTO 服务类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-23
 */
public interface ResourceDtoService {
    /**
     * <p>
     * 插入一条记录（选择字段，策略插入）
     * </p>
     *
     * @param entity 实体对象
     * @return
     */
    boolean save(ResourceDTO entity);

    /**
     * <p>
     * 插入（批量），该方法不适合 Oracle
     * </p>
     *
     * @param entityList 实体对象集合
     * @return
     */
    boolean saveBatch(Collection<ResourceDTO> entityList);

    /**
     * <p>
     * 插入（批量）
     * </p>
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     * @return
     */
    boolean saveBatch(Collection<ResourceDTO> entityList, int batchSize);

    /**
     * <p>
     * TableId 注解存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return
     */
    boolean saveOrUpdate(ResourceDTO entity);

    /**
     * <p>
     * 批量修改插入
     * </p>
     *
     * @param entityList 实体对象集合
     * @return
     */
    boolean saveOrUpdateBatch(Collection<ResourceDTO> entityList);

    /**
     * <p>
     * 批量修改插入
     * </p>
     *
     * @param entityList 实体对象集合
     * @param batchSize  每次的数量
     * @return
     */
    boolean saveOrUpdateBatch(Collection<ResourceDTO> entityList, int batchSize);

    /**
     * <p>
     * 根据 ID 选择修改
     * </p>
     *
     * @param entity 实体对象
     * @return
     */
    boolean updateById(ResourceDTO entity);

    /**
     * <p>
     * 根据ID 批量更新
     * </p>
     *
     * @param entityList 实体对象集合
     * @return
     */
    boolean updateBatchById(Collection<ResourceDTO> entityList);

    /**
     * <p>
     * 根据ID 批量更新
     * </p>
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     * @return
     */
    boolean updateBatchById(Collection<ResourceDTO> entityList, int batchSize);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @return
     */
    ResourceDTO getById(Serializable id);

    /**
     * <p>
     * 查询（根据ID 批量查询）
     * </p>
     *
     * @param idList 主键ID列表
     * @return
     */
    Collection<ResourceDTO> listByIds(Collection<? extends Serializable> idList);

    /**
     * <p>
     * 查询（根据 columnMap 条件）
     * </p>
     *
     * @param columnMap 表字段 map 对象
     * @return
     */
    Collection<ResourceDTO> listByMap(Map<String, Object> columnMap);
}
