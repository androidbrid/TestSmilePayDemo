package com.zoloz.smilepay.demo;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayCallBack;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.TradepayParam;
import com.alipay.api.request.ZolozAuthenticationCustomerSmilepayInitializeRequest;
import com.alipay.api.response.ZolozAuthenticationCustomerSmilepayInitializeResponse;
import com.alipay.zoloz.smile2pay.service.Zoloz;
import com.alipay.zoloz.smile2pay.service.ZolozCallback;
import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.zoloz.smilepay.demo.MerchantInfo.*;

public class PayTest {
    private static final String TAG = "smiletopay";

    public static final String KEY_INIT_RESP_NAME = "zim.init.resp";
    private static Zoloz zoloz;

    // 值为"1000"调用成功
    // 值为"1003"用户选择退出
    // 值为"1004"超时
    // 值为"1005"用户选用其他支付方式
    public static final String CODE_SUCCESS = "1000";
    public static final String CODE_EXIT = "1003";
    public static final String CODE_TIMEOUT = "1004";
    public static final String CODE_OTHER_PAY = "1005";

    public static final String TXT_EXIT = "已退出刷脸支付";
    public static final String TXT_TIMEOUT = "操作超时";
    public static final String TXT_OTHER_PAY = "已退出刷脸支付";
    public static final String TXT_OTHER = "抱歉未支付成功，请重新支付";

    //刷脸支付相关
    public static final String SMILEPAY_CODE_SUCCESS = "10000";
    public static final String SMILEPAY_SUBCODE_LIMIT = "ACQ.PRODUCT_AMOUNT_LIMIT_ERROR";
    public static final String SMILEPAY_SUBCODE_BALANCE_NOT_ENOUGH = "ACQ.BUYER_BALANCE_NOT_ENOUGH";
    public static final String SMILEPAY_SUBCODE_BANKCARD_BALANCE_NOT_ENOUGH = "ACQ.BUYER_BANKCARD_BALANCE_NOT_ENOUGH";

