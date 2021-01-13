package com.damein;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CaseInfo {

    private String batchNo;

    private String billCode;

    private String name;

    private String cardNo;

    private String phone;
}
