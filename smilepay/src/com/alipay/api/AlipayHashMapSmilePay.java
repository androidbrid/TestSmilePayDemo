package com.alipay.api;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by bruce on 2017/12/25.
 */
public class AlipayHashMapSmilePay extends HashMap {

    public AlipayHashMapSmilePay() {

    }

    public AlipayHashMapSmilePay(Map<String, String> textParams) {
        this.putAll(textParams);
    }
}
