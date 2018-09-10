package com.nekolr.upms.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 账户操作日志
 * </p>
 *
 * @author nekolr
 * @since 2018-09-10
 */
public class AccountLog extends Model<AccountLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户主键
     */
    private String userId;

    /**
     * 操作
     */
    private String operation;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 操作状态
     */
    private Boolean status;

    /**
     * 具体信息
     */
    private String message;

    /**
     * 操作时的 IP
     */
    private String ip;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AccountLog{" +
        "id=" + id +
        ", username=" + username +
        ", userId=" + userId +
        ", operation=" + operation +
        ", createTime=" + createTime +
        ", status=" + status +
        ", message=" + message +
        ", ip=" + ip +
        "}";
    }
}
