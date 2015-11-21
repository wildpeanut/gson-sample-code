package idv.wildpeanut;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Customize json naming
 */
public class FieldNamingSupportSample {

    public static void main(String[] args) {
        SomeObject someObject = new SomeObject("first", "second");
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
        String jsonRepresentation = gson.toJson(someObject);
        System.out.println(jsonRepresentation);

    }
}

class SomeObject {
    
    @SerializedName("custom_naming")
    private final String someField;
    private final String someOtherField;

    public SomeObject(String a, String b) {
        this.someField = a;
        this.someOtherField = b;
    }
}
