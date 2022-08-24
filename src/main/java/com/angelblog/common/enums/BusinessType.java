package com.angelblog.common.enums;

/**
 * 业务操作类型
 * 
 * @author alcedo
 */
public enum BusinessType
{
    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,
    
    /**
     * 清空
     */
    CLEAN,
    /**
     * 数据库备份
     */
    DB_BACKUP,
    /**
     * 数据库恢复
     */
    DB_RECOVERY,
}
