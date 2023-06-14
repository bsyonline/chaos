package com.rolex.microservices.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author rolex
 * @since 2021-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    private Long orderNo;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.orderNo;
    }

}
