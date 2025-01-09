package co.com.bancolombia.transformservice.config.utils;

import co.com.bancolombia.model.validation.exceptions.TechnicalException;
import lombok.NoArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import static co.com.bancolombia.model.validation.exceptions.message.TechnicalErrorMessage.INVALID_EXPRESSION;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ExpressionUtils {

    public static final String VALUE = "value";

    public static String getExpressionValue(String value, String expression) {
        var expressionParser = new SpelExpressionParser();
        var context = new StandardEvaluationContext();

        context.setVariable(VALUE, value);
        try {
            return expressionParser.parseExpression(expression).getValue(context, String.class);
        } catch (ExpressionException exception) {
            throw new TechnicalException(INVALID_EXPRESSION, exception);
        }
    }
}
