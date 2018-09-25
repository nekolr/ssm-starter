package com.nekolr.upms.server.util;

import com.nekolr.upms.api.dto.ResourceDTO;
import com.nekolr.upms.server.vo.resource.Menu;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class MenuUtils {

    /**
     * ResourceDTO 转 VO BeanCopier
     */
    private static final BeanCopier dto2VoBeanCopier = BeanCopier.create(ResourceDTO.class, Menu.class, false);

    private MenuUtils() {

    }

    /**
     * 将 DTO 集合转成 VO 集合
     *
     * @param menus
     * @return
     */
    public static List<Menu> dto2Vo(List<ResourceDTO> menus) {
        if (menus != null) {
            List<Menu> menuList = menus.stream().map(resourceDTO -> {
                Menu menu = new Menu();
                dto2VoBeanCopier.copy(resourceDTO, menu, null);
                // 名称不同手动赋值
                menu.setPath(resourceDTO.getUri());
                return menu;
            }).collect(Collectors.toList());
            return menuList;
        }
        return null;
    }

    /**
     * 将列表转换成树形
     * <p>
     * 时间复杂度：O(N)
     *
     * @param menus 未形成树形结构的菜单列表
     * @return 形成树形结构的菜单列表
     */
    public static List<Menu> menus2Tree(List<Menu> menus) {
        if (menus != null) {
            long startTime = System.currentTimeMillis();
            Map<String, List<Menu>> map = new HashMap<>((int) (menus.size() / 0.75F + 1.0F));
            List<Menu> result = new ArrayList<>();
            menus.forEach(menu -> {
                map.put(menu.getId(), map.get(menu.getId()) == null ? new ArrayList<>() : map.get(menu.getId()));
                menu.setChildren(map.get(menu.getId()));
                if (menu.getParentId() == null) {
                    result.add(menu);
                } else {
                    map.put(menu.getParentId(), map.get(menu.getParentId()) == null ? new ArrayList<>() : map.get(menu.getParentId()));
                    map.get(menu.getParentId()).add(menu);
                    map.put(menu.getParentId(), map.get(menu.getParentId()));
                }
            });
            long endTime = System.currentTimeMillis();
            log.info("convert menu list to tree completed. total time: {} ms", (endTime - startTime));
            return result;
        }
        return null;
    }
}
