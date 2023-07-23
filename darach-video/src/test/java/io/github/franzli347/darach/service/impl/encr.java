package io.github.franzli347.darach.service.impl;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.Charset;

public class encr {

    @Test
    public void test() throws IOException {
//        // 读取rsa公钥和私钥
//
        String publicKey = new ClassPathResource("rsa/public_key.pem")
                .getContentAsString(Charset.defaultCharset());
        String privateKey = new ClassPathResource("rsa/private_key.pem")
                .getContentAsString(Charset.defaultCharset());

//        // 公加私解
        RSA rsa = new RSA(privateKey, publicKey);
//        String encrypt = rsa.encryptBase64(StrUtil.bytes("{\n" +
//                "    \"email\":\"1@333.com\",\n" +
//                "    \"password\":\"123456\"\n" +
//                "}", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
//        System.out.println(encrypt);


        // 私加公解
//        encrypt = rsa.encryptBase64(StrUtil.bytes("我是一段测试bbb", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        String dec = rsa.decryptStr("\"ga9Jg//nGSm0fw4h1UZ3LF5G78o7kWr757z6LaFLPfkvaT0hF7pnttYaOxk9tA4P2vc0xZ54d+9n3RZ+M3wutTZHU28+e/jDLe0pN17xHRouYaUIpUfstabNFaMw7ejr25uVGZpo4ulhGz/fEEen5F0aKi0MNFSSGYC62zCBgJs=\"", KeyType.PublicKey);
        System.out.println(dec);
    }


}
