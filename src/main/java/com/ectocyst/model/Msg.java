package com.ectocyst.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seagull_gby
 * @date 2019/3/23 13:01
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Msg {

    private String title;
    private String content;
    private String extraInfo;

}
