package idv.wildpeanut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Use Customize Skip Strategy to export json data
 */
public class ExcludingFieldsSample {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setExclusionStrategies(new MyExclusionStrategy(String.class))
                .serializeNulls().create();
        SampleObjectForTest src = new SampleObjectForTest();
        String json = gson.toJson(src);
        System.out.println(json);

    }

}

class SampleObjectForTest {
    @Skip
    private final int annotatedField;
    private String stringField;
    private long longField;
    private Class<?> clazzField;

    public SampleObjectForTest() {
        annotatedField = 5;
        stringField = "someDefaultValue";
        longField = 1234;
    }
}

class MyExclusionStrategy implements ExclusionStrategy {

    private final Class<?> typeToSkip;

    public MyExclusionStrategy(Class<?> typeToSkip) {
        this.typeToSkip = typeToSkip;
    }

    public boolean shouldSkipClass(Class<?> clazz) {
        return (clazz == typeToSkip);
    }

    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(Skip.class) != null;
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Skip {
  // Field tag only annotation
}
