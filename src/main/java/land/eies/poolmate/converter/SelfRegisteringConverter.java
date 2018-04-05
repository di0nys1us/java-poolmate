package land.eies.poolmate.converter;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;

abstract class SelfRegisteringConverter<S, T> implements Converter<S, T>, InitializingBean {

    private final ConversionService conversionService;

    SelfRegisteringConverter(final ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    ConversionService getConversionService() {
        return conversionService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (conversionService instanceof ConfigurableConversionService) {
            ((ConfigurableConversionService) conversionService).addConverter(this);
        }
    }
}
