package co.com.bancolombia.transformservice.config.toplaintext;

import co.com.bancolombia.transformservice.config.utils.FillDirection;

public abstract class ReqInnerValue extends ReqValue{
    public ReqInnerValue(int length, boolean optional, String fillValue, FillDirection fillDirection) {
        super(length, optional, fillValue, fillDirection);
    }

    public abstract String getValue();
}
