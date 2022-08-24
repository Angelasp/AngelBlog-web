package com.angelblog.project.system.blog.service.impl;

import java.util.List;
import com.angelblog.common.utils.DateUtils;
import com.angelblog.common.utils.Guid;
import com.angelblog.common.utils.security.ShiroUtils;
import com.angelblog.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.angelblog.project.system.blog.mapper.AttachmentMapper;
import com.angelblog.project.system.blog.domain.Attachment;
import com.angelblog.project.system.blog.service.IAttachmentService;
import com.angelblog.common.core.text.Convert;

/**
 * 附件Service业务层处理
 * 
 * @author alcedo
 * @date 2020-11-01
 */
@Service
public class AttachmentServiceImpl implements IAttachmentService 
{
    @Autowired
    private AttachmentMapper attachmentMapper;

    /**
     * 查询附件
     * 
     * @param attachId 附件ID
     * @return 附件
     */
    @Override
    public Attachment selectAttachmentById(String attachId)
    {
        return attachmentMapper.selectAttachmentById(attachId);
    }

    @Override
    public List<Attachment> selectAttachmentsByZid(String zid) {
        Attachment attachment=new Attachment();
        attachment.setZid(zid);
        return attachmentMapper.selectAttachmentList(attachment);
    }

    /**
     * 查询附件列表
     * 
     * @param attachment 附件
     * @return 附件
     */
    @Override
    public List<Attachment> selectAttachmentList(Attachment attachment)
    {
        return attachmentMapper.selectAttachmentList(attachment);
    }

    /**
     * 新增附件
     * 
     * @param attachment 附件
     * @return 结果
     */
    @Override
    public int insertAttachment(Attachment attachment)
    {
        attachment.setAttachId(Guid.get());
        User user= ShiroUtils.getUser();
        attachment.setUserId(user.getUserId().toString());
        attachment.setCreateBy(user.getUserName());
        attachment.setCreateTime(DateUtils.getNowDate());
        return attachmentMapper.insertAttachment(attachment);
    }

    /**
     * 修改附件
     * 
     * @param attachment 附件
     * @return 结果
     */
    @Override
    public int updateAttachment(Attachment attachment)
    {
        attachment.setUpdateTime(DateUtils.getNowDate());
        return attachmentMapper.updateAttachment(attachment);
    }

    /**
     * 删除附件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAttachmentByIds(String ids)
    {
        return attachmentMapper.deleteAttachmentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除附件信息
     * 
     * @param attachId 附件ID
     * @return 结果
     */
    @Override
    public int deleteAttachmentById(String attachId)
    {
        return attachmentMapper.deleteAttachmentById(attachId);
    }
}
