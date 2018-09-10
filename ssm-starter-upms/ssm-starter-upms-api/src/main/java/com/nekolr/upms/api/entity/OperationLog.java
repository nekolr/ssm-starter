package com.nekolr.upms.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author nekolr
 * @since 2018-09-10
 */
public class OperationLog extends Model<OperationLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 日志名称
     */
    private String logName;

    /**
     * 操作者的用户名
     */
    private String username;

    /**
     * 操作者的主键
     */
    private String userId;

    /**
     * 请求的 api 名称
     */
    private String api;

    /**
     * 请求的方法名
     */
    private String method;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 具体信息
     */
    private String message;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
        "id=" + id +
        ", logName=" + logName +
        ", username=" + username +
        ", userId=" + userId +
        ", api=" + api +
        ", method=" + method +
        ", createTime=" + createTime +
        ", status=" + status +
        ", message=" + message +
        "}";
    }
}
