package com.rolex.microservices.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Data
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    Long id;
    String name;
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;
}
