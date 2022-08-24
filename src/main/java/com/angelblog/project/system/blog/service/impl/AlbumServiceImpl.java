package com.angelblog.project.system.blog.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.angelblog.project.system.blog.domain.AlbumMaterial;
import com.angelblog.project.system.blog.domain.Material;
import com.angelblog.project.system.blog.domain.MaterialUse;
import com.angelblog.project.system.blog.mapper.MaterialMapper;
import com.angelblog.common.utils.BlogConstants;
import com.angelblog.common.annotation.DataScope;
import com.angelblog.common.utils.DateUtils;
import com.angelblog.common.utils.Guid;
import com.angelblog.common.utils.security.ShiroUtils;
import com.angelblog.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.angelblog.project.system.blog.mapper.AlbumMapper;
import com.angelblog.project.system.blog.domain.Album;
import com.angelblog.project.system.blog.service.IAlbumService;
import com.angelblog.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 素材相册Service业务层处理
 * 
 * @author alcedo
 * @date 2021-11-08
 */
@Service
public class AlbumServiceImpl implements IAlbumService 
{
    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private MaterialMapper materialMapper;
    /**
     * 查询素材专辑
     * 
     * @param albumId 素材专辑ID
     * @return 素材专辑
     */
    @Override
    public Album selectAlbumById(String albumId)
    {
        return albumMapper.selectAlbumById(albumId);
    }

    /**
     * 查询素材专辑列表
     * 
     * @param album 素材专辑
     * @return 素材专辑
     */
    @Override
    @DataScope(deptAlias = "a" ,userAlias = "a")
    public List<Album> selectAlbumList(Album album)
    {
        return albumMapper.selectAlbumList(album);
    }

    /**
     * 新增素材专辑
     * 
     * @param album 素材专辑
     * @return 结果
     */
    @Override
    public int insertAlbum(Album album)
    {
        album.setAlbumId(Guid.get());
        User user= ShiroUtils.getUser();
        album.setDeptId(user.getDeptId().toString());
        album.setUserId(user.getUserId().toString());
        album.setCreateBy(user.getUserName());
        album.setCreateTime(DateUtils.getNowDate());
        return albumMapper.insertAlbum(album);
    }

    /**
     * 修改素材专辑
     * 
     * @param album 素材专辑
     * @return 结果
     */
    @Override
    public int updateAlbum(Album album)
    {
        album.setUpdateTime(DateUtils.getNowDate());
        return albumMapper.updateAlbum(album);
    }

    /**
     * 删除素材专辑对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAlbumByIds(String ids)
    {
        return albumMapper.deleteAlbumByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除素材专辑信息
     * 
     * @param albumId 素材专辑ID
     * @return 结果
     */
    @Override
    public int deleteAlbumById(String albumId)
    {
        return albumMapper.deleteAlbumById(albumId);
    }
    /**
     * 查询专辑【关联】的素材
     * @param material
     * @return
     */
    @Override
    public List<AlbumMaterial> selectAlbumMaterialList(Material material) {

        List<AlbumMaterial>  list=materialMapper.selectAlbumMaterialList(material);
        return list;
    }

    /**
     * 查询专辑【未关联】的素材
     * @param material
     * @return
     */
    @Override
    public List<Material> selectAlbumUnMaterialList(Material material) {

        List<Material> list=materialMapper.selectAlbumUnMaterialList(material);
        return list;
    }

    public boolean containsId(String tarId,String[] arr){
        boolean flag=false;
        for(String id:arr){
            if(id.equals(tarId)){
                flag=true;
                break;
            }
        }
        return flag;
    }

    /**
     * 删除专辑关联的素材
     * @param ids
     */
    @Override
    @Transactional
    public void deleteMaterialBatch(String ids) {
        String[] arr=Convert.toStrArray(ids);
        //删除使用记录
        List<AlbumMaterial> albumMaterials = albumMapper.selectAlbumMaterialByIds(arr);
        for(AlbumMaterial albumMaterial:albumMaterials){
            materialMapper.deleteMaterialUseByMx(albumMaterial.getMaterialId(),"cms_album_material",albumMaterial.getAlbumId(),"album_id");
            int n=materialMapper.selectCountMaterialUse(albumMaterial.getMaterialId());
            if(n==0){
                materialMapper.updateMaterialUseState(albumMaterial.getMaterialId(), BlogConstants.USE_STATE_NO);
            }
        }
        albumMapper.deleteAlbumMaterialByIds(arr);
    }

    /**
     * 保存专辑关联的素材
     * @param albumId
     * @param materialIds
     */
    @Override
    @Transactional
    public void saveMaterial(String albumId, String materialIds) {
        String[] addArr=Convert.toStrArray(materialIds);

        User user=ShiroUtils.getUser();

        if(addArr!=null&&addArr.length>0){
            List<AlbumMaterial> list=Lists.newArrayList();
            AlbumMaterial albumMaterial=null;
            Integer i=0;
            for(String s:addArr){
                i++;
                albumMaterial=new AlbumMaterial();
                albumMaterial.setAlbumId(albumId);
                albumMaterial.setMaterialId(s);
                albumMaterial.setSort(i.toString());
                list.add(albumMaterial);
            }
            albumMapper.insertAlbumMaterialBatch(list);

            //插入使用记录 cms_material_use
            List<MaterialUse> useList=Lists.newArrayList();
            MaterialUse use=null;
            for(String s:addArr){
                use=new MaterialUse();
                use.setUseId(albumId);
                use.setMaterialId(s);
                use.setUserId(user.getUserId().toString());
                use.setCreateBy(user.getUserName());
                use.setCreateTime(new Date());
                use.setUseTable(BlogConstants.MATERIAL_USE_ALBUM_TABLE_NAME);
                use.setUseColumn(BlogConstants.MATERIAL_USE_ALBUM_COLUMN_NAME);
                useList.add(use);
            }
            materialMapper.insertMaterialUseBatch(useList);

            //更新素材使用状态
            for(String mid:addArr){
                materialMapper.updateMaterialUseState(mid,BlogConstants.USE_STATE_YES);
            }
        }

    }

    @Override
    public Map getAlbum(String code) {
        Album album=albumMapper.selectAlbumByCode(code);
        if(album!=null){
            Material queryForm=new Material();
            queryForm.setAlbumId(album.getAlbumId());
            List<AlbumMaterial> images=materialMapper.selectAlbumMaterialList(queryForm);
            album.setImages(images);
            Map map=JSON.parseObject(JSON.toJSONString(album),Map.class);
            return map;
        }
        return null;
    }
}
