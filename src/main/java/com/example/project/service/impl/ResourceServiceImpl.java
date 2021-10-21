package com.example.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.project.entity.Resource;
import com.example.project.dao.ResourceMapper;
import com.example.project.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.project.vo.ResourceVO;
import com.example.project.vo.TreeVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author zcz
 * @since 2021-09-27
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    /**
     * 根据角色的ID，查询该角色所具有的资源
     * @param roleId
     * @return
     */
    @Override
    public List<ResourceVO> listResourceByRoleId(long roleId) {
        QueryWrapper<Resource> query = Wrappers.<Resource>query();
        query.eq("rr.role_id", roleId).isNull("re.parent_id").orderByAsc("re.sort");
        List<ResourceVO> resourceVOS = baseMapper.listResource(query);
        resourceVOS.forEach(resourceVO -> {
            Long resourceId = resourceVO.getResourceId();
            QueryWrapper<Resource> queryWrapper = Wrappers.<Resource>query();
            queryWrapper.eq("rr.role_id", roleId)
                    .eq("re.parent_id", resourceId).orderByAsc("re.sort");
            List<ResourceVO> resourceVOList = baseMapper.listResource(queryWrapper);
            if (CollectionUtils.isNotEmpty(resourceVOList)) {
                resourceVO.setSubs(resourceVOList);
            }
        });
        return resourceVOS;
    }

    /**
     * 查询系统资源，供前端使用渲染
     * @return
     * @param roleId
     * @param flag
     */
    @Override
    public List<TreeVO> listResource(Long roleId, Integer flag) {
        if (roleId == null) {
            LambdaQueryWrapper wrapper = Wrappers.<Resource>lambdaQuery()
                    .isNull(Resource::getParentId).orderByAsc(Resource::getSort);
            List<Resource> resources = list(wrapper);
            List<TreeVO> treeVOS = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(resources)) {
                treeVOS = resources.stream().map(r -> {
                    TreeVO treeVO = new TreeVO();
                    treeVO.setId(r.getResourceId());
                    treeVO.setTitle(r.getResourceName());

                    LambdaQueryWrapper subWrapper = Wrappers.<Resource>lambdaQuery()
                            .eq(Resource::getParentId, r.getResourceId()).orderByAsc(Resource::getSort);
                    List<Resource> subResources = list(subWrapper);
                    if (CollectionUtils.isNotEmpty(subResources)) {
                        List<TreeVO> chilren = subResources.stream().map(sub -> {
                            TreeVO subTreeVO = new TreeVO();
                            subTreeVO.setId(sub.getResourceId());
                            subTreeVO.setTitle(sub.getResourceName());
                            return subTreeVO;
                        }).collect(Collectors.toList());
                        treeVO.setChildren(chilren);
                    }
                    return treeVO;
                }).collect(Collectors.toList());
            }
            return treeVOS;
        } else {
            QueryWrapper<Resource> query = Wrappers.<Resource>query();
            query.eq(flag == 1, "rr.role_id", roleId)
                    .isNull("re.parent_id").orderByAsc("re.sort");
            List<TreeVO> treeVOS = baseMapper.listResourceByRoleId(query, roleId);
            treeVOS.forEach(treeVO -> {
                treeVO.setChecked(false);
                QueryWrapper<Resource> queryWrapper = Wrappers.<Resource>query();
                queryWrapper.eq(flag == 1, "rr.role_id", roleId)
                        .eq("re.parent_id", treeVO.getId()).orderByAsc("re.sort");
                List<TreeVO> children = baseMapper.listResourceByRoleId(queryWrapper, roleId);
                if (CollectionUtils.isNotEmpty(children)) {
                    treeVO.setChildren(children);
                }
            });
            return treeVOS;
        }
    }

    @Override
    public HashSet<String> convert(List<ResourceVO> resourceVOS) {
        HashSet<String> model = new HashSet<>();
        resourceVOS.forEach(resourceVO -> {
            String url = resourceVO.getUrl();
            if (StringUtils.isNotBlank(url)) {
                model.add(url.substring(0, url.indexOf("/")));
            }

            List<ResourceVO> subs = resourceVO.getSubs();
            if (CollectionUtils.isNotEmpty(subs)) {
                subs.forEach(sub -> {
                    String subUrl = sub.getUrl();
                    if (StringUtils.isNotBlank(subUrl)) {
                        model.add(subUrl.substring(0, subUrl.indexOf("/")));
                    }
                });
            }
        });
        return model;
    }
}
