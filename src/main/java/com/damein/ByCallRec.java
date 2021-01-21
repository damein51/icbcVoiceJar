package com.damein;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ByCallRec {

    private String id;

    private Long callTime;

    private String callTimeStr;

    private String url;
}

