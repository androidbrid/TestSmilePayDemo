package com.alipay.api;


/**
 * Created by bruce on 2017/12/25.
 */
public class RequestParametersHolderSmilePay {
    public AlipayHashMapSmilePay protocalMustParams = null;
    public AlipayHashMapSmilePay protocalOptParams = null;
    public AlipayHashMapSmilePay applicationParams = new AlipayHashMapSmilePay();

    public void setApplicationParams(AlipayHashMapSmilePay appParams) {
        applicationParams.putAll(appParams);
    }

    public void setProtocalMustParams(AlipayHashMapSmilePay protocalMustParams) {
        this.protocalMustParams = protocalMustParams;
    }

    public void setProtocalOptParams(AlipayHashMapSmilePay protocalOptParams) {
        this.protocalOptParams = protocalOptParams;
    }

    public AlipayHashMapSmilePay getProtocalMustParams() {
        return this.protocalMustParams;
    }

    public AlipayHashMapSmilePay getProtocalOptParams() {
        return this.protocalOptParams;
    }

    public AlipayHashMapSmilePay getApplicationParams() {
        return applicationParams;
    }
}
