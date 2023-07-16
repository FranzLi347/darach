package io.github.franzli347.darach.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RsaController {

   @Resource
    RSA rsa;
    @GetMapping("/r/p")
    public Map<String,String> getPublicKey() {
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        AES aes = SecureUtil.aes(key);
        return Map.of(
                "rsa",aes.encryptHex(rsa.getPublicKeyBase64())
                ,"aes", String.valueOf(key)
        );

    }

}
