package br.com.curso.spring.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
	
    @Autowired
    private Environment env;

    public String gteTokenSecret(){
        return env.getProperty("token.secret");
    }
}
