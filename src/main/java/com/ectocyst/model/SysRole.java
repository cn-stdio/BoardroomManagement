package com.ectocyst.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seagull_gby
 * @date 2019/3/7 19:36
 * Description: 权限表
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRole {

    /**
     * 权限ID
     */
    private long id;

    /**
     * 权限名称
     */
    private String name;

    public String getName() {
        return this.name;
    }
}
