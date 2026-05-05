package features;

import com.xpeho.spring_boot_java_random_user.presentation.dto.UserRequest;
import io.cucumber.java.DataTableType;

import java.util.List;
import java.util.Map;

/**
 * Cucumber converters — automatically transform data
 * from .feature files into typed Java objects.
 */
public class CucumberTypeConfig {

    /**
     * Represents a field/expected verification row in a DataTable.
     */
    public record FieldAssertion(String field, String expected) {}

    /**
     * Converts each DataTable row (2 columns without header)
     * into a FieldAssertion object.
     */
    @DataTableType
    public FieldAssertion fieldAssertion(List<String> row) {
        if (row.size() < 2) {
            throw new IllegalArgumentException(
                "DataTable row must have at least 2 columns (field | expected), got %d: %s"
                    .formatted(row.size(), row)
            );
        }
        return new FieldAssertion(row.get(0).trim(), row.get(1).trim());
    }

    /**
     * Converts a DataTable row (Map<String,String>) into a UserRequest.
     * Usable in .feature files with header-based tables.
     */
    @DataTableType
    public UserRequest userRequest(Map<String, String> row) {
        return new UserRequest(
                row.get("gender"),
                row.get("firstname"),
                row.get("lastname"),
                row.get("civility"),
                row.get("email"),
                row.get("phone"),
                row.get("picture"),
                row.get("nat")
        );
    }

}
