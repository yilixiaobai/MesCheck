package com.chinamobile.checkmes.framework;

import com.chinamobile.checkmes.until.RSAUtil;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionPropertyConfig {

    @Bean(name="encryptablePropertyResolver")
    public EncryptablePropertyResolver encryptablePropertyResolver() {
        return new EncryptionPropertyResolver();
    }

    class EncryptionPropertyResolver implements EncryptablePropertyResolver {

        @Override
        public String resolvePropertyValue(String value) {
            if(StringUtils.isBlank(value)) {
                return value;
            }
            if(value.startsWith("RSA@")) {
                return resolveDESValue(value.substring(4));
            }
            // 不需要解密的值直接返回
            return value;
        }

        private String resolveDESValue(String value) {
            String result=null;
            try {
               result= RSAUtil.deCode(value);
            }catch (Exception e){
                e.printStackTrace();
            }
            return  result;
        }

    }
}