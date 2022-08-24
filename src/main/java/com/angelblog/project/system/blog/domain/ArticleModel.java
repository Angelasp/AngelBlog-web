package com.angelblog.project.system.blog.domain;


import com.angelblog.common.utils.StringUtils;

/**
 * 文章模型。文章，书籍，书籍章节，链接，图集。
 * @author alcedo
 */
public enum ArticleModel {
    DUOGUYU("news","新闻"),
    OTHER("other","其它");

    private String val;
    private String desc;
    private ArticleModel(String v, String desc){
        this.val=v;
        this.desc=desc;
    }
    public String getDesc(){
        return desc;
    }
    public String getVal(){
        return val;
    }
    @Override
    public String toString() {
        return val;
    }

    /**
     * 根据val获取去desc
     * @param val
     * @return
     */
    public static String getDescByVal(String val){
        if(StringUtils.isEmpty(val)){
            return "其它";
        }
        for(ArticleModel articleModel:ArticleModel.values()){
            if(val.equals(articleModel.getVal())){
                return articleModel.getDesc();
            }
        }
        return  "";
    }

}
