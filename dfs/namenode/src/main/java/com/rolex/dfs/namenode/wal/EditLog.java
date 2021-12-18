package com.rolex.dfs.namenode.wal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Data
@AllArgsConstructor
public class EditLog {
    long txId;
    String content;
}