    public static final String SMILEPAY_TXT_LIMIT = "刷脸支付超出限额，请选用其他支付方式";
    public static final String SMILEPAY_TXT_EBALANCE_NOT_ENOUGH = "账户余额不足，支付失败";
    public static final String SMILEPAY_TXT_BANKCARD_BALANCE_NOT_ENOUGH = "账户余额不足，支付失败";
    public static final String SMILEPAY_TXT_FAIL = "抱歉未支付成功，请重新支付";
    public static final String SMILEPAY_TXT_SUCCESS = "刷脸支付成功";
    Context mContext;
    public PayTest(Context context){
        mContext=context;
        zoloz = com.alipay.zoloz.smile2pay.service.Zoloz.getInstance(mContext);
    }
    /**
     * 发起刷脸支付请求，先zolozGetMetaInfo获取本地app信息，然后调用服务端获取刷脸付协议.
     */
    public  void smilePay(final double shmoney) {
        zoloz.zolozGetMetaInfo(mockInfo(), new ZolozCallback() {
            @Override
            public void response(Map smileToPayResponse) {
                if (smileToPayResponse == null) {
                    Log.e(TAG, "response is null");
                    promptText(TXT_OTHER);
                    return;
                }

                String code = (String)smileToPayResponse.get("code");
                String metaInfo = (String)smileToPayResponse.get("metainfo");
                Log.i(TAG, "code:" + code);
                Log.i(TAG, "metainfo:" + metaInfo);
                //获取metainfo成功
                if (CODE_SUCCESS.equalsIgnoreCase(code) && metaInfo != null) {

                    //正式接入请上传metaInfo到服务端，不要忘记UrlEncode，使用服务端使用的sdk从服务端访问openapi获取zimId和zimInitClientData；
                    AlipayClient alipayClient1 = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                            appId,
                            appKey,
                            "json",
                            "utf-8",
                            null,
                            "RSA2");
                    ZolozAuthenticationCustomerSmilepayInitializeRequest request
                            = new ZolozAuthenticationCustomerSmilepayInitializeRequest();
                    request.setBizContent(metaInfo);

                    //起一个异步线程发起网络请求
                    alipayClient1.execute(request,
                            new AlipayCallBack() {
                                @Override
                                public AlipayResponse onResponse(AlipayResponse response) {
                                    if (response != null && SMILEPAY_CODE_SUCCESS.equals(response.getCode())) {
                                        try {
                                            ZolozAuthenticationCustomerSmilepayInitializeResponse zolozResponse
                                                    = (ZolozAuthenticationCustomerSmilepayInitializeResponse)response;

                                            String result = zolozResponse.getResult();
                                            JSONObject resultJson = JSON.parseObject(result);
                                            String zimId = resultJson.getString("zimId");
                                            String zimInitClientData = resultJson.getString("zimInitClientData");
                                            //人脸调用
                                            smile(zimId, zimInitClientData,shmoney);
                                        } catch (Exception e) {
                                            promptText(TXT_OTHER);
                                        }
                                    } else {
                                        promptText(TXT_OTHER);
                                    }
                                    return null;
                                }
                            });
                } else {
                    Log.i(TAG, "response: ");
                    promptText(TXT_OTHER);
                }
            }
        });
    }


    /**
     * 发起刷脸支付请求.
     * @param zimId 刷脸付token，从服务端获取，不要mock传入
     * @param protocal 刷脸付协议，从服务端获取，不要mock传入
     */
    public  void smile(String zimId, String protocal, final double shmoney) {
        Map params = new HashMap();
        params.put("smile_mode", 1);
        params.put(KEY_INIT_RESP_NAME, protocal);
        zoloz.zolozVerify(zimId, params, new ZolozCallback() {
            @Override
            public void response(final Map smileToPayResponse) {
                if (smileToPayResponse == null) {
                    promptText(TXT_OTHER);
                    return;
                }

                String code = (String)smileToPayResponse.get("code");
                String fToken = (String)smileToPayResponse.get("ftoken");
                String subCode = (String)smileToPayResponse.get("subCode");
                String msg = (String)smileToPayResponse.get("msg");
                Log.d(TAG, "ftoken is:" + fToken);

                //刷脸成功
                if (CODE_SUCCESS.equalsIgnoreCase(code) && fToken != null) {
                    //promptText("刷脸成功，返回ftoken为:" + fToken);
                    //这里在Main线程，网络等耗时请求请放在异步线程中
                    //后续这里可以发起支付请求
                    //https://docs.open.alipay.com/api_1/alipay.trade.pay
                    //需要修改两个参数
                    //scene固定为security_code
                    //auth_code为这里获取到的fToken值
                    //支付一分钱，支付需要在服务端发起，这里只是模拟
                    try {
                        pay(fToken, shmoney);
                    } catch (Exception e) {
                        promptText(SMILEPAY_TXT_FAIL);
                    }
                } else if (CODE_EXIT.equalsIgnoreCase(code)) {
                    promptText(TXT_EXIT);
                } else if (CODE_TIMEOUT.equalsIgnoreCase(code)) {
                    promptText(TXT_TIMEOUT);
                } else if (CODE_OTHER_PAY.equalsIgnoreCase(code)) {
                    promptText(TXT_OTHER_PAY);
                } else {
                    String txt = TXT_OTHER;
                    if (!TextUtils.isEmpty(subCode)) {
                        txt = txt + "(" + subCode + ")";
                    }
                    promptText(txt);
                }
            }

        });
    }



    /**
     * 发起刷脸支付请求.
     * @param txt toast文案
     */
    public  void promptText(final String txt) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();

                Toast.makeText(mContext, txt, Toast.LENGTH_LONG).show();

                Looper.loop();// 进入loop中的循环，查看消息队列

            }
        }).start();
    }

    /**
     * 发起刷脸支付请求.
     * @param ftoken 刷脸返回的token
     * @param amount 支付金额
     */
    public  void pay(String ftoken, double amount) throws Exception {
        AlipayClient alipayClient1 = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                appId,
                appKey,
                "json",
                "utf-8",
                null,
                "RSA2");
        AlipayTradePayRequest alipayTradePayRequest = new AlipayTradePayRequest();
        TradepayParam tradepayParam11 = new TradepayParam();
        tradepayParam11.setOut_trade_no(UUID.randomUUID().toString());

        //auth_code和scene填写需要注意
        tradepayParam11.setAuth_code(ftoken);
        tradepayParam11.setScene("security_code");
        tradepayParam11.setSubject("smilepay");
        tradepayParam11.setStore_id("smilepay test");
        tradepayParam11.setTimeout_express("5m");
        tradepayParam11.setTotal_amount(""+amount);
        alipayTradePayRequest.setBizContent(JSON.toJSONString(tradepayParam11));
        alipayClient1.execute(alipayTradePayRequest,
                new AlipayCallBack() {

                    @Override
                    public AlipayResponse onResponse(AlipayResponse response) {
                        if (response != null && SMILEPAY_CODE_SUCCESS.equals(response.getCode())) {
                            //promptText(SMILEPAY_TXT_SUCCESS);
                            EventBus.getDefault().post("人脸支付成功");
                        } else {
                            if (response != null) {
                                String subCode = response.getSubCode();
                                if (SMILEPAY_SUBCODE_LIMIT.equalsIgnoreCase(subCode)) {
                                    promptText(SMILEPAY_TXT_LIMIT);
                                } else if(SMILEPAY_SUBCODE_BALANCE_NOT_ENOUGH.equalsIgnoreCase(subCode)) {
                                    promptText(SMILEPAY_TXT_EBALANCE_NOT_ENOUGH);
                                } else if(SMILEPAY_SUBCODE_BANKCARD_BALANCE_NOT_ENOUGH.equalsIgnoreCase(subCode)) {
                                    promptText(SMILEPAY_TXT_BANKCARD_BALANCE_NOT_ENOUGH);
                                } else {
                                    promptText(SMILEPAY_TXT_FAIL);
                                }
                                EventBus.getDefault().post("人脸支付失败");
                            } else {
                                promptText(SMILEPAY_TXT_FAIL);
                                EventBus.getDefault().post("人脸支付失败");
                            }
                        }
                        return null;
                    }
                });
        return;
    }
    public void close(){
        zoloz.zolozUninstall();
    }
}
