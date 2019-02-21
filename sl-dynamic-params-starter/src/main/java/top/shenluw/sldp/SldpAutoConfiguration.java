package top.shenluw.sldp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.shenluw.sldp.processor.GsonDynamicParamsMethodProcessor;
import top.shenluw.sldp.processor.JacksonDynamicParamsMethodProcessor;
import top.shenluw.sldp.processor.JsonDynamicParamsMethodProcessor;
import top.shenluw.sldp.processor.WebDataBinderDynamicParamsMethodProcessor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Shenluw
 * 创建日期：2018/2/18 17:30
 */
@Configuration
@EnableConfigurationProperties(SldpProperties.class)
public class SldpAutoConfiguration {

    @Autowired
    private SldpProperties sldpProperties;

    @Bean
    public WebMvcConfigurer sldpWebMvcConfigurer(ApplicationContext context, DynamicParamsMethodProcessorSorter processorSorter) {
        Map<String, AbstractDynamicParamsMethodProcessor> beans = context.getBeansOfType(AbstractDynamicParamsMethodProcessor.class);
        return new WebMvcConfigurer() {
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
                processorSorter.sort(beans.values()).forEach(composite::addResolver);
                resolvers.add(composite);
            }
        };
    }


    @Bean
    public DynamicParamsMethodProcessorSorter dynamicParamsMethodProcessorSorter() {
        return resolvers -> resolvers;
    }


    private void configureDynamicParamsMethodProcessor(AbstractDynamicParamsMethodProcessor processor, TypeNameAliasResolver aliasResolver) {
        processor.setTypeName(sldpProperties.getTypeName());
        processor.setTypeNameAliasResolver(aliasResolver);
        processor.setDefaultProcessor(Objects.equals(processor.getClass().getName(), sldpProperties.getDefaultProcessor()));
    }

    @Bean
    public WebDataBinderDynamicParamsMethodProcessor webDataBinderDynamicParamsMethodProcessor(TypeNameAliasResolver aliasResolver) {
        WebDataBinderDynamicParamsMethodProcessor processor = new WebDataBinderDynamicParamsMethodProcessor();
        configureDynamicParamsMethodProcessor(processor, aliasResolver);
        return processor;
    }

    @Bean
    public TypeNameAliasResolver typeNameAliasResolver() {
        return new TypeNameAliasResolver(sldpProperties.getTypeAlias());
    }

    @Configuration
    @ConditionalOnBean({Gson.class})
    class GsonConfiguration {

        @Bean
        public GsonDynamicParamsMethodProcessor gsonDynamicParamsMethodProcessor(Gson gson, TypeNameAliasResolver aliasResolver) {
            GsonDynamicParamsMethodProcessor processor = new GsonDynamicParamsMethodProcessor(sldpProperties.getJsonDataName(), gson);
            configureDynamicParamsMethodProcessor(processor, aliasResolver);
            return processor;
        }
    }

    @Configuration
    @ConditionalOnBean({ObjectMapper.class})
    class JacksonConfiguration {

        @Bean
        public JsonDynamicParamsMethodProcessor jacksonDynamicParamsMethodProcessor(ObjectMapper objectMapper, TypeNameAliasResolver aliasResolver) {
            JacksonDynamicParamsMethodProcessor processor = new JacksonDynamicParamsMethodProcessor(sldpProperties.getJsonDataName(), objectMapper);
            configureDynamicParamsMethodProcessor(processor, aliasResolver);
            return processor;
        }

    }

}
