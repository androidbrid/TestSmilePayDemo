package com.zoloz.smilepay.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bruce on 2018/6/15.
 */

public class MerchantInfo {
    //这里三个值请填写自己真实的值
    //应用的签名私钥
    public final static String appKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC88ucriYSKi57QSH2kTVFVJP7zrV3aRUmWqpXEzjPWCZfB3QQMJm/EZL8yLeFS4VC3Ic7888qVWoUhch6F/MZDhCv10yuS3e9yS42QtwgN/zOUx6WkLayBiZZJ+eS/uq+TWv75N65Np8U2KoEBBtXDVXKu+PYATFgGYBGY+796famSbKSnwrWK2c751FZsdXfVVN1OQcYmBYtP2P/n7phJKEKcaSRvvGrcXJNrlSMOLez2vTb+R73Tw96S+DcsN3vJufbRNgfuy0Y7p2jMU9SorZnRQ9nroCl4DIH//7tx1y837bblRQjVAfi+Ordo7HCUd/m7WfFZ4gDXDZ5EtRLvAgMBAAECggEADLMy8I00uG88BHnq4AFGZzeuX0iW11Xy5Uqf+HN3rnpElgY6AGsQ9h1jcIb+7ygv2a9PD1d/lVxZOG9IN+9OkDkxDzvbYOrD7mW/pSt4QiCWIfjtXESppKdPz7cZNf3hUUE7hBrG/3c4XTSsKRqBgbiblcEsH8cB36PTavn42826vzVSHRhgFCqZG0FIUA/ekzd3cAgQD+OVwX7pSPArjl3OhYAyDmz5kVjejk1sP46OY+iS9yVPmQJgWAMlFnRyyvhC5sr4FxGSzO5ZC+glmySFcKktyfhMrunVBeCLa92RTGRhOhEuPf+KM/exD19i6bkwQ76JjVrtp5D//64LMQKBgQD0EkgnP9gEWxm5QXbKOKYTJr7sTuyRI+JVRqJ1LFCRKLlJyA08M5Ftz83GZCZZx4tEtqxSAQyfdPymdmooBIC2HzBDGDWELzqoI7bwYuOZU/UKzzNwRus07/waUWPk9cxnHbWr06At4L9/CB15BoP0Yv5gh6J36pVScSfg0pjN1wKBgQDGLvNr448Ij6j/POen51G1v5x4b7Md4Y4bTExKO62S+u1/HgIV/oFZUXUtbuFM6Mot1NguShofsDMtgwrxtrSqYrQiUFfPtBPZv8TihDfyJXck/oqLv6rc+3O6g5Hw2GOD0ZfINbXOn4oe2NDDADmCz+SIPHIRH4lpYsd8qk5QqQKBgHc0FplUzSQTelTIbEQHttTTr5uqUE/yvq/3d5DSu+aphRVL52rnpLeFLnMeprxigNpmfXLvoEKg2GRUoTQ6PsK0KNYcFMciafuEtexh39ca8ir9V5oWiPmp2+7eoXNJuTMDh58MFMdTZTOduwdI71mEn8h1dhwrLhK9wUREluyxAoGAUyXPqyxGDml3ld6PsroyZfaGR2FV3OuNG5bfSyXOpSPyEKseZOvqiPbMGsuyavogGSp7qLAeVb0vO6EHLnJhSjsvKFZdAR+kVG9Iw2CFuGpPmo3QwuEfZqd43n/k8w9WcS+2ZqFiLdZxtI9yxt2JQ4DFeQMjOFvq4vQ2je9F/EECgYBT/ANWdCVf58diEB3LqmnijEPOSKdOd0HId1tR9UXsuF/e55zEIPhNiCDYYyp06YYLIKmc8QZDKCgZgM3GhJ3zfY6/b57qiV1hdMNUZQAhxCq3VNAqVupX3kb3JlJvrzYxF66IM/8fNTToJaIJRGUeDiq8vxh/PM0DVIlTe+xOBw==";
    public final static String partnerId = "2088621521355983";
    //应用的appId
    public final static String appId = "2017041106644334";
    /**
     * mock数据，真实商户请填写真实信息.
     */
    public static Map mockInfo() {
        Map merchantInfo = new HashMap();
        //以下信息请根据真实情况填写
        //商户id
        merchantInfo.put("partnerId", partnerId);
        merchantInfo.put("merchantId", partnerId);
        //开放平台注册的appid
        merchantInfo.put("appId", appId);
        //机具编号，便于关联商家管理的机具
        merchantInfo.put("deviceNum", "TEST_ZOLOZ_TEST");
        //真实店铺号
        merchantInfo.put("storeCode", "TEST");
        //口碑店铺号
        merchantInfo.put("alipayStoreCode", "TEST");
        //品牌，传入拼音或者英文，标示该商家
        merchantInfo.put("brandCode", "TEST");

        merchantInfo.put("areaCode", "TEST");
        merchantInfo.put("geo", "0.000000,0.000000");
        merchantInfo.put("wifiMac", "TEST");
        merchantInfo.put("wifiName", "TEST");
        merchantInfo.put("deviceMac", "TEST");

        return merchantInfo;
    }
}
