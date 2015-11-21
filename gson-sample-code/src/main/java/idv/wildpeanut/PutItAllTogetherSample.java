package idv.wildpeanut;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Since;

import lombok.Data;

/**
 * a example show how to use Gson Version, ExclusionStrategy and FieldNamingPolicy
 */
public class PutItAllTogetherSample {

    public static void main(String[] args) {
        String datePattern = "yyyy-MM-dd";
        // ---------- Read json data
        //@formatter:off
        String jsonRawData = "{                                "
                            +"	'sequenceNumberInDB': '1',     " 
                            +"	'productNumber': 'Product-1',  "
                            +"	'numberOfSold': 123,           " 
                            +"	'productName': 'IPhone 100',   "
                            +"  'createDate': '2015-11-21',    "
                            +"  'upsetPrice':{'price':'30213'} "
                            +"}                                ";

        Gson readGson = new GsonBuilder()
                .setDateFormat(datePattern)
                .create();
        //@formatter:on

        Product product = readGson.fromJson(jsonRawData, Product.class);

        System.out.println(product);

        // ---------- inner business logic process
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Apple");
        manufacturer.setCountry("USA");
        product.setManufacturer(manufacturer);

        // ---------- output json data
        //@formatter:off
        Gson writeGson = new GsonBuilder()
                .setDateFormat(datePattern)
                .setVersion(1.1)
                .setExclusionStrategies(new MyOtherExclusionStrategy(UpsetPrice.class))
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
                .create();
        //@formatter:on

        System.out.println(writeGson.toJson(product));
    }
}

/**
 * a merchandise data
 */
@Data
class Product implements Serializable {

    @Since(1.1) // new specification for show manufacturer
    private Manufacturer manufacturer;

    @Since(1.0) // original specification
    private String productNumber;

    private String numberOfSold;

    private String productName;

    private UpsetPrice upsetPrice;

    private Date createDate;

    private Date updateDate;

    @Mask("interal number should not be exposed")
    private static final long serialVersionUID = 8327107236157917413L;

    @Mask("interal number should not be exposed")
    private String sequenceNumberInDB;
}

@Data
class Manufacturer {
    @Since(1.2) // for future, show manufacturer level
    private String leve;

    private String name;
    private String country;
}

@Data
class UpsetPrice {
    private BigDecimal price;
}

class MyOtherExclusionStrategy implements ExclusionStrategy {

    private final Class<?> typeToSkip;

    public MyOtherExclusionStrategy(Class<?> typeToSkip) {
        this.typeToSkip = typeToSkip;
    }

    public boolean shouldSkipClass(Class<?> clazz) {
        return (clazz == typeToSkip);
    }

    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(Mask.class) != null;
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Mask {
    String[] value();
}