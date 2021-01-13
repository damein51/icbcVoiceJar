package com.damein;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CallRec {

    private String batchNo;

    private String billCode;

    private Long callTime;

    private String callTimeStr;

    private String url;

    private String phone;

    private String callId;

    private String name;

    public static void main(String[] args) {
        String origin = "http://10.30.1.153:8068/cincc-serv/media/download?msServer=ms2&filePath=666666/servicerecord/000001666666005002/20200619/30003A15801860003C202006191355560A1E011525503700S202006191356041486850A1E011507743979.mp3";

        String str = "http://10.30.1.153:8068/cincc-serv/media/download?msServer=ms2&filePath=";

        System.out.println(str.length());

        String replace = origin.replace(str, "");
        System.out.println(replace);

    }
}

