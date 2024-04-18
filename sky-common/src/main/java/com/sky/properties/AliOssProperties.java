package com.sky.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.alioss")
@Data
public class AliOssProperties {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
//    // 从环境变量中获取RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
//    String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
//    String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");
//    // 使用代码嵌入的RAM用户的访问密钥配置访问凭证。
//    CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);

}
