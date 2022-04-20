package com.rolex.rpc.model;

import com.rolex.rpc.CommandType;
import lombok.Data;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Data
public class MsgBody {
    Long jobId;
    CommandType type;
}
