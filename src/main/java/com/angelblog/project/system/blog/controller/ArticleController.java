package com.angelblog.project.system.blog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.angelblog.project.system.blog.domain.Article;
import com.angelblog.project.system.blog.domain.ArticleModel;
import com.angelblog.project.system.blog.domain.Tags;
import com.angelblog.project.system.blog.service.IArticleService;
import com.angelblog.project.system.blog.service.ITagsService;
import com.angelblog.common.utils.BlogConstants;
import com.angelblog.common.annotation.Log;
import com.angelblog.common.config.Global;
import com.angelblog.common.core.controller.BaseController;
import com.angelblog.common.core.domain.AjaxResult;
import com.angelblog.common.core.page.TableDataInfo;
import com.angelblog.common.core.text.Convert;
import com.angelblog.common.enums.BusinessType;
import com.angelblog.common.exception.BusinessException;
import com.angelblog.common.utils.StringUtils;
import com.angelblog.common.utils.file.FileUploadUtils;
import com.angelblog.common.utils.file.MimeTypeUtils;
import com.angelblog.common.utils.poi.ExcelUtil;
import com.angelblog.project.system.config.service.IConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章管理Controller
 * 
 * @author alcedo
 * @date 2020-10-28
 */
@Controller
@RequestMapping("/blog/article")
public class ArticleController extends BaseController
{
    private String prefix = "blog/article";

    @Autowired
    private IArticleService articleService;
    @Autowired
    private ITagsService tagsService;

    @Autowired
    private IConfigService configService;

    private String getEditorType(){
        return configService.selectConfigByKey(BlogConstants.KEY_EDITOR_TYPE);
    }

    @RequiresPermissions("blog:article:view")
    @GetMapping()
    public String article()
    {
        return prefix + "/article";
    }

    /**
     * 查询文章管理列表
     */
    @RequiresPermissions("blog:article:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Article article)
    {
        startPage();
        List<Article> list = articleService.selectArticleList(article);
        return getDataTable(list);
    }

    /**
     * 导出文章管理列表
     */
    @RequiresPermissions("blog:article:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Article article)
    {
        List<Article> list = articleService.selectArticleList(article);
        ExcelUtil<Article> util = new ExcelUtil<Article>(Article.class);
        return util.exportExcel(list, "article");
    }

    /**
     * 新增文章管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        List<Tags> tags=tagsService.selectTagsAll();
        mmap.put("tags",tags);

        String editorType = getEditorType();
        if(BlogConstants.EDITOR_TYPE_EDITORMD.equals(editorType)){
            return prefix + "/add_editormd";
        }else{
            return prefix + "/add";
        }
    }

    /**
     * 新增保存文章管理
     */
    @RequiresPermissions("blog:article:add")
    @Log(title = "文章管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Article article)
    {
        return toAjax(articleService.insertArticle(article));
    }

    /**
     * 修改文章管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        Article article = articleService.selectArticleById(id);
        mmap.put("article", article);
        String tagIds=article.getTags();
        List<Tags> tags= tagsService.selectSelectedTagsAll(tagIds);
        mmap.put("tags", tags);
        String editorType = getEditorType();
        if(BlogConstants.EDITOR_TYPE_EDITORMD.equals(editorType)){
            return prefix + "/edit_editormd";
        }else{
            return prefix + "/edit";
        }
    }

    /**
     * 修改保存文章管理
     */
    @RequiresPermissions("blog:article:edit")
    @Log(title = "文章管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Article article)
    {
        return toAjax(articleService.updateArticle(article));
    }

    /**
     * 删除文章管理
     */
    @RequiresPermissions("blog:article:remove")
    @Log(title = "文章管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(articleService.deleteArticleByIds(ids));
    }

    @RequestMapping ("/detail/{id}")
    public String article_detail(@PathVariable String id, Model model){

        Article article= articleService.selectArticleById(id);
        if(article==null){
            throw new BusinessException("您要访问的文章不存在!");
        }
        String article_model=  article.getArticleModel();//文章模型
        if(ArticleModel.DUOGUYU.getVal().equals(article_model)){
            List<Tags> fullTabs=tagsService.selectBlogTabs();
            model.addAttribute("fullTabs",fullTabs);
            String tagIds=article.getTags();
            if(StringUtils.isNotEmpty(tagIds)){
                String[] arr= Convert.toStrArray(tagIds);
                List<Tags> tagsList=new ArrayList<Tags>();
                Tags tmp=null;
                for(String tid:arr){
                    //检测每个标签再数据库cms_tags中是否存在（根据名称），如果存在记下id，不存在则新增并记下id
                    tmp=tagsService.selectTagsById(Long.valueOf(tid));
                    if(tmp!=null){
                        tagsList.add(tmp);
                    }
                }
                model.addAttribute("tagsList",tagsList);//这个值用于输出模板文件的标签
            }
            Map dataMap= JSONObject.parseObject(JSON.toJSONString(article),Map.class);
            model.addAllAttributes(dataMap);

        }else{

        }
        return prefix+"/article-detail";

    }



    /**
     * 上传图片(markdown编辑器上传图片使用)
     */
    @PostMapping("/uploadImage")
    @ResponseBody
    public Object uploadImage(@RequestParam("editormd-image-file") MultipartFile file) throws Exception
    {
        try
        {
            /*// 上传的后台只需要返回一个 JSON 数据，结构如下：
                 {
                 success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
                 message : "提示的信息，上传成功或上传失败及错误信息等。",
                 url     : "图片地址"        // 上传成功时才返回
                 }
                 */

            // 上传图片并返回新文件名称
            String path = FileUploadUtils.upload(Global.getUploadPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
            Map map=new HashMap();
            map.put("success",1);
            map.put("url",path);
            map.put("message","上传成功!");
            return map;
        }
        catch (Exception e)
        {
            Map map=new HashMap();
            map.put("success",0);
            map.put("url","");
            map.put("message","上传失败!"+e.getMessage());
            return map;
        }
    }

}
