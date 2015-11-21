package idv.wildpeanut;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Since;

/**
 * Gson annotation /@Since allow developer provide api data in different version
 * 
 */
public class VersionSupportSample {

    public static void main(String[] args) {

        VersionedClass versionedObject = new VersionedClass();
        Gson gson = new GsonBuilder().setVersion(1.0).create();
        String jsonOutput = gson.toJson(versionedObject);
        System.out.println(jsonOutput);
        System.out.println();

        gson = new Gson();
        jsonOutput = gson.toJson(versionedObject);
        System.out.println(jsonOutput);
    }

}

class VersionedClass {
    @Since(1.1)
    private final String newerField;
    @Since(1.0)
    private final String newField;
    private final String field;

    public VersionedClass() {
        this.newerField = "最新";
        this.newField = "現在";
        this.field = "舊";
    }
}
