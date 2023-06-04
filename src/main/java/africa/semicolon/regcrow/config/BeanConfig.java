package africa.semicolon.regcrow.config;

import africa.semicolon.regcrow.utils.RegcrowMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public RegcrowMapper regcrowMapper(){
        return new RegcrowMapper();
    }
}
